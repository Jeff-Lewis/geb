/**
 * 
 */
package ru.prbb.jobber.services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.Utils;
import ru.prbb.jobber.domain.AtrLoadDataItem;
import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SendMessageItem;
import ru.prbb.jobber.repo.BloombergDao;
import ru.prbb.jobber.repo.BloombergServicesJ;
import ru.prbb.jobber.repo.ParametersDao;
import ru.prbb.jobber.repo.SendingDao;
import ru.prbb.jobber.repo.SubscriptionDao;

/**
 * Запланированные задачи Jobber
 * 
 * @author RBr
 */
@Service
public class ScheduledServices {

	private static final String PREFIX = "ru.prbb.jobber.scheduled_task.";

	public static final String TASK_BDS_LOAD = PREFIX + "BdsLoad";
	public static final String TASK_FUTURES_LOAD = PREFIX + "FuturesLoad";
	public static final String TASK_QUOTES_LOAD = PREFIX + "QuotesLoad";
	public static final String TASK_ATR_LOAD = PREFIX + "AtrLoad";
	public static final String TASK_BDP_OVERRIDE_LOAD = PREFIX + "BdpOverrideLoad";
	public static final String TASK_HIST_DATA_LOAD = PREFIX + "HistDataLoad";
	public static final String TASK_CURRENCIES_DATA_LOAD = PREFIX + "CurrenciesDataLoad";
	public static final String TASK_BONDS_LOAD = PREFIX + "BondsLoad";
	public static final String TASK_SUBSCRIPTION_CHECK = PREFIX + "SubscriptionCheck";
	public static final String TASK_JOBBERS = PREFIX + "Jobbers";
	public static final String TASK_QUOTES = PREFIX + "Quotes";
	public static final String TASK_BONDS = PREFIX + "Bonds";
	public static final String TASK_QUOTES_RUS = PREFIX + "QuotesRus";
	public static final String TASK_FULLERMONEY_AUDIO = PREFIX + "FullermoneyAudio";
	public static final String TASK_QUOTES_USA = PREFIX + "QuotesUsa";
	public static final String TASK_ANALYTICS = PREFIX + "Analytics";

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ParametersDao parameters;

	@Autowired
	private BloombergServicesJ bs;

	@Autowired
	private BloombergDao daoBloomberg;
	@Autowired
	private SubscriptionDao daoSubscription;
	@Autowired
	private SendingDao daoSending;

	/*
	 * Цель перезапуска
	 * 1. блумберг изредка сходил сума и переставал отдавать данные по тикерам
	 * избирательно
	 * 2. обновление указателей на активный контракт для фьючей
	 */
	@Scheduled(cron = "30 0 3 * * *")
	public void subscriptionStop() {
		log.info("Stop subscription");

		//bs.subscriptionStop();
	}

	@Scheduled(cron = "0 0 3 * * *")
	@Description(parameter = TASK_BDS_LOAD, comment = "Jobber/BDS")
	public void taskBdsLoad() {
		if (isTaskDisabled(TASK_BDS_LOAD)) {
			log.info("task BdsLoad is disabled");
			return;
		}

		log.info("task BdsLoad");

		List<SecForJobRequest> securities = daoBloomberg.getLoadEstimatesPeersData();
		String[] _securities = new String[securities.size()];
		for (int i = 0; i < securities.size(); i++) {
			SecForJobRequest security = securities.get(i);
			_securities[i] = security.code;
		}

		String[] fields = { "BEST_ANALYST_RECS_BULK", "BLOOMBERG_PEERS" };

		Map<String, Object> answer = bs.executeBdsRequest("Jobber/BDS", _securities, fields);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> pd = ((List<Map<String, Object>>) answer.get("PEERS"));
		for (Map<String, Object> data : pd) {
			try {
				daoBloomberg.putPeersData(data);
			} catch (Exception e) {
				log.error("putPeersData " + data, e);
				Object res = "empty";
				try {
					res = daoBloomberg.putPeersDataTest(data);
				} catch (Exception e1) {
					log.error("putPeersDataTest " + data, e1);
				}
				log.info("putPeersDataTest data={}", data);
				log.info("putPeersDataTest  res={}", res);
			}
		}

		@SuppressWarnings("unchecked")
		Map<String, List<Map<String, String>>> ba =
				(Map<String, List<Map<String, String>>>) answer.get("BEST_ANALYST_RECS_BULK");

		for (String security : _securities) {
			List<Map<String, String>> datas = ba.get(security);
			if (null == datas) {
				log.warn("no datas for " + security);
				continue;
			}
			for (Map<String, String> data : datas) {
				try {
					daoBloomberg.putAnalysData(security, data);
				} catch (Exception e) {
					log.error("putAnalysData " + data, e);
				}
			}
		}

		@SuppressWarnings("unchecked")
		Map<String, List<String>> pt =
				(Map<String, List<String>>) answer.get("BLOOMBERG_PEERS");

		for (String security : _securities) {
			List<String> datas = pt.get(security);
			if (null == datas) {
				log.warn("no datas for " + security);
				continue;
			}
			for (String data : datas) {
				try {
					data += " Equity";
					daoBloomberg.putPeersProc(security, data);
				} catch (Exception e) {
					log.error("putPeersProc " + data, e);
				}
			}
		}

		log.info("task BdsLoad done");
	}

