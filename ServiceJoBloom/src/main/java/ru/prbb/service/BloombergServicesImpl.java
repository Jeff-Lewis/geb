/**
 * 
 */
package ru.prbb.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.prbb.Utils;
import ru.prbb.bloomberg.AtrRequest;
import ru.prbb.bloomberg.BdpOverrideRequest;
import ru.prbb.bloomberg.BdpRequest;
import ru.prbb.bloomberg.BdsRequest;
import ru.prbb.bloomberg.BdsRequest.BEST_ANALYST_RECS_BULK;
import ru.prbb.bloomberg.BdsRequest.PeerData;
import ru.prbb.bloomberg.BloombergSession;
import ru.prbb.bloomberg.HistoricalDataRequest;
import ru.prbb.bloomberg.MessageHandler;
import ru.prbb.bloomberg.ReferenceDataRequest;
import ru.prbb.domain.AtrDataItem;
import ru.prbb.domain.BloombergResultItem;
import ru.prbb.domain.CashFlowData;
import ru.prbb.domain.CashFlowResultItem;
import ru.prbb.domain.HistParamData;
import ru.prbb.domain.OverrideData;
import ru.prbb.domain.SecForJobRequest;
import ru.prbb.domain.SecurityItem;
import ru.prbb.domain.UpdateFutureData;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;

/**
 * @author RBr
 * 
 */
@Component
public final class BloombergServicesImpl implements BloombergServices
{
	private Log log = LogFactory.getLog(BloombergServicesImpl.class);

	@Autowired
	private BloombergDao dao;

	@Override
	public List<BloombergResultItem> executeBondYeildLoad(Date startDate, Date endDate, String[] securities) {
		log.info("executeBondYeildLoad " + startDate + ", " + endDate + ", " + securities);

		final String YLD_CNV_MID = "YLD_CNV_MID";

		final Map<String, Map<Date, Map<String, String>>> a =
				executeHistoricalDataRequest("Загрузка доходности облигаций",
						startDate, endDate,
						securities, new String[] { YLD_CNV_MID });

		final List<BloombergResultItem> res = new ArrayList<>();

		final SimpleDateFormat sdf = Utils.createDateFormatYMD();

		for (String security : securities) {
			// security -> {date -> { field, value } }
			final Map<Date, Map<String, String>> datevalues = a.get(security);

			for (Date date : datevalues.keySet()) {
				final Map<String, String> values = datevalues.get(date);
				final String value = values.get(YLD_CNV_MID);

				final BloombergResultItem item = new BloombergResultItem();
				item.setSecurity(security);
				item.setParams(YLD_CNV_MID);
				item.setDate(sdf.format(date));
				item.setValue(value);

				res.add(item);
			}
		}

		dao.putBondYeild(res);

		return res;
	}

