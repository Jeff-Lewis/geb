/**
 * 
 */
package ru.prbb.jobber.bloomberg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.prbb.jobber.domain.SecForJobRequest;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;

/**
 * @author RBr
 * 
 */
public class BdpOverrideRequest implements BloombergRequest, MessageHandler {
	public static final String BEST_EPS_GAAP = "BEST_EPS_GAAP";

	private static final Log log = LogFactory.getLog(BdpOverrideRequest.class);

	private final List<SecForJobRequest> securities;

	private final Map<String, Map<String, String>> answer = new HashMap<String, Map<String, String>>();

	/**
	 * @return
	 *         security -> { period, value }
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

		final BloombergSession bs = new BloombergSession(name);
		bs.start();
		try {
			for (String crncy : currencies) {
				for (String period : periods) {
					final Request request = bs.createRequest("//blp/refdata", "ReferenceDataRequest");

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

					bs.sendRequest(request, this, new CorrelationID(1));
				}
			}
		} finally {
			bs.stop();
		}
	}

	@Override
	public void processMessage(Message message) {
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

			String value = BloombergSession.getElementAsString(fieldData, BEST_EPS_GAAP);

			values.put(period, value);
		}
	}
}
