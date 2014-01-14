/**
 * 
 */
package ru.prbb.jobber.bloomberg;

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
public class ReferenceDataRequest implements BloombergRequest, MessageHandler {
	private static final Log log = LogFactory.getLog(ReferenceDataRequest.class);

	private final String[] securities;
	private final String[] fields;
	private final Map<String, String> overrides;

	private final Map<String, Map<String, String>> answer = new HashMap<String, Map<String, String>>();

	/**
	 * @return
	 *         security -> { field, value }
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
		final Element securityData = msg.getElement("securityData");

		final String security = securityData.getElementAsString("security");
		log.debug(security);

		final Map<String, String> values = new HashMap<String, String>();
		answer.put(security, values);

		final Element fieldData = securityData.getElement("fieldData");
		final int numValues = fieldData.numValues();
		for (int i = 0; i < numValues; i++) {
			final Element element = fieldData.getValueAsElement(i);
			for (String field : fields) {
				try {
					if (element.hasElement(field)) {
						final String value = element.getElementAsString(field);
						values.put(field, value);
					}
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}
}
