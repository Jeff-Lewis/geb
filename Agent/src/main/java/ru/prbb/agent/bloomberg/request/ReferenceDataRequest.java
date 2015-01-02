/**
 * 
 */
package ru.prbb.agent.bloomberg.request;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.prbb.agent.bloomberg.BloombergRequest;
import ru.prbb.agent.bloomberg.BloombergSession;
import ru.prbb.agent.bloomberg.MessageHandler;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;

/**
 * @author RBr
 */
public class ReferenceDataRequest implements BloombergRequest, MessageHandler {

	private final Logger log = LoggerFactory.getLogger(getClass());

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
		final BloombergSession bs = new BloombergSession(name);
		bs.start();
		try {
			final Request request = bs.createRequest("//blp/refdata", "ReferenceDataRequest");

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

			bs.sendRequest(request, this);
		} finally {
			bs.stop();
		}
	}

	@Override
	public void processMessage(Message msg) {
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
						final String value = BloombergSession.getElementAsString(fieldData, field);
						values.put(field, value);
					}
				}
			}
		}
	}
}
