package ru.prbb.activeagent.executors;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskData;
import ru.prbb.activeagent.tasks.TaskFieldInfoRequest;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

public class TaskFieldInfoExecutor extends TaskExecutor {

	public TaskFieldInfoExecutor() {
		super(TaskFieldInfoRequest.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		TaskFieldInfoRequest taskData = mapper.readValue(data,
				new TypeReference<TaskFieldInfoRequest>() {
				});

		final SessionOptions sesOpt = new SessionOptions();
		sesOpt.setServerHost("localhost");
		sesOpt.setServerPort(8194);

		Session session = new Session(sesOpt);
		session.start();
		try {
			if (session.openService("//blp/apiflds")) {
				Service service = session.getService("//blp/apiflds");

				final Request request = service.createRequest("FieldInfoRequest");
				request.append("id", taskData.getCode());
				request.set("returnFieldDocumentation", false);
				
				sendRequest(taskData, session, request);
			}
		} finally {
			session.stop();
		}
	}

	@Override
	protected void processMessage(TaskData data, Message msg) {
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

				// TODO answer
				//answer.put("BLM_ID", id);
				//answer.put("NAME", description);
				//answer.put("CODE", mnemonic);
			} else {
				final Element fieldError = field.getElement("fieldError");
				final String message = fieldError.getElementAsString("message");
				logger.fine("ERROR: " + id + " - " + message);
			}

		}
	}

}
