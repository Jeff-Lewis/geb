package ru.prbb.activeagent.executors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskFieldInfoRequest;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;

public class TaskFieldInfoExecutor extends TaskExecutor {

	public TaskFieldInfoExecutor() {
		super(TaskFieldInfoRequest.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		TaskFieldInfoRequest taskData = mapper.readValue(data,
				new TypeReference<TaskFieldInfoRequest>() {
				});

		Session session = startSession();
		try {
			String serviceUri = "//blp/apiflds";
			if (session.openService(serviceUri)) {
				Service service = session.getService(serviceUri);

				final Request request = service.createRequest("FieldInfoRequest");
				request.append("id", taskData.getCode());
				request.set("returnFieldDocumentation", false);

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
		final Element fields = msg.getElement("fieldData");
		final int numElements = fields.numValues();
		for (int i = 0; i < numElements; i++) {
			final Element field = fields.getValueAsElement(i);
			final String id = field.getElementAsString("id");
			if (field.hasElement("fieldInfo")) {
				final Element fieldInfo = field.getElement("fieldInfo");
				final String mnemonic = fieldInfo.getElementAsString("mnemonic");
				final String description = fieldInfo.getElementAsString("description");

				logger.fine("fldId=" + id + ", fldMnemonic=" + mnemonic + ", fldDesc=" + description);

				Map<String, String> map = new HashMap<>();
				map.put("BLM_ID", id);
				map.put("NAME", description);
				map.put("CODE", mnemonic);
				send(map);
			} else {
				final Element fieldError = field.getElement("fieldError");
				final String message = fieldError.getElementAsString("message");
				logger.fine("ERROR: " + id + " - " + message);
				sendError(id + " - " + message);
			}

		}
	}

	private void send(Map<String, String> answer) {
		try {
			send(mapper.writeValueAsString(answer));
		} catch (Exception e) {
			sendError(e.getMessage());
		}
	}
}
