/**
 * 
 */
package ru.prbb.bloomberg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;

/**
 * @author RBr
 * 
 */
public class HistoricalDataRequest implements BloombergRequest, MessageHandler {
	private static final Log log = LogFactory.getLog(HistoricalDataRequest.class);

	private final Date startDate;
	private final Date endDate;
	private final String[] securities;
	private final String[] fields;
	private final String[] currencies;

	private final Map<String, Map<Date, Map<String, String>>> answer = new HashMap<String, Map<Date, Map<String, String>>>();

	/**
	 * @return
	 *         security -> {date -> { field, value } )
	 */
	public Map<String, Map<Date, Map<String, String>>> getAnswer() {
		return answer;
	}

	/**
	 * 
	 * @param date
	 * @param securities
	 *            code
	 * @param fields
	 */
	public HistoricalDataRequest(Date startDate, Date endDate, String[] securities, String[] fields) {
		this(startDate, endDate, securities, fields, null);
	}

	/**
	 * 
	 * @param date
	 * @param securities
	 *            currency + code
	 * @param fields
	 * @param currencies
	 */
	public HistoricalDataRequest(Date startDate, Date endDate, String[] securities, String[] fields, String[] currencies) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.securities = securities;
		this.fields = fields;
		this.currencies = currencies;
	}

	private String currency = null;

	@Override
	public void execute(String name) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		final String sd = sdf.format(startDate.getTime());
		final String ed = sdf.format(endDate.getTime());

		final BloombergSession bs = new BloombergSession(name);
		bs.start();
		try {
			if (null != currencies) {
				for (String c : currencies) {
					currency = c;

					final Request request = bs.createRequest("//blp/refdata", "HistoricalDataRequest");
					request.set("periodicitySelection", "DAILY");
					request.set("periodicityAdjustment", "CALENDAR");
					request.set("startDate", sd);
					request.set("endDate", ed);
					request.set("currency", currency);

					final Element _securities = request.getElement("securities");
					for (String security : securities) {
						if (security.startsWith(currency)) {
							_securities.appendValue(security.substring(currency.length()));
						}
					}

					final Element _fields = request.getElement("fields");
					for (String field : fields) {
						_fields.appendValue(field);
					}

					bs.sendRequest(request, this);
				}
			} else {
				final Request request = bs.createRequest("//blp/refdata", "HistoricalDataRequest");
				request.set("periodicitySelection", "DAILY");
				request.set("periodicityAdjustment", "CALENDAR");
				request.set("startDate", sd);
				request.set("endDate", ed);

				final Element _securities = request.getElement("securities");
				for (String security : securities) {
					_securities.appendValue(security);
				}

				final Element _fields = request.getElement("fields");
				for (String field : fields) {
					_fields.appendValue(field);
				}

				bs.sendRequest(request, this);
			}
		} finally {
			bs.stop();
		}
	}

	@Override
	public void processMessage(Message msg) {
		final Element securityData = msg.getElement("securityData");

		final String security = securityData.getElementAsString("security");

		final Map<Date, Map<String, String>> datevalues = new HashMap<Date, Map<String, String>>();
		if (null != currency) {
			answer.put(currency + security, datevalues);
		} else {
			answer.put(security, datevalues);
		}

		final Element fieldData = securityData.getElement("fieldData");
		final int numValues = fieldData.numValues();
		for (int i = 0; i < numValues; i++) {
			final Element element = fieldData.getValueAsElement(i);

			final Map<String, String> values = new HashMap<String, String>();
			final Date date = element.getElementAsDate("date").calendar().getTime();
			datevalues.put(date, values);

			for (String field : fields) {
				if (element.hasElement(field)) {
					try {
						final String value = element.getElementAsString(field);
						values.put(field, value);
					} catch (Exception e) {
						log.error(e);
					}
				}
			}
		}
	}
}
