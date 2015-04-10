package ru.prbb.activeagent.executors;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskData;
import ru.prbb.activeagent.tasks.TaskRequestOverrideQuarter;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

public class TaskBdpOverrideQuarterExecutor extends TaskExecutor {

	public TaskBdpOverrideQuarterExecutor() {
		super(TaskRequestOverrideQuarter.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		TaskRequestOverrideQuarter taskData = mapper.readValue(data,
				new TypeReference<TaskRequestOverrideQuarter>() {
				});

		final SessionOptions sesOpt = new SessionOptions();
		sesOpt.setServerHost("localhost");
		sesOpt.setServerPort(8194);

		Session session = new Session(sesOpt);
		session.start();
		try {
			if (session.openService("//blp/refdata")) {
				Service service = session.getService("//blp/refdata");

				for (String crncy : taskData.getCurrencies()) {

					final String idq[] = { "1CQ", "2CQ", "3CQ", "4CQ", "1CS", "2CS", "1CY", "2CY" };
					for (final String quarter : idq) {
						final Request request = service.createRequest("ReferenceDataRequest");

						final Element _securities = request.getElement("securities");
						for (String s : taskData.getSecurities()) {
							final int p = s.indexOf('|');
							if (crncy.equals(s.substring(p + 1))) {
								_securities.appendValue(s.substring(0, p));
							}
						}

						final Element _fields = request.getElement("fields");
						for (String field : taskData.getFields()) {
							_fields.appendValue(field);
						}

						final Element overrides = request.getElement("overrides");

						final Element overridePeriod = overrides.appendElement();
						overridePeriod.setElement("fieldId", "BEST_FPERIOD_OVERRIDE");
						overridePeriod.setElement("value", period = quarter);

						final Element overrideCrncy = overrides.appendElement();
						overrideCrncy.setElement("fieldId", "EQY_FUND_CRNCY");
						overrideCrncy.setElement("value", crncy);

						String override = taskData.getOver();
						if (null != override) {
							final Element overrideDataSource = overrides.appendElement();
							overrideDataSource.setElement("fieldId", "BEST_DATA_SOURCE_OVERRIDE");
							overrideDataSource.setElement("value", override);
						}

						sendRequest(taskData, session, request);
					}
				}
			}
		} finally {
			session.stop();
		}
	}

	private String period;

	@Override
	protected void processMessage(TaskData data, Message msg) {
		TaskRequestOverrideQuarter taskData = (TaskRequestOverrideQuarter) data;
		final Element arraySecurityData = msg.getElement("securityData");
		final int numItems = arraySecurityData.numValues();
		for (int i = 0; i < numItems; ++i) {
			final Element securityData = arraySecurityData.getValueAsElement(i);

			final String security = securityData.getElementAsString("security");

			// TODO answer
			Map<String, Map<String, Map<String, String>>> answer = null;
			Map<String, Map<String, String>> pv;
			if (answer.containsKey(security)) {
				pv = answer.get(security);
			} else {
				pv = new HashMap<>();
				answer.put(security, pv);
			}
			final Map<String, String> values = new HashMap<>();
			pv.put(period, values);

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
		}
	}

}
