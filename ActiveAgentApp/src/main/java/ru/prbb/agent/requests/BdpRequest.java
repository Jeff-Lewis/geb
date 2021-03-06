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
 * BDP запрос
 * 
 * @author RBr
 */
public class BdpRequest extends BloombergRequest {

	private final String[] securities;
	private final String[] fields;

	private final Map<String, Map<String, String>> answer = new HashMap<String, Map<String, String>>();

	/**
	 * @return security -> { field, value }
	 */
	public Map<String, Map<String, String>> getAnswer() {
		return answer;
	}

	public BdpRequest(String[] securities, String[] fields) {
		this.securities = securities;
		this.fields = fields;
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

			sendRequest(request, new CorrelationID(answer));
		} finally {
			stop();
		}
	}

	@Override
	public void process(Message msg) {
		final Element arraySecurityData = msg.getElement("securityData");
		final int numItems = arraySecurityData.numValues();
		for (int i = 0; i < numItems; ++i) {
			final Element securityData = arraySecurityData.getValueAsElement(i);

			final String security = securityData.getElementAsString("security");

			final Map<String, String> values = new HashMap<String, String>();
			answer.put(security, values);

			if (securityData.hasElement("securityError")) {
				final String value = securityData.getElementAsString("securityError");
				log.error("SecurityError:" + value);
				values.put("securityError", value);
				continue;
			}

			final Element fieldData = securityData.getElement("fieldData");
			for (String field : fields) {
				if (fieldData.hasElement(field)) {
					try {
						final String value = fieldData.getElementAsString(field);
						values.put(field, value);
					} catch (Exception e) {
						log.error("Get field value", e);
					}
				}
			}
		}
	}
}
