package ru.prbb.activeagent.executors;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskAtrLoad;
import ru.prbb.activeagent.tasks.TaskData;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

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
		
		final SessionOptions sesOpt = new SessionOptions();
		sesOpt.setServerHost("localhost");
		sesOpt.setServerPort(8194);

		Session session = new Session(sesOpt);
		session.start();
		try {
			if (session.openService("//blp/tasvc")) {
				Service service = session.getService("//blp/tasvc");

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
					
					sendRequest(taskData, session, request);
				}
			}
		} finally {
			session.stop();
		}
	}

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void processMessage(TaskData data, Message sr) {
		final String security = sr.getElementAsString("securityName");

		if (sr.hasElement("studyError")) {
			final String message = sr.getElement("studyError").getElementAsString("message");
			return;
		}

		final Map<Date, Double> map = new HashMap<>();

		final Element sdArray = sr.getElement("studyData");
		for (int j = 0; j < sdArray.numValues(); ++j) {
			final Element sd = sdArray.getValueAsElement(j);

			final String date = sd.getElementAsString("date");
			final Double value = sd.getElementAsFloat64("ATR");

			// log.info("security:" + security + ", date:" + date + ", value:" + value);

			try {
				final java.sql.Date date_time = new java.sql.Date(sdf.parse(date).getTime());
				map.put(date_time, value);
			} catch (ParseException e) {
				logger.severe(e.getMessage());
			}
		}

		// TODO answer.put(security, map);
	}
}
