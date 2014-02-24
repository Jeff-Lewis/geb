/**
 * 
 */
package ru.prbb.agent.service;

import java.text.SimpleDateFormat;
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
import ru.prbb.bloomberg.request.AtrRequest;
import ru.prbb.bloomberg.request.BdpOverrideRequest;
import ru.prbb.bloomberg.request.BdpRequest;
import ru.prbb.bloomberg.request.BdpRequestOverride;
import ru.prbb.bloomberg.request.BdpRequestOverrideQuarter;
import ru.prbb.bloomberg.request.BdsRequest;
import ru.prbb.bloomberg.request.BdsRequest.BEST_ANALYST_RECS_BULK;
import ru.prbb.bloomberg.request.BdsRequest.PeerData;
import ru.prbb.bloomberg.request.HistoricalDataRequest;
import ru.prbb.bloomberg.request.ReferenceDataRequest;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;

/**
 * @author RBr
 */
@Component
public final class BloombergServices/* implements BloombergServices*/{

	private final Log log = LogFactory.getLog(getClass());

	//@Override
	public Map<String, Map<Date, Map<String, String>>> executeBondYeildLoad(
			Date startDate, Date endDate, String[] securities) {
		log.info("BondYeildLoad:");

		String[] fields = { "YLD_CNV_MID" };
		final HistoricalDataRequest r = new HistoricalDataRequest(startDate, endDate, securities, fields);
		r.execute("Загрузка доходности облигаций");
		return r.getAnswer();
	}

	//@Override
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

	//@Override
	public List<BloombergResultItem> executeQuotesLoad(Date startDate, Date endDate, String[] securities) {
		log.info("QuotesLoad:");

		final String PX_LAST = "PX_LAST";

		final Map<String, Map<Date, Map<String, String>>> a = executeHistoricalDataRequest("Загрузка котировок", startDate, endDate,
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

		return res;
	}

	//@Override
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

	//@Override
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

	//@Override
	public Map<String, Map<java.sql.Date, Double>> executeAtrLoad(Date date, List<String> securities) {
		log.info("AtrLoad:");

		// final List<String> securities = dao.getSecForAtr();

		final AtrRequest r = new AtrRequest(date, date, securities, AtrDataItem.algorithm, AtrDataItem.atr_period, AtrDataItem.period,
				AtrDataItem.calendar);

		r.execute("Jobber/Atr");

		return r.getAnswer();
	}

	//@Override
	public Map<String, Map<String, String>> executeBdpOverrideLoad(List<SecForJobRequest> securities) {
		log.info("BdpOverrideLoad:");

		// final List<SecForJobRequest> securities = dao.getLoadEstimatesPeersData();

		final BdpOverrideRequest r = new BdpOverrideRequest(securities);

		r.execute("Jobber/BDP override");

		return r.getAnswer();
	}

	/**
	 * @param string
	 * @param securities
	 * @param fields
	 * @param currencies
	 * @param over
	 * @return
	 */
	public Map<String, Map<String, String>> executeBdpRequestOverride(String name,
			String[] securities, String[] fields, String period, String over) {
		final BdpRequestOverride r = new BdpRequestOverride(securities, fields, period, over);
		r.execute(name);
		return r.getAnswer();
	}

	/**
	 * @param string
	 * @param securities
	 * @param fields
	 * @param currencies
	 * @param over
	 * @return
	 */
	public Map<String, Map<String, String>> executeBdpRequestOverrideQuarter(String name,
			String[] securities, String[] fields, String[] currencies, String over) {
		final BdpRequestOverrideQuarter r = new BdpRequestOverrideQuarter(securities, fields, currencies, over);
		r.execute(name);
		return r.getAnswer();
	}

	//@Override
	public Map<String, Object> executeBdsRequest(String name, String[] securities, String[] fields) {
		log.info("BdsRequest:");

		final BdsRequest r = new BdsRequest(securities, fields);

		r.execute(name);

		List<PeerData> pd = r.getPeersData();
		Map<String, List<BEST_ANALYST_RECS_BULK>> ba = r.getBestAnalyst();
		Map<String, List<String>> pt = r.getPeerTicker();

		Map<String, Object> res = new HashMap<>();
		res.put("PeersData", pd);
		res.put("BestAnalyst", ba);
		res.put("PeerTicker", pt);
		return res;
	}

	//@Override
	public Map<String, Map<String, String>> executeBondsLoad(List<String> securities) {
		log.info("BondsLoad:");

		// final List<String> securities = dao.getSecForBonds();
		String[] fields = { "CHG_PCT_1D", "YLD_YTM_MID" };

		final BdpRequest r = new BdpRequest(toArray(securities), fields);

		r.execute("Jobber/Bonds");

		return r.getAnswer();
	}

	//@Override
	public Map<String, Map<Date, Map<String, String>>> executeHistDataLoad(Date date, List<SecForJobRequest> securities) {
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

	//@Override
	public Map<String, Map<Date, Map<String, String>>> executeQuotesLoad(Date date, List<String> securities) {
		log.info("QuotesLoad:");

		// final List<String> securities = dao.getSecForQuotes();

		final HistoricalDataRequest r = new HistoricalDataRequest(date, date, toArray(securities),
				new String[] { "PX_LAST" });

		r.execute("Jobber/Quotes");

		return r.getAnswer();
	}

	//@Override
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
	public Map<String, Map<Date, Map<String, String>>> executeHistoricalDataRequest(
			String name, Date startDate, Date endDate, String[] securities, String[] fields) {
		log.info("HistoricalDataRequest:" + name);

		final HistoricalDataRequest r = new HistoricalDataRequest(startDate, endDate, securities, fields);
		r.execute(name);
		return r.getAnswer();
	}

	private String[] toArray(Collection<String> securities) {
		return securities.toArray(new String[securities.size()]);
	}
}
