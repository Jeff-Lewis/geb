/**
 * 
 */
package ru.prbb.agent.requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;

/**
 * @author RBr
 */
public class CashFlowLoadRequest extends BloombergRequest {

	private final Map<String, Long> ids;
	private final Map<String, String> dates;

	private final List<Map<String, Object>> answer = new ArrayList<>();

	/**
	 * @return security -> {date -> { field, value } )
	 */
	public List<Map<String, Object>> getAnswer() {
		return answer;
	}

	public CashFlowLoadRequest(Map<String, Long> ids, Map<String, String> dates) {
		this.ids = ids;
		this.dates = dates;
	}

	@Override
	public void execute(String name) {
		start(name);
		try {
			final Service service = getService("//blp/refdata");

			for (String security : ids.keySet()) {
				final Request request = service.createRequest("ReferenceDataRequest");

				final Element _securities = request.getElement("securities");
				_securities.appendValue(security);

				final Element _fields = request.getElement("fields");
				_fields.appendValue("DES_CASH_FLOW");

				final Element _overrides = request.getElement("overrides");
				final Element overrid = _overrides.appendElement();
				overrid.setElement("fieldId", "Settle_Dt");
				overrid.setElement("value", dates.get(security));

				sendRequest(request, new CorrelationID(answer));
			}
		} finally {
			stop();
		}
	}

	@Override
	public void process(Message msg) {
		final Element sdArray = msg.getElement("securityData");
		for (int i = 0; i < sdArray.numValues(); ++i) {
			final Element sd = sdArray.getValueAsElement(i);

			final String security = sd.getElementAsString("security");
			final Long id = ids.get(security);
			log.debug(security + ", id:" + id);

			if (sd.hasElement("securityError")) {
				log.error(security + ", SecurityError:"
						+ sd.getElement("securityError").getElementAsString("message"));
				continue;
			}

			final Element fieldData = sd.getElement("fieldData");

			final Element fsArray = fieldData.getElement("DES_CASH_FLOW");
			for (int j = 0; j < fsArray.numValues(); ++j) {
				final Element fs = fsArray.getValueAsElement(j);

				final String date = fs.getElementAsString("Payment Date");
				final Double value = fs.getElementAsFloat64("Coupon Amount");
				final Double value2 = fs.getElementAsFloat64("Principal Amount");

				if (log.isDebugEnabled()) {
					log.debug("id:" + id + ", date:" + date + ", value:" + value + ", value2:" + value2);
				}

				final Map<String, Object> map = new HashMap<>();
				answer.add(map);
				map.put("id_sec", id);
				map.put("security", security);
				map.put("date", date);
				map.put("value", value);
				map.put("value2", value2);
			}
		}
	}
}
