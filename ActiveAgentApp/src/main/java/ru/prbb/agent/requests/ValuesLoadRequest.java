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
public class ValuesLoadRequest extends BloombergRequest {

	private final Map<String, Long> ids;

	private final List<Map<String, Object>> answer = new ArrayList<>();

	/**
	 * @return security -> {date -> { field, value } )
	 */
	public List<Map<String, Object>> getAnswer() {
		return answer;
	}

	public ValuesLoadRequest(Map<String, Long> ids) {
		this.ids = ids;
	}

	@Override
	public void execute(String name) {
		start(name);
		try {
			final Service service = getService("//blp/refdata");

			final Request request = service.createRequest("ReferenceDataRequest");

			final Element _securities = request.getElement("securities");
			for (String security : ids.keySet()) {
				_securities.appendValue(security);
			}

			final Element _fields = request.getElement("fields");
			_fields.appendValue("Factor_Schedule");

			sendRequest(request, new CorrelationID(answer));
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

			final Element fsArray = fieldData.getElement("Factor_Schedule");
			for (int j = 0; j < fsArray.numValues(); ++j) {
				final Element fs = fsArray.getValueAsElement(j);

				final String date = fs.getElementAsString("Payment Date");
				final Double value = fs.getElementAsFloat64("Factor");

				if (log.isDebugEnabled()) {
					log.debug("id:" + id + ", date:" + date + ", value:" + value);
				}

				final Map<String, Object> map = new HashMap<>();
				answer.add(map);
				map.put("id_sec", id);
				map.put("security", security);
				map.put("date", date);
				map.put("value", value);
			}
		}
	}
}
