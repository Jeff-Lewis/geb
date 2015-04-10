package ru.prbb.activeagent.executors;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskData;
import ru.prbb.activeagent.tasks.TaskHistoricalDataRequest;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

public class TaskHistoricalDataExecutor extends TaskExecutor {

	public TaskHistoricalDataExecutor() {
		super(TaskHistoricalDataRequest.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data)throws Exception {
		TaskHistoricalDataRequest taskData = mapper.readValue(data,
				new TypeReference<TaskHistoricalDataRequest>() {
				});

		final SessionOptions sesOpt = new SessionOptions();
		sesOpt.setServerHost("localhost");
		sesOpt.setServerPort(8194);

		Session session = new Session(sesOpt);
		session.start();
		try {
			if (session.openService("//blp/refdata")) {
				Service service = session.getService("//blp/refdata");

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

						sendRequest(taskData, session, request);
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

					sendRequest(taskData, session, request);
				}
			}
		} finally {
			session.stop();
		}
	}

	private String currency = null;

	@Override
	protected void processMessage(TaskData data, Message msg) {
		TaskHistoricalDataRequest taskData =  (TaskHistoricalDataRequest) data;
		final Element securityData = msg.getElement("securityData");

		final String security = securityData.getElementAsString("security");

		final Map<String, Map<String, String>> datevalues = new HashMap<>();
		if (null != currency) {
			// TODO answer.put(currency + security, datevalues);
		} else {
			// TODO answer.put(security, datevalues);
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
	}

}
