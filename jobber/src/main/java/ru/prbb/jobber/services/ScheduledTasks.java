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
import java.util.Set;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ru.prbb.Utils;
import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SendMessageItem;
import ru.prbb.jobber.repo.BloombergDao;
import ru.prbb.jobber.repo.BloombergServicesJ;
import ru.prbb.jobber.repo.SendingDao;

/**
 * Запланированные задачи Jobber
 * 
 * @author RBr
 */
@Configuration
@EnableScheduling
public class ScheduledTasks {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServicesJ bs;

	@Autowired
	private BloombergDao dao;
	
	@Autowired
	private SendingDao daoSending;

	@Scheduled(cron = "0 00 3 * * ?")
	public void taskBdsLoad() {
		log.info("task BdsLoad");

		List<SecForJobRequest> securities = dao.getLoadEstimatesPeersData();
		String[] _securities = new String[securities.size()];
		for (int i = 0; i < securities.size(); i++) {
			SecForJobRequest security = securities.get(i);
			_securities[i] = security.code;
		}
		
		String[] fields = { "BEST_ANALYST_RECS_BULK", "BLOOMBERG_PEERS" };

		Map<String, Object> answer = bs.executeBdsRequest("Jobber/BDS", _securities, fields);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> pd = ((List<Map<String, Object>>) answer.get("Peers"));
		dao.putPeersData(pd);

		@SuppressWarnings("unchecked")
		Map<String, List<Map<String, String>>> ba =
				(Map<String, List<Map<String, String>>>) answer.get("BEST_ANALYST_RECS_BULK");

		dao.putAnalysData(_securities, ba);

		@SuppressWarnings("unchecked")
		Map<String, List<String>> pt =
				(Map<String, List<String>>) answer.get("PeerTicker");

		dao.putPeersProc(_securities, pt);
	}

	@Scheduled(cron = "0 15 4 * * ?")
	public void taskFuturesLoad() {
		log.info("task FuturesLoad");

		List<SecurityItem> securities = dao.getSecForUpdateFutures();
		final String[] _securities = new String[securities.size()];
		for (int i = 0; i < securities.size(); i++) {
			_securities[i] = securities.get(i).getCode();
		}

		Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Jobber/Update securities", _securities,
						toArray("SECURITY_NAME", "NAME", "SHORT_NAME", "FUT_FIRST_TRADE_DT", "LAST_TRADEABLE_DT"));

