package ru.prbb.activeagent.executors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskBdhEpsRequest;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;

public class TaskBdhEpsExecutor extends TaskExecutor {

	public TaskBdhEpsExecutor() {
		super(TaskBdhEpsRequest.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		taskData = mapper.readValue(data,
				new TypeReference<TaskBdhEpsRequest>() {
				});

		Session session = startSession();
		try {
			String serviceUri = "//blp/refdata";
			if (session.openService(serviceUri)) {
				Service service = session.getService(serviceUri);

				for (String crncy : taskData.getCurrencies()) {
					Request request = service.createRequest("HistoricalDataRequest");
					request.set("periodicitySelection", taskData.getPeriod());
					request.set("periodicityAdjustment", taskData.getCalendar());
					request.set("startDate", taskData.getDateStart());
					request.set("endDate", taskData.getDateEnd());
					request.set("currency", currency = crncy);
					request.set("returnEids", "true");
					request.set("returnRelativeDate", "true");

					final Element _securities = request.getElement("securities");
					for (String security : taskData.getSecurities()) {
						if (security.startsWith(crncy)) {
							_securities.appendValue(security.substring(crncy.length()));
						}
					}

					final Element _fields = request.getElement("fields");
					for (String p : taskData.getFields()) {
						_fields.appendValue(p);
					}

					sendRequest(session, request);
				}
			} else {
				throw new IOException("Unable to open service " + serviceUri);
			}
		} finally {
			session.stop();
		}

	}

	private String currency;
	private TaskBdhEpsRequest taskData;

	@Override
	protected void processMessage(Message msg) {
		final Element sd = msg.getElement("securityData");

		final String security = sd.getElementAsString("security");
		final Map<String, Map<String, String>> datevalues = new HashMap<>();

		final Element arrayFieldData = sd.getElement("fieldData");
		final int count = arrayFieldData.numValues();
		for (int i = 0; i < count; ++i) {
			final Element fieldData = arrayFieldData.getValueAsElement(i);

			final String date = fieldData.getElementAsString("date");
			final String relative_date = fieldData.getElementAsString("RELATIVE_DATE");
			final Map<String, String> values = new HashMap<>();
			datevalues.put(date, values);

			for (String field : taskData.getFields()) {
				if (fieldData.hasElement(field)) {
					try {
						String value = fieldData.getElementAsString(field) + ";"
								+ taskData.getPeriod() + ";" + relative_date + ";" + currency + ";" + taskData.getCalendar();
						values.put(field, value);
					} catch (Exception e) {
						logger.severe(e.getMessage());
						sendError(e.getMessage());
					}
				}
			}
		}

		send(currency + security, datevalues);
	}

	private void send(String security, Map<String, Map<String, String>> datevalues) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(security).append('\t');
			sb.append(mapper.writeValueAsString(datevalues));
			send(sb.toString());
		} catch (Exception e) {
			sendError(e.getMessage());
		}
	}
}
