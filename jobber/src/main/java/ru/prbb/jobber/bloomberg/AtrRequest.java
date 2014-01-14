/**
 * 
 */
package ru.prbb.jobber.bloomberg;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.prbb.Utils;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;

/**
 * BDP запрос
 * 
 * @author RBr
 * 
 */
public class AtrRequest implements BloombergRequest, MessageHandler {

	private static final Log log = LogFactory.getLog(AtrRequest.class);

	private final java.util.Date startDate;
	private final java.util.Date endDate;
	private final String maType;
	private final Integer taPeriod;
	// private final String period;
	// private final String calendar;
	private final List<String> securities;
	private final String ds_high_code = "PX_HIGH";
	private final String ds_low_code = "PX_LOW";
	private final String ds_close_code = "PX_LAST";

	private final Map<String, Map<Date, Double>> answer = new HashMap<String, Map<Date, Double>>();

	/**
	 * @return
	 *         security -> { date -> value }
	 */
	public Map<String, Map<Date, Double>> getAnswer() {
		return answer;
	}

	public AtrRequest(java.util.Date startDate, java.util.Date endDate, List<String> securities,
			final String maType, final Integer taPeriod, final String period, final String calendar) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.securities = securities;
		this.maType = maType;
		this.taPeriod = taPeriod;
		// this.period = period;
		// this.calendar = calendar;
	}

	@Override
	public void execute(String name) {
		final BloombergSession bs = new BloombergSession("Загрузка ATR");
		bs.start();
		try {
			final Service service = bs.getService("//blp/tasvc");

			for (String security : securities) {
				Request request = service.createRequest("studyRequest");

				Element priceSource = request.getElement("priceSource");
				// set security name
				priceSource.setElement("securityName", security);

				Element dataRange = priceSource.getElement("dataRange");
				dataRange.setChoice("historical");

				// set historical price data
				Element historical = dataRange.getElement("historical");
				final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				historical.setElement("startDate", sdf.format(startDate)); // set study start date
				historical.setElement("endDate", sdf.format(endDate)); // set study start date

				// DMI study example - set study attributes
				Element studyAttributes = request.getElement("studyAttributes");
				studyAttributes.setChoice("atrStudyAttributes");

				Element dmiStudy = studyAttributes.getElement("atrStudyAttributes");
				dmiStudy.setElement("maType", maType);
				dmiStudy.setElement("period", taPeriod);
				dmiStudy.setElement("priceSourceHigh", ds_high_code);
				dmiStudy.setElement("priceSourceLow", ds_low_code);
				dmiStudy.setElement("priceSourceClose", ds_close_code);

				bs.sendRequest(request, this);
			}
		} finally {
			bs.stop();
		}
	}

	private final SimpleDateFormat sdf = Utils.createDateFormatYMD();

	@Override
	public void processMessage(Message sr) {
		final String security = sr.getElementAsString("securityName");

		if (sr.hasElement("studyError")) {
			final String message = sr.getElement("studyError").getElementAsString("message");
			log.error("security:" + security + ", studyError:" + message);
			return;
		}

		final Map<Date, Double> map = new HashMap<>();
		answer.put(security, map);

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
				log.error(e);
			}
		}
	}
}