		dao.putUpdatesFutures(securities, answer);
	}

	// @Scheduled(cron = "0 55 4 * * ?")
	private void taskQuotesPortfolio() {
		log.info("task QuotesPortfolio");

		dao.execQuotesPortfolio(new java.sql.Date(yesterday().getTime()));
	}

	@Scheduled(cron = "0 00 5 * * ?")
	public void taskQuotesLoad() {
		taskQuotesPortfolio();

		log.info("task QuotesLoad");

		Date date = yesterday();
		String _date = Utils.createDateFormatYMD().format(date);

		String[] securities = toArray(dao.getSecForQuotes());

		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeHistoricalDataRequest("Jobber/Quotes", date, date,
						securities, toArray("PX_LAST"));

		dao.putQuotes(_date, securities, answer);
	}

	@Scheduled(cron = "0 10 5 * * ?")
	public void taskAtrLoad() {
		log.info("task AtrLoad");

		Date date = yesterday();

		String[] securities = toArray(dao.getSecForAtr());

		List<Map<String, Object>> answer =
				bs.executeAtrLoad("Jobber/Atr", date, date, securities,
						"Exponential", 7, "DAILY", "CALENDAR");

		dao.putAtrData(securities, answer);
	}

	@Scheduled(cron = "0 00 6 * * ?")
	public void taskBdpOverrideLoad() {
		log.info("task BdpOverrideLoad");
		
		List<SecForJobRequest> securities = dao.getLoadEstimatesPeersData();

		Map<String, Map<String, String>> answer =
				bs.executeBdpOverrideLoad("Jobber/BDP override", securities);

		dao.putOverrideData(securities, answer);
	}

	@Scheduled(cron = "0 00 7 * * ?")
	public void taskHistDataLoad() {
		log.info("task HistDataLoad");

		Date date = yesterday();

		List<SecForJobRequest> securities = dao.getSecForHistData();

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

		String _date = Utils.createDateFormatYMD().format(date);
		dao.putHistParamsData(_date, currencies, cursec, answer);
	}

	@Scheduled(cron = "0 00 8 * * ?")
	public void taskCurrenciesDataLoad() {
		log.info("task CurrenciesDataLoad");

		String[] securities = toArray(dao.getSecForCurrency());

		Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Jobber/Currency", securities, toArray("PX_LAST", "QUOTE_FACTOR"));

		dao.putCurrencyData(securities, answer);
	}

	@Scheduled(cron = "0 59 11-18 * * ?")
	public void taskBondsLoad() {
		log.info("task BondsLoad");

		String[] securities = toArray(dao.getSecForBonds());

		String[] fields = { "CHG_PCT_1D", "YLD_YTM_MID" };

		Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Jobber/Bonds", securities, fields);

		dao.putBondsData(securities, answer);
		
		taskBonds();
	}

	/**
	 * Проверка работы real-time обновлений (подписки)
	 */
	@Scheduled(cron = "0 */15 8-23 * * MON-FRI")
	public void taskSubscription() {
		log.info("task Subscription");
		try {
			List<SendMessageItem> items = dao.exec("{call dbo.chck_cbot_sp}");
			daoSending.send(items);
		} catch (PersistenceException e) {
			if (e.getMessage().contains("JZ0R2: No result set for this query")) {
				// всё в порядке
			} else {
				throw e;
			}
		}
	}

	/**
	 * Проверка результатов ночных загрузок
	 */
	@Scheduled(cron = "0 0 10 * * MON-FRI")
	public void taskJobbers() {
		log.info("task Jobbers");
		try {
			List<SendMessageItem> items = dao.exec("{call dbo.put_quotes_check_sp}");
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
	 * Рассылка котировок, основываясь на файлике Quotes.xls и используя jsp
	 * Пн.-Пт., с 10:00 каждые 30 минут на протяжении 15 часов 15 минут
	 */
	@Scheduled(cron = "0 0,30 0,10-23 * * MON-FRI")
	public void taskQuotes() {
		log.info("task Quotes");
		try {
			List<SendMessageItem> items = dao.exec("{call dbo.quotes_send_mail_sp}");
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
	 * Отправка смс оповещений с котировками бондов
	 */
	//@Scheduled(cron = "0 0 12-19 * * MON-FRI")
	public void taskBonds() {
		log.info("task Bonds");
		try {
			List<SendMessageItem> items = dao.exec("{call dbo.quotes_send_sms_bonds_sp}");
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
	 * Отправка E-mail оповещений с котировками по России
	 */
	@Scheduled(cron = "0 0,30 * * * ?")
	public void taskQuotesRus() {
		log.info("task QuotesRus");
		try {
			List<SendMessageItem> items = dao.exec("{call dbo.quotes_send_mail_rus_sp}");
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
	 * Рассылка ссылок на Fullermoney Audio
	 * Вт.-Сб.
	 */
	@Scheduled(cron = "0 30 12 * * TUE-SAT")
	public void taskFullermoneyAudio() {
		log.info("task Fullermoney Audio");
		try {
			List<SendMessageItem> items = dao.exec("{call dbo.fuller_send_sms_sp}");
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
	@Scheduled(cron = "0 50 18 * * ?")
	public void taskQuotesUsa() {
		log.info("task QuotesUsa");
		try {
			List<SendMessageItem> items = dao.exec("{call dbo.quotes_send_sms_usa_sp}");
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
	public void taskAnalytics() {
		log.info("task Analytics");
		try {
			List<SendMessageItem> items = dao.exec("{call dbo.analytics_news_send_sms_sp}");
			daoSending.send(items);
		} catch (PersistenceException e) {
			if (e.getMessage().contains("JZ0R2: No result set for this query")) {
				log.info("task Subscription: No data");
			} else {
				log.error("task Subscription", e);
			}
		}
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
