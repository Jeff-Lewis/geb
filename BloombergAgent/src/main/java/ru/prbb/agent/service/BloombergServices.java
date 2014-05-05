/**
 * 
 */
package ru.prbb.agent.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import ru.prbb.Utils;
import ru.prbb.bloomberg.core.BloombergSession;
import ru.prbb.bloomberg.core.MessageHandler;
import ru.prbb.bloomberg.model.AtrDataItem;
import ru.prbb.bloomberg.model.BloombergResultItem;
import ru.prbb.bloomberg.model.CashFlowData;
import ru.prbb.bloomberg.model.CashFlowResultItem;
import ru.prbb.bloomberg.model.SecForJobRequest;
import ru.prbb.bloomberg.model.SecurityItem;
import ru.prbb.bloomberg.request.AtrLoadRequest;
import ru.prbb.bloomberg.request.AtrRequest;
import ru.prbb.bloomberg.request.BdhEpsRequest;
import ru.prbb.bloomberg.request.BdhRequest;
import ru.prbb.bloomberg.request.BdpOverrideRequest;
import ru.prbb.bloomberg.request.BdpRequest;
import ru.prbb.bloomberg.request.BdpRequestOverride;
import ru.prbb.bloomberg.request.BdpRequestOverrideQuarter;
import ru.prbb.bloomberg.request.BdsRequest;
import ru.prbb.bloomberg.request.BdsRequest.BEST_ANALYST_RECS_BULK;
import ru.prbb.bloomberg.request.BdsRequest.EARN_ANN_DT_TIME_HIST_WITH_EPS;
import ru.prbb.bloomberg.request.BdsRequest.ERN_ANN_DT_AND_PER;
import ru.prbb.bloomberg.request.BdsRequest.PeerData;
import ru.prbb.bloomberg.request.CashFlowLoadRequest;
import ru.prbb.bloomberg.request.CashFlowLoadRequestNew;
import ru.prbb.bloomberg.request.FieldInfoRequest;
import ru.prbb.bloomberg.request.HistoricalDataRequest;
import ru.prbb.bloomberg.request.RateCouponLoadRequest;
import ru.prbb.bloomberg.request.ReferenceDataRequest;
import ru.prbb.bloomberg.request.ValuesLoadRequest;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;

/**
 * @author RBr
 */
@Component
public final class BloombergServices {

	private final Log log = LogFactory.getLog(getClass());

	public Map<String, Map<String, Map<String, String>>> executeBondYeildLoad(
			String startDate, String endDate, String[] securities) {
		log.info("BondYeildLoad:");

		String[] fields = { "YLD_CNV_MID" };
		final HistoricalDataRequest r = new HistoricalDataRequest(startDate, endDate, securities, fields);
		r.execute("Загрузка доходности облигаций");
		return r.getAnswer();
	}

