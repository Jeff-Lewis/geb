package ru.prbb.activeagent.executors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskHistoricalDataRequest;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;

public class TaskHistoricalDataExecutor extends TaskExecutor {

	public TaskHistoricalDataExecutor() {
		super(TaskHistoricalDataRequest.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		taskData = mapper.readValue(data,
				new TypeReference<TaskHistoricalDataRequest>() {
				});

		Session session = startSession();
		try {
			String serviceUri = "//blp/refdata";
			if (session.openService(serviceUri)) {
				Service service = session.getService(serviceUri);

				if (null != taskData.getCurrencies()) {
					for (String c : taskData.getCurrencies()) {
						currency = c;

						Request request = service.createRequest("HistoricalDataRequest");

						request.set("periodicitySelection", "DAILY");
						request.set("periodicityAdjustment", "CALENDAR");
						request.set("startDate", taskData.getDateStart());
						request.set("endDate", taskData.getDateEnd());
						request.set("currency", currency);

						final Element _securities = request.getElement("securities");
						for (String security : taskData.getSecurities()) {
							if (security.startsWith(currency)) {
								_securities.appendValue(security.substring(currency.length()));
							}
						}

						final Element _fields = request.getElement("fields");
						for (String field : taskData.getFields()) {
							_fields.appendValue(field);
						}

						sendRequest(session, request);
					}
				} else {
					Request request = service.createRequest("HistoricalDataRequest");

					request.set("periodicitySelection", "DAILY");
					request.set("periodicityAdjustment", "CALENDAR");
					request.set("startDate", taskData.getDateStart());
					request.set("endDate", taskData.getDateEnd());

					final Element _securities = request.getElement("securities");
					for (String security : taskData.getSecurities()) {
						_securities.appendValue(security);
					}

					final Element _fields = request.getElement("fields");
					for (String field : taskData.getFields()) {
						_fields.appendValue(field);
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

	private String currency = null;
	private TaskHistoricalDataRequest taskData;

	@Override
	protected void processMessage(Message msg) {
		Map<String, Map<String, Map<String, String>>> answer = new HashMap<>();

		final Element securityData = msg.getElement("securityData");

		final String security = securityData.getElementAsString("security");

		final Map<String, Map<String, String>> datevalues = new HashMap<>();
		if (null != currency) {
			answer.put(currency + security, datevalues);
		} else {
			answer.put(security, datevalues);
		}

		final Element fieldData = securityData.getElement("fieldData");
		final int numValues = fieldData.numValues();
		for (int i = 0; i < numValues; i++) {
			final Element element = fieldData.getValueAsElement(i);

			final Map<String, String> values = new HashMap<String, String>();
			final String date = element.getElementAsString("date");
			datevalues.put(date, values);

			for (String field : taskData.getFields()) {
				if (element.hasElement(field)) {
					try {
						final String value = element.getElementAsString(field);
						values.put(field, value);
					} catch (Exception e) {
						logger.severe(e.getMessage());
					}
				}
			}
		}

		send(answer);
	}

	private void send(Map<String, Map<String, Map<String, String>>> answer) {
		try {
			send(mapper.writeValueAsString(answer));
		} catch (Exception e) {
			sendError(e.getMessage());
		}
	}
}
