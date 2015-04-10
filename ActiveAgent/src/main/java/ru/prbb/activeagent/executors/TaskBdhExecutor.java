package ru.prbb.activeagent.executors;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskBdhRequest;
import ru.prbb.activeagent.tasks.TaskData;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

public class TaskBdhExecutor extends TaskExecutor {

	public TaskBdhExecutor() {
		super(TaskBdhRequest.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		TaskBdhRequest taskData = mapper.readValue(data,
				new TypeReference<TaskBdhRequest>() {
				});

		final SessionOptions sesOpt = new SessionOptions();
		sesOpt.setServerHost("localhost");
		sesOpt.setServerPort(8194);

		Session session = new Session(sesOpt);
		session.start();
		try {
			if (session.openService("//blp/refdata")) {
				Service service = session.getService("//blp/refdata");

				for (final String crncy : taskData.getCurrencies()) {
					final Request request = service.createRequest("HistoricalDataRequest");
					request.set("periodicitySelection", taskData.getPeriod());
					request.set("periodicityAdjustment", taskData.getCalendar());
					request.set("startDate", taskData.getDateStart());
					request.set("endDate", taskData.getDateEnd());
					request.set("currency", currency = crncy);

					final Element _securities = request.getElement("securities");
					for (String security : taskData.getSecurities()) {
						final int p = security.indexOf("|");
						if (security.substring(p + 1).equals(crncy)) {
							_securities.appendValue(security.substring(0, p));
						}
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

	private String currency;

	@Override
	protected void processMessage(TaskData data, Message msg) {
		TaskBdhRequest taskData = (TaskBdhRequest) data;

		final Element sd = msg.getElement("securityData");

		final String security = sd.getElementAsString("security");
		final Map<String, Map<String, String>> datevalues = new HashMap<>();

		final Element arrayFieldData = sd.getElement("fieldData");
		final int count = arrayFieldData.numValues();
		for (int i = 0; i < count; ++i) {
			final Element fieldData = arrayFieldData.getValueAsElement(i);

			final String date = fieldData.getElementAsString("date");
			final Map<String, String> values = new HashMap<>();
			datevalues.put(date, values);

			for (String field : taskData.getFields()) {
				if (fieldData.hasElement(field)) {
					try {
						String value = fieldData.getElementAsString(field) + ";"
								+ taskData.getPeriod() + ";" + currency + ";" + taskData.getCalendar();
						values.put(field, value);
					} catch (Exception e) {
						logger.severe(e.getMessage());
					}
				}
			}
		}

		// TODO answer.put(security + '|' + currency, datevalues);
	}

}
