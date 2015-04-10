package ru.prbb.activeagent.executors;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskData;
import ru.prbb.activeagent.tasks.TaskReferenceDataRequest;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

public class TaskReferenceDataExecutor extends TaskExecutor {

	public TaskReferenceDataExecutor() {
		super(TaskReferenceDataRequest.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		TaskReferenceDataRequest taskData = mapper.readValue(data,
				new TypeReference<TaskReferenceDataRequest>() {
				});

		final SessionOptions sesOpt = new SessionOptions();
		sesOpt.setServerHost("localhost");
		sesOpt.setServerPort(8194);

		Session session = new Session(sesOpt);
		session.start();
		try {
			if (session.openService("//blp/refdata")) {
				Service service = session.getService("//blp/refdata");

				final Request request = service.createRequest("ReferenceDataRequest");

				final Element _securities = request.getElement("securities");
				for (String security : taskData.getSecurities()) {
					_securities.appendValue(security);
				}

				final Element _fields = request.getElement("fields");
				for (String field : taskData.getFields()) {
					_fields.appendValue(field);
				}

				Map<String, String> overrides = null;
				if (null != overrides) {
					final Element _overrides = request.getElement("overrides");
					for (String fieldId : overrides.keySet()) {
						final Element override = _overrides.appendElement();
						override.setElement("fieldId", fieldId);
						override.setElement("value", overrides.get(fieldId));
					}
				}

				sendRequest(taskData, session, request);
			}
		} finally {
			session.stop();
		}
	}

	@Override
	protected void processMessage(TaskData data, Message msg) {
		TaskReferenceDataRequest taskData = (TaskReferenceDataRequest) data;
		final Element element = msg.asElement();
		final Element securityDataArray = element.getElement("securityData");
		final int numItems = securityDataArray.numValues();
		for (int i = 0; i < numItems; ++i) {
			final Element securityData = securityDataArray.getValueAsElement(i);

			final String security = securityData.getElementAsString("security");
			logger.fine(security);

			final Map<String, String> values = new HashMap<>();
			// TODO answer.put(security, values);

			if (securityData.hasElement("securityError")) {
				final Element securityError = securityData.getElement("securityError");
				final String value = securityError.getElementAsString("message");
				values.put("securityError", value);
			} else {
				final Element fieldData = securityData.getElement("fieldData");
				for (String field : taskData.getFields()) {
					if (fieldData.hasElement(field)) {
						final String value = getElementAsString(fieldData, field);
						values.put(field, value);
					}
				}
			}
		}
	}

}