	@Override
	public List<CashFlowResultItem> executeCashFlowLoad(final List<CashFlowData> items) {
		log.info("executeCashFlowLoad");

		final List<CashFlowResultItem> res = new ArrayList<>();

		final BloombergSession bs = new BloombergSession("Загрузка дат погашений");
		bs.start();
		try {
			final Service service = bs.getService("//blp/refdata");

			for (CashFlowData item : items) {
				final Request request = service.createRequest("ReferenceDataRequest");

				final Element _securities = request.getElement("securities");
				_securities.appendValue(item.name);

				final Element _fields = request.getElement("fields");
				_fields.appendValue("DES_CASH_FLOW");

				final Element _overrides = request.getElement("overrides");
				final Element overrid = _overrides.appendElement();
				overrid.setElement("fieldId", "Settle_Dt");
				overrid.setElement("value", Utils.toStringYMD2(item.date));

				bs.sendRequest(request, new MessageHandler() {

					@Override
					public void processMessage(Message msg) {
						final Element sdArray = msg.getElement("securityData");
						for (int i = 0; i < sdArray.numValues(); ++i) {
							final Element sd = sdArray.getValueAsElement(i);

							final String security = sd.getElementAsString("security");
							final Long id = getId(security);
							log.info(security + ", id:" + id);

							if (sd.hasElement("securityError")) {
								log.error(security + ", SecurityError:"
										+ sd.getElement("securityError").getElementAsString("message"));
								continue;
							}

							final Element fieldData = sd.getElement("fieldData");

							final Element fsArray = fieldData.getElement("DES_CASH_FLOW");
							for (int j = 0; j < fsArray.numValues(); ++j) {
								final Element fs = fsArray.getValueAsElement(j);

								final String date = fs.getElementAsString("Payment Date");
								final Double value = fs.getElementAsFloat64("Coupon Amount");
								final Double value2 = fs.getElementAsFloat64("Principal Amount");

								log.info("id:" + id + ", date:" + date + ", value:" + value + ", value2:" + value2);

								final CashFlowResultItem item = new CashFlowResultItem();
								res.add(item);
								item.setId(id);
								item.setSecurity(security);
								item.setDate(date);
								item.setValue(value);
								item.setValue2(value2);
							}
						}
					}

					private Long getId(String security) {
						for (CashFlowData item : items) {
							if (security.equals(item.name)) {
								return item.id;
							}
						}
						return null;
					}
				});
			}
		} finally {
			bs.stop();
		}
		dao.putSecurityCashFlow(res);

		return res;
	}

	@Override
	public List<BloombergResultItem> executeQuotesLoad(Date startDate, Date endDate, String[] securities) {
		log.info("QuotesLoad " + startDate + ", " + endDate + ", " + securities);

		final String PX_LAST = "PX_LAST";

		final Map<String, Map<Date, Map<String, String>>> a =
				executeHistoricalDataRequest("Загрузка котировок",
						startDate, endDate,
						securities, new String[] { PX_LAST });

		final List<BloombergResultItem> res = new ArrayList<>();

		final SimpleDateFormat sdf = Utils.createDateFormatYMD();

		for (String security : securities) {
			// security -> {date -> { field, value } }
			final Map<Date, Map<String, String>> datevalues = a.get(security);

			for (Date date : datevalues.keySet()) {
				final Map<String, String> values = datevalues.get(date);
				final String value = values.get(PX_LAST);

				final BloombergResultItem item = new BloombergResultItem();
				item.setSecurity(security);
				item.setParams(PX_LAST);
				item.setDate(sdf.format(date));
				item.setValue(value);
				res.add(item);
			}
		}

		dao.putQuotesOne(res);

		return res;
	}

	@Override
	public List<BloombergResultItem> executeRateCouponLoad(final Map<String, Long> items) {
		log.info("executeRateCouponLoad");

		final List<BloombergResultItem> info = new ArrayList<>();

		final BloombergSession bs = new BloombergSession("Загрузка ставки по купонам");
		bs.start();
		try {
			final Service service = bs.getService("//blp/refdata");

			final Request request = service.createRequest("ReferenceDataRequest");

			final Element _securities = request.getElement("securities");
			for (String security : items.keySet()) {
				_securities.appendValue(security);
			}

			final Element _fields = request.getElement("fields");
			_fields.appendValue("multi_cpn_schedule");

			bs.sendRequest(request, new MessageHandler() {

				@Override
				public void processMessage(Message msg) {
					final Element sdArray = msg.getElement("securityData");
					for (int i = 0; i < sdArray.numValues(); ++i) {
						final Element sd = sdArray.getValueAsElement(i);

						final String security = sd.getElementAsString("security");
						final Long id = items.get(security);
						log.info(security + ", id:" + id);

						if (sd.hasElement("securityError")) {
							log.error(security + ", SecurityError:"
									+ sd.getElement("securityError").getElementAsString("message"));
							continue;
						}

						final Element fieldData = sd.getElement("fieldData");

						final Element fsArray = fieldData.getElement("multi_cpn_schedule");
						for (int j = 0; j < fsArray.numValues(); ++j) {
							final Element fs = fsArray.getValueAsElement(j);

							final String date = fs.getElementAsString("Period End Date");
							final String value = fs.getElementAsString("Coupon");

							log.info("id:" + id + ", date:" + date + ", value:" + value);

							final BloombergResultItem item = new BloombergResultItem();
							info.add(item);
							item.setId(id);
							item.setSecurity(security);
							item.setDate(date);
							item.setValue(value);
						}
					}
				}
			});
		} finally {
			bs.stop();
		}

		dao.putSecurityCouponSchedule(info);

		return info;
	}

