package ru.prbb.activeagent.executors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskBdpRequestOverrideQuarter;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;

public class TaskBdpOverrideQuarterExecutor extends TaskExecutor {

	public TaskBdpOverrideQuarterExecutor() {
		super(TaskBdpRequestOverrideQuarter.class.getSimpleName());
	}

	private TaskBdpRequestOverrideQuarter taskData;

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		taskData = mapper.readValue(data,
				new TypeReference<TaskBdpRequestOverrideQuarter>() {
				});

		Session session = startSession();
		try {
			String serviceUri = "//blp/refdata";
			if (session.openService(serviceUri)) {
				Service service = session.getService(serviceUri);

				for (String crncy : taskData.getCurrencies()) {

					//Calendar
					//final String idq[] = { "1CQ", "2CQ", "3CQ", "4CQ", "1CS", "2CS", "1CY", "2CY" };
					//Fiscal
					final String idq[] = { "1FQ", "2FQ", "3FQ", "4FQ", "1FS", "2FS", "1FY", "2FY" };
					for (final String quarter : idq) {
						final Request request = service.createRequest("ReferenceDataRequest");

						final Element _securities = request.getElement("securities");
						for (String security : taskData.getSecurities()) {
							if (security.startsWith(crncy)) {
								_securities.appendValue(security.substring(crncy.length()));
							}
						}

						final Element _fields = request.getElement("fields");
						for (String field : taskData.getFields()) {
							_fields.appendValue(field);
						}

						final Element overrides = request.getElement("overrides");

						final Element overridePeriod = overrides.appendElement();
						overridePeriod.setElement("fieldId", "BEST_FPERIOD_OVERRIDE");
						overridePeriod.setElement("value", quarter);

						final Element overrideCrncy = overrides.appendElement();
						overrideCrncy.setElement("fieldId", "EQY_FUND_CRNCY");
						overrideCrncy.setElement("value", crncy);

						String override = taskData.getOver();
						if (null != override) {
							final Element overrideDataSource = overrides.appendElement();
							overrideDataSource.setElement("fieldId", "BEST_DATA_SOURCE_OVERRIDE");
							overrideDataSource.setElement("value", override);
						}

						sendRequest(session, request, new CorrelationID(new RequestData(crncy, quarter)));
					}
				}
			} else {
				throw new IOException("Unable to open service " + serviceUri);
			}
		} finally {
			session.stop();
		}
	}

	private class RequestData {

		String crncy;
		String period;

		RequestData(String crncy, String period) {
			this.crncy = crncy;
			this.period = period;
		}
		
	}

	@Override
	protected void processMessage(Message msg) {
		Map<String, Map<String, Map<String, String>>> answer = new HashMap<>();

		RequestData rd = (RequestData) msg.correlationID().object();

		final Element arraySecurityData = msg.getElement("securityData");
		final int numItems = arraySecurityData.numValues();
		for (int i = 0; i < numItems; ++i) {
			final Element securityData = arraySecurityData.getValueAsElement(i);

			final String security = rd.crncy + securityData.getElementAsString("security");

			Map<String, Map<String, String>> pv;
			if (answer.containsKey(security)) {
				pv = answer.get(security);
			} else {
				pv = new HashMap<>();
				answer.put(security, pv);
			}
			final Map<String, String> values = new HashMap<>();
			pv.put(rd.period, values);

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
						sendError(e.getMessage());
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