	@Scheduled(cron = "0 15 4 * * *")
	@Description(parameter = TASK_FUTURES_LOAD, comment = "Jobber/Update securities")
	public void taskFuturesLoad() {
		if (isTaskDisabled(TASK_FUTURES_LOAD)) {
			log.info("task FuturesLoad is disabled");
			return;
		}
		log.info("task FuturesLoad");

		List<SecurityItem> securities = daoBloomberg.getSecForUpdateFutures();
		final String[] _securities = new String[securities.size()];
		for (int i = 0; i < securities.size(); i++) {
			_securities[i] = securities.get(i).getCode();
		}

		Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Jobber/Update securities", _securities,
						toArray("SECURITY_NAME", "NAME", "SHORT_NAME", "FUT_FIRST_TRADE_DT", "LAST_TRADEABLE_DT"));

		for (SecurityItem security : securities) {
			Map<String, String> data = answer.get(security.getCode());
			if (null == data) {
				log.warn("no data for " + security);
				continue;
			}
			try {
				daoBloomberg.putUpdatesFutures(security.getId(), data);
			} catch (Exception e) {
				log.error("putUpdatesFutures " + data, e);
			}
		}

		log.info("task FuturesLoad done");
	}

	// @Scheduled(cron = "0 55 4 * * *")
	//@Description(parameter=, comment="QuotesPortfolio")
	private void taskQuotesPortfolio() {
		log.info("task QuotesPortfolio");

		daoBloomberg.execQuotesPortfolio(new java.sql.Date(yesterday().getTime()));
	}

	@Scheduled(cron = "0 0 5 * * *")
	@Description(parameter = TASK_QUOTES_LOAD, comment = "Jobber/Quotes")
	public void taskQuotesLoad() {
		if (isTaskDisabled(TASK_QUOTES_LOAD)) {
			log.info("task QuotesLoad is disabled");
			return;
		}
		taskQuotesPortfolio();

		log.info("task QuotesLoad");

		Date date = yesterday();

		String[] securities = toArray(daoBloomberg.getSecForQuotes());

		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeHistoricalDataRequest("Jobber/Quotes", date, date,
						securities, toArray("PX_LAST"));

		for (String security : securities) {
			Map<String, Map<String, String>> datas = answer.get(security);
			if (null == datas) {
				log.warn("no datas for " + security);
				continue;
			}
			for (Entry<String, Map<String, String>> entry : datas.entrySet()) {
				String _date = entry.getKey();
				Map<String, String> data = entry.getValue();
				try {
					daoBloomberg.putQuotes(_date, security, Utils.toDouble(data.get("PX_LAST")));
				} catch (Exception e) {
					log.error("putQuotes " + data, e);
				}
			}
		}

		log.info("task QuotesLoad done");
	}

	@Scheduled(cron = "0 10 5 * * *")
	@Description(parameter = TASK_ATR_LOAD, comment = "Jobber/Atr load")
	public void taskAtrLoad() {
		if (isTaskDisabled(TASK_ATR_LOAD)) {
			log.info("task AtrLoad is disabled");
			return;
		}
		log.info("task AtrLoad");

		Date date = yesterday();

		String[] securities = toArray(daoBloomberg.getSecForAtr());

		List<AtrLoadDataItem> answer =
				bs.executeAtrLoad("Jobber/Atr load", date, date, securities,
						"Exponential", 7, "DAILY", "CALENDAR");

		for (AtrLoadDataItem item : answer) {
			try {
				daoBloomberg.putAtrData(item);
			} catch (Exception e) {
				log.error("putAtrData " + item, e);
			}
		}

		log.info("task AtrLoad done");
	}

	@Scheduled(cron = "0 0 6 * * *")
	@Description(parameter = TASK_BDP_OVERRIDE_LOAD, comment = "Jobber/BDP override")
	public void taskBdpOverrideLoad() {
		if (isTaskDisabled(TASK_BDP_OVERRIDE_LOAD)) {
			log.info("task BdpOverrideLoad is disabled");
			return;
		}
		log.info("task BdpOverrideLoad");

		List<SecForJobRequest> securities = daoBloomberg.getLoadEstimatesPeersData();

		Map<String, Map<String, String>> answer =
				bs.executeBdpOverrideLoad("Jobber/BDP override", securities);

		for (SecForJobRequest security : securities) {
			final Map<String, String> values = answer.get(security.code);
			if (null == values) {
				log.warn("no values for " + security.code);
				continue;
			}
			for (Entry<String, String> entry : values.entrySet()) {
				String period = entry.getKey();
				String value = entry.getValue();
				try {
					daoBloomberg.putOverrideData(security.code, value, period);
				} catch (Exception e) {
					log.error("putOverrideData " + security.code + ", " + period + "=" + value, e);
				}
			}
		}

		log.info("task BdpOverrideLoad done");
	}

	@Scheduled(cron = "0 0 7 * * *")
	@Description(parameter = TASK_HIST_DATA_LOAD, comment = "Jobber/HistData")
	public void taskHistDataLoad() {
		if (isTaskDisabled(TASK_HIST_DATA_LOAD)) {
			log.info("task HistDataLoad is disabled");
			return;
		}
		log.info("task HistDataLoad");

		Date date = yesterday();

		List<SecForJobRequest> securities = daoBloomberg.getSecForHistData();

		Set<String> _currencies = new HashSet<>();
		for (SecForJobRequest item : securities) {
			_currencies.add(item.iso);
		}
		String[] currencies = toArray(_currencies);

		String[] cursec = new String[securities.size()];
		int i = 0;
		for (SecForJobRequest item : securities) {
			cursec[i++] = item.iso + item.code;
		}

		final String[] fields = {
				"EQY_WEIGHTED_AVG_PX", "YLD_CNV_MID",
				"PX_HIGH", "PX_LAST", "PX_LOW", "PX_VOLUME",
				"TOT_BUY_REC", "TOT_HOLD_REC", "TOT_SELL_REC"
		};

		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeHistoricalDataRequest("Jobber/HistData", date, date, cursec, fields, currencies);

		String strDate = Utils.createDateFormatYMD().format(date);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		for (String currency : currencies) {
			for (String cs : cursec) {
				if (cs.startsWith(currency)) {
					final String security = cs.substring(currency.length());

					final Map<String, String> values = answer.get(cs).get(strDate);
					if (null == values) {
						log.warn("no values for " + security);
						continue;
					}

					for (String field : fields) {
						if (values.containsKey(field)) {
							String value = values.get(field);
							try {
								daoBloomberg.putHistParamsData(security, field, sqlDate, value, currency);
							} catch (Exception e) {
								log.error("putHistParamsData " + values, e);
							}
						}
					}

				}
			}
		}

		log.info("task HistDataLoad done");
	}

	@Scheduled(cron = "0 0 8 * * *")
	@Description(parameter = TASK_CURRENCIES_DATA_LOAD, comment = "Jobber/Currency")
	public void taskCurrenciesDataLoad() {
		if (isTaskDisabled(TASK_CURRENCIES_DATA_LOAD)) {
			log.info("task CurrenciesDataLoad is disabled");
			return;
		}
		log.info("task CurrenciesDataLoad");

		String[] securities = toArray(daoBloomberg.getSecForCurrency());

		Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Jobber/Currency", securities, toArray("PX_LAST", "QUOTE_FACTOR"));

		for (String security : securities) {
			Map<String, String> data = answer.get(security);
			if (null == data) {
				log.warn("no data for " + security);
				continue;
			}
			try {
				Integer quoteFactor = Utils.parseNumber(data.get("QUOTE_FACTOR")).intValue();
				Number pxLast = Utils.parseNumber(data.get("PX_LAST"));
				daoBloomberg.putCurrencyData(security, quoteFactor, pxLast);
				;
			} catch (Exception e) {
				log.error("putCurrencyData " + data, e);
			}
		}

		log.info("task CurrenciesDataLoad done");
	}

	//@Scheduled(cron = "0 59 11-18 * * *")
	@Description(parameter = TASK_BONDS_LOAD, comment = "Jobber/Bonds")
	public void taskBondsLoad() {
		if (isTaskDisabled(TASK_BONDS_LOAD)) {
			log.info("task BondsLoad is disabled");
			return;
		}
		log.info("task BondsLoad");

		String[] securities = toArray(daoBloomberg.getSecForBonds());

		String[] fields = { "CHG_PCT_1D", "YLD_YTM_MID" };

		Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Jobber/Bonds", securities, fields);

		for (String security : securities) {
			Map<String, String> data = answer.get(security);
			if (null == data) {
				log.warn("no data for " + security);
				continue;
			}
			try {
				daoBloomberg.putBondsData(security, fields, data);
			} catch (Exception e) {
				log.error("putBondsData " + data, e);
			}
		}

		//taskBonds();
	}

	/**
	 * Проверка работы real-time обновлений (подписки)
	 */
	@Scheduled(cron = "0 0/15 8-23 * * MON-FRI")
	@Description(parameter = TASK_SUBSCRIPTION_CHECK, comment = "Проверка работы real-time обновлений (подписки)")
	public void taskSubscriptionCheck() {
		if (isTaskDisabled(TASK_SUBSCRIPTION_CHECK)) {
			log.info("task SubscriptionCheck is disabled");
			return;
		}
		log.info("task SubscriptionCheck");
		try {
			List<SendMessageItem> items = daoBloomberg.exec("{call dbo.chck_cbot_sp}");
//			if (items.size() > 0)
//				log.error(items.get(0).getText());
			daoSending.send(items);
		} catch (PersistenceException e) {
			if (e.getMessage().contains("JZ0R2: No result set for this query")) {
				log.info("Subscription is active");
			} else {
				throw e;
			}
		}
	}

	/**
	 * Проверка результатов ночных загрузок
	 */
	@Scheduled(cron = "0 0 10 * * MON-FRI")
	@Description(parameter = TASK_JOBBERS, comment = "Проверка результатов ночных загрузок")
	public void taskJobbers() {
		if (isTaskDisabled(TASK_JOBBERS)) {
			log.info("task Jobbers is disabled");
			return;
		}
		log.info("task Jobbers");
		try {
			List<SendMessageItem> items = daoBloomberg.exec("{call dbo.put_quotes_check_sp}");
			daoSending.send(items);
		} catch (PersistenceException e) {
			if (e.getMessage().contains("JZ0R2: No result set for this query")) {
				log.info("task Jobbers: done");
			} else {
				log.error("task Jobbers", e);
			}
		}
	}

	/**
	 * Рассылка котировок, основываясь на файлике Quotes.xls и используя jsp
	 * Пн.-Пт., с 10:00 каждые 30 минут на протяжении 15 часов 15 минут
	 */
	@Scheduled(cron = "0 0/30 0,10-23 * * MON-FRI")
	@Description(parameter = TASK_QUOTES, comment = "Рассылка котировок")
	public void taskQuotes() {
		if (isTaskDisabled(TASK_QUOTES)) {
			log.info("task Quotes is disabled");
			return;
		}
		log.info("task Quotes");
		try {
			List<SendMessageItem> items = daoBloomberg.exec("{call dbo.quotes_send_mail_sp}");
			daoSending.send(items);
		} catch (PersistenceException e) {
			if (e.getMessage().contains("JZ0R2: No result set for this query")) {
				log.info("task Quotes: done");
			} else {
				log.error("task Quotes", e);
			}
		}
	}

	/**
	 * Отправка смс оповещений с котировками бондов
	 */
	//@Scheduled(cron = "0 0 14 * * MON-FRI")
	@Description(parameter = TASK_BONDS, comment = "Отправка смс оповещений с котировками бондов")
	public void taskBonds() {
		if (isTaskDisabled(TASK_BONDS)) {
			log.info("task Bonds is disabled");
			return;
		}
		log.info("task Bonds");
		try {
			List<SendMessageItem> items = daoBloomberg.exec("{call dbo.quotes_send_sms_bonds_sp}");
			daoSending.send(items);
		} catch (PersistenceException e) {
			if (e.getMessage().contains("JZ0R2: No result set for this query")) {
				log.info("task Bonds: done");
			} else {
				log.error("task Bonds", e);
			}
		}
	}

	/**
	 * Отправка E-mail оповещений с котировками по России
	 */
	@Scheduled(cron = "0 0/30 * * * *")
	@Description(parameter = TASK_QUOTES_RUS, comment = "Отправка E-mail оповещений с котировками по России")
	public void taskQuotesRus() {
		if (isTaskDisabled(TASK_QUOTES_RUS)) {
			log.info("task QuotesRus is disabled");
			return;
		}
		log.info("task QuotesRus");
		try {
			List<SendMessageItem> items = daoBloomberg.exec("{call dbo.quotes_send_mail_rus_sp}");
			daoSending.send(items);
		} catch (PersistenceException e) {
			if (e.getMessage().contains("JZ0R2: No result set for this query")) {
				log.info("task QuotesRus: done");
			} else {
				log.error("task QuotesRus", e);
			}
		}
	}

	/**
	 * Рассылка ссылок на Fullermoney Audio
	 * Вт.-Сб.
	 */
	@Scheduled(cron = "0 30 12 * * TUE-SAT")
	@Description(parameter = TASK_FULLERMONEY_AUDIO, comment = "Рассылка ссылок на Fullermoney Audio")
	public void taskFullermoneyAudio() {
		if (isTaskDisabled(TASK_FULLERMONEY_AUDIO)) {
			log.info("task Fullermoney Audio is disabled");
			return;
		}
		log.info("task Fullermoney Audio");
		try {
			List<SendMessageItem> items = daoBloomberg.exec("{call dbo.fuller_send_sp}");
			daoSending.send(items);
		} catch (PersistenceException e) {
			if (e.getMessage().contains("JZ0R2: No result set for this query")) {
				log.info("task Subscription: No data");
			} else {
				log.error("task Subscription", e);
			}
		}
	}

	/**
	 * Отправка смс оповещений с котировками по США
	 */
	@Scheduled(cron = "0 50 18 * * *")
	@Description(parameter = TASK_QUOTES_USA, comment = "Отправка смс оповещений с котировками по США")
	public void taskQuotesUsa() {
		if (isTaskDisabled(TASK_QUOTES_USA)) {
			log.info("task QuotesUsa is disabled");
			return;
		}
		log.info("task QuotesUsa");
		try {
			List<SendMessageItem> items = daoBloomberg.exec("{call dbo.quotes_send_sms_usa_sp}");
			daoSending.send(items);
		} catch (PersistenceException e) {
			if (e.getMessage().contains("JZ0R2: No result set for this query")) {
				log.info("task Subscription: No data");
			} else {
				log.error("task Subscription", e);
			}
		}
	}

	/**
	 * Рассылка SMS со ссылкой на ежедневный выпуск сводной
	 */
	@Scheduled(cron = "0 0 20 * * MON-FRI")
	@Description(parameter = TASK_ANALYTICS, comment = "Рассылка SMS со ссылкой на ежедневный выпуск сводной")
	public void taskAnalytics() {
		if (isTaskDisabled(TASK_ANALYTICS)) {
			log.info("task Analytics is disabled");
			return;
		}
		log.info("task Analytics");
		try {
			List<SendMessageItem> items = daoBloomberg.exec("{call dbo.analytics_news_send_sp}");
			daoSending.send(items);
		} catch (PersistenceException e) {
			if (e.getMessage().contains("JZ0R2: No result set for this query")) {
				log.info("task Subscription: No data");
			} else {
				log.error("task Subscription", e);
			}
		}
	}

	public boolean isTaskDisabled(String name) {
		try {
			String value = parameters.getValue(name).toLowerCase();
			return "disabled".equals(value.toLowerCase());
		} catch (Exception ignore) {
		}
		return false;
	}

	private String[] toArray(Collection<String> list) {
		return list.toArray(new String[list.size()]);
	}

	private String[] toArray(String... args) {
		return args;
	}

	private Date yesterday() {
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DAY_OF_YEAR, -1);
		return c.getTime();
	}
}
