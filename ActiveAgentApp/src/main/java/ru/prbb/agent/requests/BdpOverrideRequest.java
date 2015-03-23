/**
 * 
 */
package ru.prbb.agent.requests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.prbb.agent.model.SecForJobRequest;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;

/**
 * @author RBr
 */
public class BdpOverrideRequest extends BloombergRequest {
	public static final String BEST_EPS_GAAP = "BEST_EPS_GAAP";

	private final List<SecForJobRequest> securities;

	private final Map<String, Map<String, String>> answer = new HashMap<String, Map<String, String>>();

	/**
	 * @return security -> { period, value }
	 */
	public Map<String, Map<String, String>> getAnswer() {
		return answer;
	}

	public BdpOverrideRequest(List<SecForJobRequest> info) {
		this.securities = info;
	}

	private static final String periods[] = { "1CQ", "2CQ", "3CQ", "4CQ", "1CS", "2CS", "1CY", "2CY" };
	private String period;

	@Override
	public void execute(String name) {
		final Set<String> currencies = new HashSet<String>();
		for (SecForJobRequest item : securities) {
			currencies.add(item.iso);
		}

		start(name);
		try {
			for (String crncy : currencies) {
				for (String period : periods) {
					final Request request = createRequest("//blp/refdata", "ReferenceDataRequest");

					final Element _securities = request.getElement("securities");
					for (SecForJobRequest item : securities) {
						if (crncy.equals(item.iso)) {
							_securities.appendValue(item.code);
						}
					}

					final Element fields = request.getElement("fields");
					fields.appendValue(BEST_EPS_GAAP);

					final Element overrides = request.getElement("overrides");

					Element overridePeriod = overrides.appendElement();
					overridePeriod.setElement("fieldId", "BEST_FPERIOD_OVERRIDE");
					overridePeriod.setElement("value", this.period = period);

					Element overrideCrncy = overrides.appendElement();
					overrideCrncy.setElement("fieldId", "EQY_FUND_CRNCY");
					overrideCrncy.setElement("value", crncy);

					sendRequest(request, new CorrelationID(answer));
				}
			}
		} finally {
			stop();
		}
	}

	@Override
	public void process(Message message) {
		Element ReferenceDataResponse = message.asElement();
		Element securityDataArray = ReferenceDataResponse.getElement("securityData");
		int numItems = securityDataArray.numValues();
		for (int i = 0; i < numItems; ++i) {
			Element securityData = securityDataArray.getValueAsElement(i);
			String security = securityData.getElementAsString("security");

			final Map<String, String> values;
			if (answer.containsKey(security)) {
				values = answer.get(security);
			} else {
				values = new HashMap<String, String>();
				answer.put(security, values);
			}

			if (securityData.hasElement("securityError")) {
				log.error("SecurityError:" + securityData.getElement("securityError"));
				continue;
			}

			Element fieldData = securityData.getElement("fieldData");

			String value = BloombergRequest.getElementAsString(fieldData, BEST_EPS_GAAP);

			values.put(period, value);
		}
	}
}
