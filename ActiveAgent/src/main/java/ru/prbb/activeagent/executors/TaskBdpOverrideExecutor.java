package ru.prbb.activeagent.executors;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskBdpRequestOverride;
import ru.prbb.activeagent.tasks.TaskData;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

public class TaskBdpOverrideExecutor extends TaskExecutor {

	public TaskBdpOverrideExecutor() {
		super(TaskBdpRequestOverride.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		TaskBdpRequestOverride taskData = mapper.readValue(data,
				new TypeReference<TaskBdpRequestOverride>() {
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

				final Element overrides = request.getElement("overrides");

				final Element _securities = request.getElement("securities");
				for (String security : taskData.getSecurities()) {
					final int p = security.indexOf("|");

					_securities.appendValue(security.substring(0, p));

					final Element overrid = overrides.appendElement();
					overrid.setElement("fieldId", "EQY_FUND_CRNCY");
					overrid.setElement("value", security.substring(p + 1));
				}

				final Element _fields = request.getElement("fields");
				for (String field : taskData.getFields()) {
					_fields.appendValue(field);
				}

				final Element overridPeriod = overrides.appendElement();
				overridPeriod.setElement("fieldId", "BEST_FPERIOD_OVERRIDE");
				overridPeriod.setElement("value", taskData.getPeriod());

				sendRequest(taskData, session, request);
			}
		} finally {
			session.stop();
		}
	}

	@Override
	protected void processMessage(TaskData data, Message msg) {
		TaskBdpRequestOverride taskData = (TaskBdpRequestOverride) data;

		final Element arraySecurityData = msg.getElement("securityData");
		final int numItems = arraySecurityData.numValues();
		for (int i = 0; i < numItems; ++i) {
			final Element securityData = arraySecurityData.getValueAsElement(i);

			final String security = securityData.getElementAsString("security");

			final Map<String, String> values = new HashMap<String, String>();

			if (securityData.hasElement("securityError")) {
				final String value = securityData.getElementAsString("securityError");
				logger.severe("SecurityError:" + value);
				values.put("securityError", value);
				continue;
			}

			final Element fieldData = securityData.getElement("fieldData");
			for (String field : taskData.getFields()) {
				if (fieldData.hasElement(field)) {
					try {
						String value = fieldData.getElementAsString(field);
						values.put(field, value);
					} catch (Exception e) {
						logger.severe(e.getMessage());
					}
				}
			}
			// TODO answer.put(security, values);
		}
	}

}