	public List<CashFlowResultItem> executeCashFlowLoad(final List<CashFlowData> items) {
		log.info("CashFlowLoad:");

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

							if (sd.hasElement("securityError")) {
								log.error(security + ", SecurityError:" + sd.getElement("securityError").getElementAsString("message"));
								continue;
							}

							final Element fieldData = sd.getElement("fieldData");

							final Element fsArray = fieldData.getElement("DES_CASH_FLOW");
							for (int j = 0; j < fsArray.numValues(); ++j) {
								final Element fs = fsArray.getValueAsElement(j);

								final String date = fs.getElementAsString("Payment Date");
								final Double coupon = fs.getElementAsFloat64("Coupon Amount");
								final Double principal = fs.getElementAsFloat64("Principal Amount");

								final CashFlowResultItem item = new CashFlowResultItem();
								res.add(item);
								item.setId(id);
								item.setSecurity(security);
								item.setDate(date);
								item.setCoupon(coupon);
								item.setPrincipal(principal);
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
		return res;
	}

	public List<BloombergResultItem> executeQuotesLoad(String startDate, String endDate, String[] securities) {
		log.info("QuotesLoad:");

		final String PX_LAST = "PX_LAST";

		final Map<String, Map<String, Map<String, String>>> a =
				executeHistoricalDataRequest("Загрузка котировок", startDate, endDate,
						securities, new String[] { PX_LAST });

		final List<BloombergResultItem> res = new ArrayList<>();

		for (String security : securities) {
			// security -> {date -> { field, value } }
			final Map<String, Map<String, String>> datevalues = a.get(security);

			for (String date : datevalues.keySet()) {
				final Map<String, String> values = datevalues.get(date);
				final String value = values.get(PX_LAST);

				final BloombergResultItem item = new BloombergResultItem();
				item.setSecurity(security);
				item.setParams(PX_LAST);
				item.setDate(date);
				item.setValue(value);
				res.add(item);
			}
		}

		return res;
	}

	public List<BloombergResultItem> executeRateCouponLoad(final Map<String, Long> items) {
		log.info("RateCouponLoad:");

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

						if (sd.hasElement("securityError")) {
							log.error(security + ", SecurityError:" + sd.getElement("securityError").getElementAsString("message"));
							continue;
						}

						final Element fieldData = sd.getElement("fieldData");

						final Element fsArray = fieldData.getElement("multi_cpn_schedule");
						for (int j = 0; j < fsArray.numValues(); ++j) {
							final Element fs = fsArray.getValueAsElement(j);

							final String date = fs.getElementAsString("Period End Date");
							final String value = fs.getElementAsString("Coupon");

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

		return info;
	}

	public List<BloombergResultItem> executeValuesLoad(final Map<String, Long> items) {
		log.info("ValuesLoad:");

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

						if (sd.hasElement("securityError")) {
							log.error(security + ", SecurityError:" + sd.getElement("securityError").getElementAsString("message"));
							continue;
						}

						final Element fieldData = sd.getElement("fieldData");

						final Element fsArray = fieldData.getElement("Factor_Schedule");
						for (int j = 0; j < fsArray.numValues(); ++j) {
							final Element fs = fsArray.getValueAsElement(j);

							final String date = fs.getElementAsString("Payment Date");
							final String value = fs.getElementAsString("Factor");

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

		return info;
	}

	public Map<String, Map<java.sql.Date, Double>> executeAtrLoad(Date date, List<String> securities) {
		log.info("AtrLoad:");

		// final List<String> securities = dao.getSecForAtr();

		final AtrRequest r = new AtrRequest(date, date, securities, AtrDataItem.algorithm, AtrDataItem.atr_period, AtrDataItem.period,
				AtrDataItem.calendar);

		r.execute("Jobber/Atr");

		return r.getAnswer();
	}

	public Map<String, Map<String, String>> executeBdpOverrideLoad(List<SecForJobRequest> securities) {
		log.info("BdpOverrideLoad:");

		// final List<SecForJobRequest> securities = dao.getLoadEstimatesPeersData();

		final BdpOverrideRequest r = new BdpOverrideRequest(securities);

		r.execute("Jobber/BDP override");

		return r.getAnswer();
	}

	public Map<String, Map<String, String>> executeBdpRequestOverride(String name,
			String[] securities, String[] fields, String period, String override) {
		log.info("BdpRequestOverride:" + name);

		final BdpRequestOverride r = new BdpRequestOverride(securities, fields, period, override);
		r.execute(name);
		return r.getAnswer();
	}

	public Map<String, Map<String, Map<String, String>>> executeBdpRequestOverrideQuarter(String name,
			String[] securities, String[] fields, String[] currencies, String over) {
		log.info("BdpRequestOverrideQuarter:" + name);

		final BdpRequestOverrideQuarter r = new BdpRequestOverrideQuarter(securities, fields, currencies, over);
		r.execute(name);
		return r.getAnswer();
	}

	public Map<String, Object> executeBdsRequest(String name, String[] securities, String[] fields) {
		log.info("BdsRequest:");

		final BdsRequest r = new BdsRequest(securities, fields);

		r.execute(name);

		Map<String, Object> res = new HashMap<>();

		Map<String, List<BEST_ANALYST_RECS_BULK>> ba = r.getBestAnalyst();
		res.put("BEST_ANALYST_RECS_BULK", ba);

		Map<String, List<EARN_ANN_DT_TIME_HIST_WITH_EPS>> ehwe = r.getEarnHistWithEps();
		res.put("EARN_ANN_DT_TIME_HIST_WITH_EPS", ehwe);

		Map<String, List<ERN_ANN_DT_AND_PER>> eap = r.getErnAnnDTandPer();
		res.put("ERN_ANN_DT_AND_PER", eap);

		Map<String, List<String>> pt = r.getPeerTicker();
		res.put("PeerTicker", pt);

		List<PeerData> pd = r.getPeersData();
		res.put("Peers", pd);

		return res;
	}

	public Map<String, Map<String, String>> executeBondsLoad(List<String> securities) {
		log.info("BondsLoad:");

		// final List<String> securities = dao.getSecForBonds();
		String[] fields = { "CHG_PCT_1D", "YLD_YTM_MID" };

		final BdpRequest r = new BdpRequest(toArray(securities), fields);

		r.execute("Jobber/Bonds");

		return r.getAnswer();
	}

	public Map<String, Map<String, Map<String, String>>> executeHistDataLoad(String date, List<SecForJobRequest> securities) {
		log.info("HistDataLoad:");

		// final List<SecForJobRequest> securities = dao.getSecForHistData();

		final Set<String> currencies = new HashSet<String>();
		for (SecForJobRequest item : securities) {
			currencies.add(item.iso);
		}

		final List<String> cursec = new ArrayList<String>();
		for (SecForJobRequest item : securities) {
			cursec.add(item.iso + item.code);
		}

		final String[] fields = { "EQY_WEIGHTED_AVG_PX", "PX_HIGH", "PX_LAST", "PX_LOW",
				"PX_VOLUME", "TOT_BUY_REC", "TOT_HOLD_REC", "TOT_SELL_REC" };

		final HistoricalDataRequest r = new HistoricalDataRequest(date, date, toArray(cursec), fields,
				toArray(currencies));

		r.execute("Jobber/HistData");

		return r.getAnswer();
	}

	public Map<String, Map<String, Map<String, String>>> executeQuotesLoad(String date, List<String> securities) {
		log.info("QuotesLoad:");

		// final List<String> securities = dao.getSecForQuotes();

		final HistoricalDataRequest r = new HistoricalDataRequest(date, date, toArray(securities),
				new String[] { "PX_LAST" });

		r.execute("Jobber/Quotes");

		return r.getAnswer();
	}

	public Map<String, Map<String, String>> executeFuturesLoad(List<SecurityItem> securities) {
		log.info("FuturesLoad:");

		// List<SecurityItem> securities = dao.getSecForUpdateFutures();

		final String[] _securities = new String[securities.size()];
		for (int i = 0; i < securities.size(); i++) {
			_securities[i] = securities.get(i).getCode();
		}
		final BdpRequest r = new BdpRequest(_securities, new String[] { "SECURITY_NAME", "NAME", "SHORT_NAME" });

		r.execute("Jobber/Update securities");

		return r.getAnswer();
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
	public Map<String, Map<String, String>> executeReferenceDataRequest(
			String name, String[] securities, String[] fields) {
		log.info("ReferenceDataRequest:" + name);

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
	public Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(
			String name, String startDate, String endDate, String[] securities, String[] fields) {
		log.info("HistoricalDataRequest:" + name);

		final HistoricalDataRequest r = new HistoricalDataRequest(startDate, endDate, securities, fields);
		r.execute(name);
		return r.getAnswer();
	}

	private String[] toArray(Collection<String> securities) {
		return securities.toArray(new String[securities.size()]);
	}

	public Map<String, Map<String, Map<String, String>>> executeBdhRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields) {
		log.info("BdhRequest:" + name);

		final BdhRequest r = new BdhRequest(dateStart, dateEnd, period, calendar, currencies, securities, fields);
		r.execute(name);
		return r.getAnswer();
	}

	public Map<String, Map<String, Map<String, String>>> executeBdhEpsRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields) {
		log.info("BdhEpsRequest:" + name);

		final BdhEpsRequest r = new BdhEpsRequest(dateStart, dateEnd, period, calendar, currencies, securities, fields);
		r.execute(name);
		return r.getAnswer();
	}

	public Map<String, String> executeFieldInfoRequest(String name, String code) {
		log.info("FieldInfoRequest:" + name);

		final FieldInfoRequest r = new FieldInfoRequest(code);
		r.execute(name);
		return r.getAnswer();
	}

	public List<Map<String, Object>> executeLoadCashFlowRequest(String name,
			Map<String, Long> ids, Map<String, String> dates) {
		log.info("LoadCashFlowRequest:" + name);

		CashFlowLoadRequest r = new CashFlowLoadRequest(ids, dates);
		r.execute(name);
		return r.getAnswer();
	}

	public List<Map<String, Object>> executeLoadCashFlowRequestNew(String name,
			Map<String, Long> ids, Map<String, String> dates) {
		log.info("LoadCashFlowRequest:" + name);

		CashFlowLoadRequestNew r = new CashFlowLoadRequestNew(ids, dates);
		r.execute(name);
		return r.getAnswer();
	}

	public List<Map<String, Object>> executeLoadValuesRequest(String name, Map<String, Long> ids) {
		log.info("LoadValuesRequest:" + name);

		ValuesLoadRequest r = new ValuesLoadRequest(ids);
		r.execute(name);
		return r.getAnswer();
	}

	public List<Map<String, Object>> executeLoadRateCouponRequest(String name, Map<String, Long> ids) {
		log.info("LoadRateCouponRequest:" + name);

		RateCouponLoadRequest r = new RateCouponLoadRequest(ids);
		r.execute(name);
		return r.getAnswer();
	}

	public List<Map<String, Object>> executeLoadAtrRequest(String name, String startDate, String endDate,
			String[] securities, String maType, Integer taPeriod, String period, String calendar) {
		log.info("LoadAtrRequest:" + name);

		AtrLoadRequest r = new AtrLoadRequest(startDate, endDate, securities, maType, taPeriod, period, calendar);
		r.execute(name);
		return r.getAnswer();
	}
}
