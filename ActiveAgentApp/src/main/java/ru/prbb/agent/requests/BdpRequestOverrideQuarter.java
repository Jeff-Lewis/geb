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
 * BDP с override квартальный
 * 
 * @author RBr
 */
public class BdpRequestOverrideQuarter extends BloombergRequest {

	private final String[] securities;
	private final String[] fields;
	private final String[] currency;
	private final String override;

	private final Map<String, Map<String, Map<String, String>>> answer = new HashMap<>();

	/**
	 * @return security -> [ period -> [ { field, value } ] ]
	 */
	public Map<String, Map<String, Map<String, String>>> getAnswer() {
		return answer;
	}

	public BdpRequestOverrideQuarter(String[] securities, String[] fields, String[] currency, String override) {
		this.securities = securities;
		this.fields = fields;
		this.currency = currency;
		this.override = ((null != override) && !override.isEmpty()) ? override : null;
	}

	private String period;

	@Override
	public void execute(String name) {
		start(name);
		try {
			final Service service = getService("//blp/refdata");

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

					if (null != override) {
						final Element overrideDataSource = overrides.appendElement();
						overrideDataSource.setElement("fieldId", "BEST_DATA_SOURCE_OVERRIDE");
						overrideDataSource.setElement("value", override);
					}

					sendRequest(request, new CorrelationID(answer));
				}
			}
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

			Map<String, Map<String, String>> pv;
			if (answer.containsKey(security)) {
				pv = answer.get(security);
			} else {
				pv = new HashMap<>();
				answer.put(security, pv);
			}
			final Map<String, String> values = new HashMap<>();
			pv.put(period, values);

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
						String value = fieldData.getElementAsString(field);
						values.put(field, value);
					} catch (Exception e) {
						log.error("Get field value", e);
					}
				}
			}
		}
	}
}
