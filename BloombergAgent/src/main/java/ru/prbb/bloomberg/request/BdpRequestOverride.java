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
 * BDP с override
 * 
 * @author RBr
 * 
 */
public class BdpRequestOverride implements BloombergRequest, MessageHandler {
	private static final Log log = LogFactory.getLog(BdpRequestOverride.class);

	private final String[] securities;
	private final String[] fields;
	private final String period;
	private final String over;

	private final Map<String, Map<String, String>> answer = new HashMap<String, Map<String, String>>();

	/**
	 * @return
	 *         security -> { field, value }
	 */
	public Map<String, Map<String, String>> getAnswer() {
		return answer;
	}

	public BdpRequestOverride(String[] securities, String[] fields, String period, String over) {
		this.securities = securities;
		this.fields = fields;
		this.period = period;
		this.over = over;
	}

	@Override
	public void execute(String name) {
		final BloombergSession bs = new BloombergSession(name);
		bs.start();
		try {
			final Request request = bs.createRequest("//blp/refdata", "ReferenceDataRequest");

			final Element overrides = request.getElement("overrides");

			final Element _securities = request.getElement("securities");
			for (String security : securities) {
				final int p = security.indexOf("|");

				_securities.appendValue(security.substring(0, p));

				final Element overrid = overrides.appendElement();
				overrid.setElement("fieldId", "EQY_FUND_CRNCY");
				overrid.setElement("value", security.substring(p + 1));
			}

			final Element _fields = request.getElement("fields");
			for (String field : fields) {
				_fields.appendValue(field);
			}

			final Element overridPeriod = overrides.appendElement();
			overridPeriod.setElement("fieldId", "BEST_FPERIOD_OVERRIDE");
			overridPeriod.setElement("value", period);

			bs.sendRequest(request, this);
		} finally {
			bs.stop();
		}
	}

	@Override
	public void processMessage(Message msg) {
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
						String value = fieldData.getElementAsString(field) + ";" + period + ";" + over;
						values.put(field, value);
					} catch (Exception e) {
						log.error(e);
					}
				}
			}
		}
	}
}
