package ru.prbb.activeagent.executors;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskCashFlowLoadNew;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;

public class TaskCashFlowLoadNewExecutor extends TaskExecutor {

	public TaskCashFlowLoadNewExecutor() {
		super(TaskCashFlowLoadNew.class.getSimpleName());
	}

	private static final String FIELD = "DES_CASH_FLOW";

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		TaskCashFlowLoadNew taskData = mapper.readValue(data,
				new TypeReference<TaskCashFlowLoadNew>() {
				});

		Calendar cYear10 = Calendar.getInstance();
		cYear10.add(Calendar.YEAR, -10);
		cYear10.set(Calendar.MONTH, Calendar.JANUARY);
		cYear10.set(Calendar.DAY_OF_MONTH, 1);
		String dateStartYear = new SimpleDateFormat("yyyyMMdd").format(cYear10.getTime());

		for (String s : taskData.getIds()) {
			final int p = s.indexOf(';');
			final Long id = new Long(s.substring(0, p));
			final String name = s.substring(p + 1);

			ids.put(name, id);
		}

		final Map<String, String> dates = new HashMap<>();

		for (String s : taskData.getDates()) {
			final int p = s.indexOf(';');
			final String date = s.substring(0, p);
			final String name = s.substring(p + 1);

			dates.put(name, date);
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
				_fields.appendValue(FIELD);

				final Element _overrides = request.getElement("overrides");
				final Element overrid = _overrides.appendElement();
				overrid.setElement("fieldId", "USER_LOCAL_TRADE_DATE");
				overrid.setElement("value", dateStartYear);

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

			final Element fsArray = fieldData.getElement(FIELD);
			for (int j = 0; j < fsArray.numValues(); ++j) {
				final Element fs = fsArray.getValueAsElement(j);

				final String date = fs.getElementAsString("Payment Date");
				final String value = fs.getElementAsString("Coupon Amount");
				final String value2 = fs.getElementAsString("Principal Amount");

				logger.fine("id:" + id + ", date:" + date + ", value:" + value + ", value2:" + value2);

				final Map<String, String> map = new HashMap<>();
				map.put("id_sec", id);
				map.put("security", security);
				map.put("date", date);
				map.put("value", value);
				map.put("value2", value2);

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