	@Override
	public List<BloombergResultItem> executeValuesLoad(final Map<String, Long> items) {
		log.info("executeValuesLoad");

		final List<BloombergResultItem> info = new ArrayList<>();

		final BloombergSession bs = new BloombergSession("Загрузка номинала");
		bs.start();
		try {
			final Service service = bs.getService("//blp/refdata");

			final Request request = service.createRequest("ReferenceDataRequest");

			final Element _securities = request.getElement("securities");
			for (String security : items.keySet()) {
				_securities.appendValue(security);
			}

			final Element _fields = request.getElement("fields");
			_fields.appendValue("Factor_Schedule");

			bs.sendRequest(request, new MessageHandler() {

				@Override
				public void processMessage(Message msg) {
					final Element sdArray = msg.getElement("securityData");
					for (int i = 0; i < sdArray.numValues(); ++i) {
						final Element sd = sdArray.getValueAsElement(i);

						final String security = sd.getElementAsString("security");
						final Long id = items.get(security);
						log.info(security + ", id:" + id);

						if (sd.hasElement("securityError")) {
							log.error(security + ", SecurityError:"
									+ sd.getElement("securityError").getElementAsString("message"));
							continue;
						}

						final Element fieldData = sd.getElement("fieldData");

						final Element fsArray = fieldData.getElement("Factor_Schedule");
						for (int j = 0; j < fsArray.numValues(); ++j) {
							final Element fs = fsArray.getValueAsElement(j);

							final String date = fs.getElementAsString("Payment Date");
							final String value = fs.getElementAsString("Factor");

							log.info("id:" + id + ", date:" + date + ", value:" + value);

							final BloombergResultItem item = new BloombergResultItem();
							info.add(item);
							item.setId(id);
							item.setSecurity(security);
							item.setDate(date);
							item.setValue(value);
						}
					}
				}
			});
		} finally {
			bs.stop();
		}

		dao.putFaceAmount(info);

		return info;
	}

	@Override
	public void executeAtrLoad(Date date) {
		log.info("executeAtrLoad " + date);

		final List<String> securities = dao.getSecForAtr();

		final AtrRequest r = new AtrRequest(date, date, securities,
				AtrDataItem.algorithm, AtrDataItem.atr_period,
				AtrDataItem.period, AtrDataItem.calendar);

		r.execute("Jobber/Atr");

		final Map<String, Map<java.sql.Date, Double>> a = r.getAnswer();
		List<AtrDataItem> items = new ArrayList<>();
		for (String security : securities) {
			final Map<java.sql.Date, Double> values = a.get(security);

			if (null != values) {
				for (java.sql.Date date_time : values.keySet()) {
					final Double atr_value = values.get(date_time);
					log.info("security:" + security + ", date_time:" + date_time + ", atr_value:" + atr_value);
					AtrDataItem item = new AtrDataItem(security, date_time, atr_value);
					items.add(item);
				}
			}
		}
		dao.putAtrData(items);
	}

	@Override
	public void executeBdpOverrideLoad() {
		log.info("executeBdpOverrideLoad");

		final List<SecForJobRequest> securities = dao.getLoadEstimatesPeersData();

		final BdpOverrideRequest r = new BdpOverrideRequest(securities);

		r.execute("Jobber/BDP override");

		Map<String, Map<String, String>> a = r.getAnswer();
		List<OverrideData> items = new ArrayList<>();
		for (SecForJobRequest security : securities) {
			final Map<String, String> values = a.get(security.code);

			if (null != values) {
				for (String period : values.keySet()) {
					final String value = values.get(period);
					log.info(security + ", " + period + ", " + value);
					OverrideData item = new OverrideData(security.code, value, period);
					items.add(item);
				}
			}
		}
		dao.putOverrideData(items);
	}

