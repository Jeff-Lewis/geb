package ru.prbb.activeagent.executors;

import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Event;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.Event.EventType.Constants;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskData;

public abstract class TaskExecutor {

    protected final Logger logger = Logger.getLogger(getClass().getName());

	private final String type;
    protected final ObjectMapper mapper;

	protected TaskExecutor(String type) {
		this.type = type;
        mapper = new ObjectMapper();
	}

	public String getType() {
		return type;
	}

	public abstract void execute(TaskItem task, String data) throws Exception;

	protected void sendRequest(TaskData data, Session session, Request request) throws Exception {
		session.sendRequest(request, new CorrelationID(data));

		boolean continueToLoop = true;
		while (continueToLoop) {
			final Event event = session.nextEvent();

			switch (event.eventType().intValue()) {
			case Constants.RESPONSE: // final response
				continueToLoop = false;
			case Constants.PARTIAL_RESPONSE:
				for (Message message : event) {

					if (message.hasElement("responseError")) {
						final Element re = message.getElement("responseError");
						throw new RuntimeException(re.getElementAsString("message"));
					}

					processMessage(data, message);
				}
				break;
			default:
				break;
			}
		}
	}

	protected abstract void processMessage(TaskData data, Message message);

	protected static String getElementAsString(Element element, String name) {
		try {
			return element.getElementAsString(name);
		} catch (Exception e) {
			return "";
		}
	}

}
