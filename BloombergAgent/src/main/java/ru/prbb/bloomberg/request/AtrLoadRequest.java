/**
 * 
 */
package ru.prbb.bloomberg.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.prbb.bloomberg.core.BloombergRequest;
import ru.prbb.bloomberg.core.BloombergSession;
import ru.prbb.bloomberg.core.MessageHandler;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;

/**
 * @author RBr
 */
public class AtrLoadRequest implements BloombergRequest, MessageHandler {

	private static final Log log = LogFactory.getLog(AtrLoadRequest.class);

	private String startDate;
	private String endDate;
	private String[] securities;
	private String maType;
	private Integer taPeriod;
	private String period;
	private String calendar;

	private final List<Map<String, Object>> answer = new ArrayList<>();

	/**
	 * @return
	 *         security -> {date -> { field, value } )
	 */
	public List<Map<String, Object>> getAnswer() {
		return answer;
	}

	public AtrLoadRequest(String startDate, String endDate, String[] securities,
			String maType, Integer taPeriod, String period, String calendar) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.securities = securities;
		this.maType = maType;
		this.taPeriod = taPeriod;
		this.period = period;
		this.calendar = calendar;
	}

	static final String ds_high_code = "PX_HIGH";
	static final String ds_low_code = "PX_LOW";
	static final String ds_close_code = "PX_LAST";

	@Override
	public void execute(String name) {
		final BloombergSession bs = new BloombergSession(name);
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
				historical.setElement("startDate", startDate); // set study start date
				historical.setElement("endDate", endDate); // set study start date

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

	@Override
	public void processMessage(Message sr) {
		//final Element sr = msg.getElement("studyResponse");

		final String security = sr.getElementAsString("securityName");

		/*
		studyResponse = {
		securityName = EAAPL US Equity
		studyError = {
		    source = 210::bbdbh2
		    code = 15
		    category = BAD_SEC
		    message = Unknown/Invalid securityInvalid Security [nid:210] 
		    subcategory = INVALID_SECURITY
		}
		fieldExceptions[] = {
		}
		studyData[] = {
		}
		}
		 */
		if (sr.hasElement("studyError")) {
			log.error(security + ", studyError:"
					+ sr.getElement("studyError").getElementAsString("message"));
			return;
		}

		/*
		studyResponse = {
		securityName = AAPL US Equity
		fieldExceptions[] = {
		}
		studyData[] = {
		    studyData = {
		        date = 2013-10-01T00:00:00.000+00:00
		        ATR = 10.264273602783119
		    }
		    studyData = {
		        date = 2013-10-02T00:00:00.000+00:00
		        ATR = 9.710705202087341
		    }
		}
		}
		 */
		final Element sdArray = sr.getElement("studyData");
		for (int j = 0; j < sdArray.numValues(); ++j) {
			final Element sd = sdArray.getValueAsElement(j);

			final String date = sd.getElementAsString("date");
			final Double value = sd.getElementAsFloat64("ATR");

			if (log.isDebugEnabled()) {
				log.debug("security:" + security + ", date:" + date + ", value:" + value);
			}

			final Map<String, Object> map = new HashMap<>();
			answer.add(map);
			map.put("security", security);
			map.put("date", date);
			map.put("value", value);
		}
	}
}