	@Override
	public void executeBdsLoad() {
		log.info("executeBdsLoad");

		final List<SecForJobRequest> securities = dao.getLoadEstimatesPeersData();
		String[] fields = { "BEST_ANALYST_RECS_BULK", "BLOOMBERG_PEERS" };

		final BdsRequest r = new BdsRequest(securities, fields);

		r.execute("Jobber/BDS");

		List<PeerData> pd = r.getPeersData();
		dao.putPeersData(pd);

		Map<String, List<BEST_ANALYST_RECS_BULK>> bestAnalyst = r.getBestAnalyst();
		Map<String, List<String>> peerTicker = r.getPeerTicker();

		for (SecForJobRequest info : securities) {
			final String security = info.code;

			final List<BEST_ANALYST_RECS_BULK> baItems = bestAnalyst.get(security);
			dao.putAnalysData(security, baItems);

			final List<String> ptItems = peerTicker.get(security);
			dao.putPeersProc(security, ptItems);
		}
	}

	@Override
	public void executeBondsLoad() {
		log.info("executeBondsLoad");

		final List<String> securities = dao.getSecForBonds();

		final String[] fields = {
				"CHG_PCT_1D", "YLD_YTM_MID"
		};

		final BdpRequest r = new BdpRequest(
				securities.toArray(new String[securities.size()]),
				fields);

		r.execute("Jobber/Bonds");

		final List<BloombergResultItem> items = new ArrayList<>();

		final Map<String, Map<String, String>> answer = r.getAnswer();
		for (String security : securities) {
			final Map<String, String> values = answer.get(security);
			for (String field : fields) {
				if (values.containsKey(field)) {
					BloombergResultItem item = new BloombergResultItem();
					item.setSecurity(security);
					item.setParams(field);
					item.setValue(values.get(field));
					items.add(item);
				}
			}
		}

		dao.putBondsData(items);
	}

	@Override
	public void executeHistDataLoad(Date date) {
		log.info("executeHistDataLoad " + date);
		final String _date = Utils.createDateFormatYMD().format(date);

		final List<SecForJobRequest> securities = dao.getSecForHistData();

		final Set<String> currencies = new HashSet<String>();
		for (SecForJobRequest item : securities) {
			currencies.add(item.iso);
		}

		final List<String> cursec = new ArrayList<String>();
		for (SecForJobRequest item : securities) {
			cursec.add(item.iso + item.code);
		}

		final String[] fields = {
				"EQY_WEIGHTED_AVG_PX",
				"PX_HIGH", "PX_LAST", "PX_LOW", "PX_VOLUME",
				"TOT_BUY_REC", "TOT_HOLD_REC", "TOT_SELL_REC"
		};

		final HistoricalDataRequest r = new HistoricalDataRequest(date, date,
				cursec.toArray(new String[cursec.size()]),
				fields,
				currencies.toArray(new String[currencies.size()]));

		r.execute("Jobber/HistData");

		final List<HistParamData> items = new ArrayList<HistParamData>();

		final Map<String, Map<Date, Map<String, String>>> answer = r.getAnswer();
		for (String currency : currencies) {
			for (String cs : cursec) {
				if (cs.startsWith(currency)) {
					final String security = cs.substring(currency.length());

					final Map<String, String> values = answer.get(cs).get(date);
					if (null == values) {
						log.error("Нет информации для " + security);
						continue;
					}

					for (String field : fields) {
						if (values.containsKey(field)) {
							final String value = values.get(field);
							log.info(security + ", " + field + ", " + date + ", " + value + ", " + currency);
							HistParamData item = new HistParamData(security, field, _date, value,
									"DAILY", currency, "CALENDAR");
							items.add(item);
						}
					}
				}
			}
		}

		dao.putHistParamsData(items);
	}

