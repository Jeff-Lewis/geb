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
import com.bloomberglp.blpapi.Service;

/**
 * BDP с override квартальный
 * 
 * @author RBr
 * 
 */
public class BdpRequestOverrideQuarter implements BloombergRequest, MessageHandler {
	private static final Log log = LogFactory.getLog(BdpRequestOverrideQuarter.class);


	private final String[] securities;
	private final String[] fields;
	private final String[] currency;
	private final String over;

	private final Map<String, Map<String, String>> answer = new HashMap<String, Map<String, String>>();

	/**
	 * @return
	 *         security -> { field, value }
	 */
	public Map<String, Map<String, String>> getAnswer() {
		return answer;
	}

	public BdpRequestOverrideQuarter(String[] securities, String[] fields, String[] currency, String over) {
		this.securities = securities;
		this.fields = fields;
		this.currency = currency;
		this.over = ((null != over) && !over.isEmpty()) ? over : null;
	}

	private String period;

	@Override
	public void execute(String name) {
		final BloombergSession bs = new BloombergSession(name);
		bs.start();
		try {
			final Service service = bs.getService("//blp/refdata");

			for (String crncy : currency) {

				final String idq[] = { "1CQ", "2CQ", "3CQ", "4CQ", "1CS", "2CS", "1CY", "2CY" };
				for (final String quarter : idq) {
					final Request request = service.createRequest("ReferenceDataRequest");

					final Element _securities = request.getElement("securities");
					for (String s : securities) {
						final int p = s.indexOf('|');
						if (crncy.equals(s.substring(p + 1))) {
							_securities.appendValue(s.substring(0, p));
						}
					}

					final Element _fields = request.getElement("fields");
					for (String field : fields) {
						_fields.appendValue(field);
					}

					final Element overrides = request.getElement("overrides");

					final Element overridePeriod = overrides.appendElement();
					overridePeriod.setElement("fieldId", "BEST_FPERIOD_OVERRIDE");
					overridePeriod.setElement("value", period = quarter);

					final Element overrideCrncy = overrides.appendElement();
					overrideCrncy.setElement("fieldId", "EQY_FUND_CRNCY");
					overrideCrncy.setElement("value", crncy);

					if (null != over) {
						final Element overrideDataSource = overrides.appendElement();
						overrideDataSource.setElement("fieldId", "BEST_DATA_SOURCE_OVERRIDE");
						overrideDataSource.setElement("value", over);
					}

					bs.sendRequest(request, this);
				}
			}
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

			values.put("PERIOD", period);
			values.put("OVERRIDE", over);

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
