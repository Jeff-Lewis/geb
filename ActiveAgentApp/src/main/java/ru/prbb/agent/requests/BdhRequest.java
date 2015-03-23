/**
 * 
 */
package ru.prbb.agent.requests;

import java.util.HashMap;
import java.util.Map;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;

/**
 * BDH запрос
 * 
 * @author RBr
 */
public class BdhRequest extends BloombergRequest {

	private final String dateStart;
	private final String dateEnd;
	private final String period;
	private final String calendar;
	private final String[] currencies;
	private final String[] securities;
	private final String[] fields;

	private final Map<String, Map<String, Map<String, String>>> answer = new HashMap<>();

	/**
	 * { security -> [ date -> [ {field -> value } ] ] }
	 */
	public Map<String, Map<String, Map<String, String>>> getAnswer() {
		return answer;
	}

	public BdhRequest(String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] security, String[] fields) {
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.period = period;
		this.calendar = calendar;
		this.currencies = currencies;
		this.securities = security;
		this.fields = fields;
	}

	private String currency;

	@Override
	public void execute(String name) {
		start(name);
		try {
			final Service service = getService("//blp/refdata");

			for (final String crncy : currencies) {
				final Request request = service.createRequest("HistoricalDataRequest");
				request.set("periodicitySelection", period);
				request.set("periodicityAdjustment", calendar);
				request.set("startDate", dateStart);
				request.set("endDate", dateEnd);
				request.set("currency", currency = crncy);

				final Element _securities = request.getElement("securities");
				for (String security : securities) {
					final int p = security.indexOf("|");
					if (security.substring(p + 1).equals(crncy)) {
						_securities.appendValue(security.substring(0, p));
					}
				}

				final Element _fields = request.getElement("fields");
				for (String field : fields) {
					_fields.appendValue(field);
				}

				sendRequest(request, new CorrelationID(answer));
			}
		} finally {
			stop();
		}
	}

	@Override
	public void process(Message msg) {
		final Element sd = msg.getElement("securityData");

		final String security = sd.getElementAsString("security");
		final Map<String, Map<String, String>> datevalues = new HashMap<>();
		answer.put(security + '|' + currency, datevalues);

		final Element arrayFieldData = sd.getElement("fieldData");
		final int count = arrayFieldData.numValues();
		for (int i = 0; i < count; ++i) {
			final Element fieldData = arrayFieldData.getValueAsElement(i);

			final String date = fieldData.getElementAsString("date");
			final Map<String, String> values = new HashMap<>();
			datevalues.put(date, values);

			for (String field : fields) {
				if (fieldData.hasElement(field)) {
					try {
						String value = fieldData.getElementAsString(field) + ";"
								+ period + ";" + currency + ";" + calendar;
						values.put(field, value);
					} catch (Exception e) {
						log.error("Get field value", e);
					}
				}
			}
		}
	}
}
