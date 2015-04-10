package ru.prbb.activeagent.executors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskReferenceDataRequest;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;

public class TaskReferenceDataExecutor extends TaskExecutor {

	private TaskReferenceDataRequest taskData;

	public TaskReferenceDataExecutor() {
		super(TaskReferenceDataRequest.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		taskData = mapper.readValue(data,
				new TypeReference<TaskReferenceDataRequest>() {
				});

		Session session = startSession();
		try {
			String serviceUri = "//blp/refdata";
			if (session.openService(serviceUri)) {
				Service service = session.getService(serviceUri);

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

				sendRequest(session, request);
			} else {
				throw new IOException("Unable to open service " + serviceUri);
			}
		} finally {
			session.stop();
		}
	}

	@Override
	protected void processMessage(Message msg) {
		Map<String, Map<String, String>> answer = new HashMap<String, Map<String, String>>();

		final Element element = msg.asElement();
		final Element securityDataArray = element.getElement("securityData");
		final int numItems = securityDataArray.numValues();
		for (int i = 0; i < numItems; ++i) {
			final Element securityData = securityDataArray.getValueAsElement(i);

			final String security = securityData.getElementAsString("security");
			logger.fine(security);

			final Map<String, String> values = new HashMap<>();
			answer.put(security, values);

			if (securityData.hasElement("securityError")) {
				final Element securityError = securityData.getElement("securityError");
				final String value = securityError.getElementAsString("message");
				values.put("securityError", value);
			} else {
				final Element fieldData = securityData.getElement("fieldData");
				for (String field : taskData.getFields()) {
					if (fieldData.hasElement(field)) {
						final String value = fieldData.getElementAsString(field);
						values.put(field, value);
					}
				}
			}
		}

		send(answer);
	}

	private void send(Map<String, Map<String, String>> answer) {
		try {
			send(mapper.writeValueAsString(answer));
		} catch (Exception e) {
			sendError(e.getMessage());
		}
	}
}
