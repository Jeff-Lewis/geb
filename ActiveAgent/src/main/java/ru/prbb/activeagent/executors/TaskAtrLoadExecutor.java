package ru.prbb.activeagent.executors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskAtrLoad;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;

/**
 * Загрузка ATR
 * 
 * @author ruslan
 */
public class TaskAtrLoadExecutor extends TaskExecutor {

	private static final String DS_HIGH_CODE = "PX_HIGH";
	private static final String DS_LOW_CODE = "PX_LOW";
	private static final String DS_CLOSE_CODE = "PX_LAST";

	public TaskAtrLoadExecutor() {
		super(TaskAtrLoad.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		TaskAtrLoad taskData = mapper.readValue(data,
				new TypeReference<TaskAtrLoad>() {
				});

		Session session = startSession();
		try {
			String serviceUri = "//blp/tasvc";
			if (session.openService(serviceUri)) {
				Service service = session.getService(serviceUri);

				for (String security : taskData.getSecurities()) {
					Request request = service.createRequest("studyRequest");

					Element priceSource = request.getElement("priceSource");
					// set security name
					priceSource.setElement("securityName", security);

					Element dataRange = priceSource.getElement("dataRange");
					dataRange.setChoice("historical");

					// set historical price data
					Element historical = dataRange.getElement("historical");
					historical.setElement("startDate", taskData.getDateStart()); // set study start date
					historical.setElement("endDate", taskData.getDateEnd()); // set study start date

					// DMI study example - set study attributes
					Element studyAttributes = request.getElement("studyAttributes");
					studyAttributes.setChoice("atrStudyAttributes");

					Element dmiStudy = studyAttributes.getElement("atrStudyAttributes");
					dmiStudy.setElement("maType", taskData.getMaType());
					dmiStudy.setElement("period", taskData.getTaPeriod());
					dmiStudy.setElement("priceSourceHigh", DS_HIGH_CODE);
					dmiStudy.setElement("priceSourceLow", DS_LOW_CODE);
					dmiStudy.setElement("priceSourceClose", DS_CLOSE_CODE);

					sendRequest(session, request);
				}
			} else {
				throw new IOException("Unable to open service " + serviceUri);
			}
		} finally {
			session.stop();
		}
	}

	@Override
	protected void processMessage(Message sr) {
		String security = sr.getElementAsString("securityName");

		logger.info("security:" + security);

		if (sr.hasElement("studyError")) {
			String message = sr.getElement("studyError").getElementAsString("message");
			logger.info(message);
			sendError(message);
			return;
		}

		List<String> list = new ArrayList<>();

		Element sdArray = sr.getElement("studyData");
		for (int j = 0; j < sdArray.numValues(); ++j) {
			Element sd = sdArray.getValueAsElement(j);

			String date = sd.getElementAsString("date");
			String value = sd.getElementAsString("ATR");

			logger.info("- date:" + date + ", value:" + value);

			list.add(date + ";" + value);
		}

		send(security, list);
	}

	private void send(String security, List<String> list) {
		list.add(0, security);
		try {
			send(mapper.writeValueAsString(list));
		} catch (Exception e) {
			sendError(e.getMessage());
		}
	}
}
