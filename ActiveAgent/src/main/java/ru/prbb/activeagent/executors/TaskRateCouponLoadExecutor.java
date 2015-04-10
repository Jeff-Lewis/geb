package ru.prbb.activeagent.executors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskRateCouponLoad;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;

public class TaskRateCouponLoadExecutor extends TaskExecutor {

	public TaskRateCouponLoadExecutor() {
		super(TaskRateCouponLoad.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		TaskRateCouponLoad taskData = mapper.readValue(data,
				new TypeReference<TaskRateCouponLoad>() {
				});

		for (String s : taskData.getIds()) {
			final int p = s.indexOf(';');
			final Long id = new Long(s.substring(0, p));
			final String name = s.substring(p + 1);

			ids.put(name, id);
		}

		Session session = startSession();
		try {
			String serviceUri = "//blp/refdata";
			if (session.openService(serviceUri)) {
				Service service = session.getService(serviceUri);

				final Request request = service.createRequest("ReferenceDataRequest");

				final Element _securities = request.getElement("securities");
				for (String security : ids.keySet()) {
					_securities.appendValue(security);
				}

				final Element _fields = request.getElement("fields");
				_fields.appendValue("multi_cpn_schedule");

				sendRequest(session, request);
			} else {
				throw new IOException("Unable to open service " + serviceUri);
			}
		} finally {
			session.stop();
		}
	}

	private final Map<String, Long> ids = new HashMap<>();

	@Override
	protected void processMessage(Message msg) {
		List<Map<String, String>> answer = new ArrayList<>();

		final Element sdArray = msg.getElement("securityData");
		for (int i = 0; i < sdArray.numValues(); ++i) {
			final Element sd = sdArray.getValueAsElement(i);

			final String security = sd.getElementAsString("security");
			final String id = ids.get(security).toString();
			logger.fine(security + ", id:" + id);

			if (sd.hasElement("securityError")) {
				logger.severe(security + ", SecurityError:"
						+ sd.getElement("securityError").getElementAsString("message"));
				continue;
			}

			final Element fieldData = sd.getElement("fieldData");

			final Element fsArray = fieldData.getElement("multi_cpn_schedule");
			for (int j = 0; j < fsArray.numValues(); ++j) {
				final Element fs = fsArray.getValueAsElement(j);

				final String date = fs.getElementAsString("Period End Date");
				final String value = fs.getElementAsString("Coupon");

				logger.fine("id:" + id + ", date:" + date + ", value:" + value);

				final Map<String, String> map = new HashMap<>();
				map.put("id_sec", id);
				map.put("security", security);
				map.put("date", date);
				map.put("value", value);

				answer.add(map);
			}
		}

		send(answer);
	}

	private void send(List<Map<String, String>> answer) {
		try {
			send(mapper.writeValueAsString(answer));
		} catch (Exception e) {
			sendError(e.getMessage());
		}
	}
}