	@Override
	public void executeQuotesLoad(Date date) {
		log.info("executeQuotesLoad " + date);
		final String _date = Utils.createDateFormatYMD().format(date);

		final List<String> securities = dao.getSecForQuotes();
		log.info("securities size:" + securities.size());

		final String PX_LAST = "PX_LAST";
		final HistoricalDataRequest r = new HistoricalDataRequest(date, date,
				securities.toArray(new String[securities.size()]),
				new String[] { PX_LAST });

		r.execute("Jobber/Quotes");

		final List<BloombergResultItem> items = new ArrayList<>();

		final Map<String, Map<Date, Map<String, String>>> answer = r.getAnswer();
		for (String security : securities) {
			final Map<String, String> values = answer.get(security).get(date);
			if (null == values) {
				log.error("Нет информации для " + security);
				continue;
			}

			if (values.containsKey(PX_LAST)) {
				try {
					final String value = values.get(PX_LAST);
					log.info("security:" + security + ", value:" + value + ", date:" + date);
					BloombergResultItem item = new BloombergResultItem();
					item.setSecurity(security);
					item.setDate(_date);
					item.setValue(value);
					items.add(item);
				} catch (NumberFormatException e) {
					log.error(e.getMessage());
				}
			}
		}

		dao.putQuotes(items);
	}

	@Override
	public void executeFuturesLoad() {
		log.info("executeFuturesLoad");

		List<SecurityItem> securities = dao.getSecForUpdateFutures();

		final String[] _securities = new String[securities.size()];
		for (int i = 0; i < securities.size(); i++) {
			_securities[i] = securities.get(i).getCode();
		}
		final BdpRequest r = new BdpRequest(_securities,
				new String[] { "SECURITY_NAME", "NAME", "SHORT_NAME" });

		r.execute("Jobber/Update securities");

		final List<UpdateFutureData> items = new ArrayList<UpdateFutureData>();

		final Map<String, Map<String, String>> answer = r.getAnswer();
		for (SecurityItem security : securities) {
			String code = security.getCode();
			if (answer.containsKey(code)) {
				final Map<String, String> values = answer.get(code);
				final String secName = values.get("SECURITY_NAME");
				final String name = values.get("NAME");
				final String shortName = values.get("SHORT_NAME");
				log.info(code + ", " + secName + ", " + name + ", " + shortName);
				UpdateFutureData item = new UpdateFutureData(security.getId(), secName, name, shortName);
				items.add(item);
			} else {
				log.error("No answer for " + code);
			}
		}

		dao.putUpdatesFutures(items);
	}

	/**
	 * Выполнить запрос //blp/refdata, ReferenceDataRequest<br>
	 * Ввод новой акции<br>
	 * Ввод нового индекса<br>
	 * Ввод новой облигации<br>
	 * Ввод нового фьючерса
	 * 
	 * @param name
	 *            Название запроса
	 * @param securities
	 *            Коды
	 * @param fields
	 *            Поля
	 * @return security -> {field, value}
	 */
	public Map<String, Map<String, String>> executeReferenceDataRequest(String name,
			String[] securities, String[] fields) {
		final ReferenceDataRequest r = new ReferenceDataRequest(securities, fields);
		r.execute(name);
		return r.getAnswer();
	}

	/**
	 * Загрузка котировок
	 * 
	 * @param name
	 *            Название запроса
	 * @param startDate
	 * @param endDate
	 * @param securities
	 *            Коды
	 * @param fields
	 *            Поля
	 * @return security -> {date -> { field, value } }
	 */
	public Map<String, Map<Date, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields) {
		final HistoricalDataRequest r = new HistoricalDataRequest(startDate, endDate, securities, fields);
		r.execute(name);
		return r.getAnswer();
	}
}
