/**
 * 
 */
package ru.prbb.bloomberg.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.prbb.bloomberg.core.BloombergRequest;
import ru.prbb.bloomberg.core.BloombergSession;
import ru.prbb.bloomberg.core.MessageHandler;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;

/**
 * @author RBr
 * 
 */
public class HistoricalDataRequest implements BloombergRequest, MessageHandler {
	private static final Log log = LogFactory.getLog(HistoricalDataRequest.class);

	private final String startDate;
	private final String endDate;
	private final String[] securities;
	private final String[] fields;
	private final String[] currencies;

	private final Map<String, Map<String, Map<String, String>>> answer = new HashMap<>();

	/**
	 * @return
	 *         security -> {date -> { field, value } )
	 */
	public Map<String, Map<String, Map<String, String>>> getAnswer() {
		return answer;
	}

	/**
	 * 
	 * @param date
	 * @param securities
	 *            code
	 * @param fields
	 */
	public HistoricalDataRequest(String startDate, String endDate, String[] securities, String[] fields) {
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
	public HistoricalDataRequest(String startDate, String endDate, String[] securities, String[] fields, String[] currencies) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.securities = securities;
		this.fields = fields;
		this.currencies = currencies;
	}

	private String currency = null;

	@Override
	public void execute(String name) {
		final BloombergSession bs = new BloombergSession(name);
		bs.start();
		try {
			if (null != currencies) {
				for (String c : currencies) {
					currency = c;

					final Request request = bs.createRequest("//blp/refdata", "HistoricalDataRequest");
					request.set("periodicitySelection", "DAILY");
					request.set("periodicityAdjustment", "CALENDAR");
					request.set("startDate", startDate);
					request.set("endDate", endDate);
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
				request.set("startDate", startDate);
				request.set("endDate", endDate);

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

		final Map<String, Map<String, String>> datevalues = new HashMap<>();
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
			final String date = element.getElementAsString("date");
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
