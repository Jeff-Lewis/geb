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

/**
 * @author RBr
 */
public class ReferenceDataRequest extends BloombergRequest {

	private final String[] securities;
	private final String[] fields;
	private final Map<String, String> overrides;

	private final Map<String, Map<String, String>> answer = new HashMap<String, Map<String, String>>();

	/**
	 * @return security -> { field, value }
	 */
	public Map<String, Map<String, String>> getAnswer() {
		return answer;
	}

	public ReferenceDataRequest(String[] securities, String[] fields) {
		this(securities, fields, null);
	}

	public ReferenceDataRequest(String[] securities, String[] fields, Map<String, String> overrides) {
		this.securities = securities;
		this.fields = fields;
		this.overrides = overrides;
	}

	@Override
	public void execute(String name) {
		start(name);
		try {
			final Request request = createRequest("//blp/refdata", "ReferenceDataRequest");

			final Element _securities = request.getElement("securities");
			for (String security : securities) {
				_securities.appendValue(security);
			}

			final Element _fields = request.getElement("fields");
			for (String field : fields) {
				_fields.appendValue(field);
			}

			if (null != overrides) {
				final Element _overrides = request.getElement("overrides");
				for (String fieldId : overrides.keySet()) {
					final Element override = _overrides.appendElement();
					override.setElement("fieldId", fieldId);
					override.setElement("value", overrides.get(fieldId));
				}
			}

			sendRequest(request, new CorrelationID(answer));
		} finally {
			stop();
		}
	}

	@Override
	public void process(Message msg) {
		final Element element = msg.asElement();
		final Element securityDataArray = element.getElement("securityData");
		final int numItems = securityDataArray.numValues();
		for (int i = 0; i < numItems; ++i) {
			final Element securityData = securityDataArray.getValueAsElement(i);

			final String security = securityData.getElementAsString("security");
			log.trace(security);

			final Map<String, String> values = new HashMap<>();
			answer.put(security, values);

			if (securityData.hasElement("securityError")) {
				final Element securityError = securityData.getElement("securityError");
				final String value = securityError.getElementAsString("message");
				values.put("securityError", value);
			} else {
				final Element fieldData = securityData.getElement("fieldData");
				for (String field : fields) {
					if (fieldData.hasElement(field)) {
						final String value = BloombergRequest.getElementAsString(fieldData, field);
						values.put(field, value);
					}
				}
			}
		}
	}
}
