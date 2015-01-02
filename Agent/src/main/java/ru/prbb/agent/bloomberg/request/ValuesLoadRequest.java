/**
 * 
 */
package ru.prbb.agent.bloomberg.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.prbb.agent.bloomberg.BloombergRequest;
import ru.prbb.agent.bloomberg.BloombergSession;
import ru.prbb.agent.bloomberg.MessageHandler;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;

/**
 * @author RBr
 */
public class ValuesLoadRequest implements BloombergRequest, MessageHandler {

	private final Logger log = LoggerFactory.getLogger(getClass());

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
		final BloombergSession bs = new BloombergSession(name);
		bs.start();
		try {
			final Service service = bs.getService("//blp/refdata");

			final Request request = service.createRequest("ReferenceDataRequest");

			final Element _securities = request.getElement("securities");
			for (String security : ids.keySet()) {
				_securities.appendValue(security);
			}

			final Element _fields = request.getElement("fields");
			_fields.appendValue("Factor_Schedule");

			bs.sendRequest(request, this);
		} finally {
			bs.stop();
		}
	}

	@Override
	public void processMessage(Message msg) {
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
