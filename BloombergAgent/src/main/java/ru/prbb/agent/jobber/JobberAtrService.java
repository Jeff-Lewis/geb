/**
 * 
 */
package ru.prbb.agent.jobber;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.bloomberg.request.AtrRequest;

/**
 * @author RBr
 */
@Service
public class JobberAtrService extends JobberService {

	private static final String EXPONENTIAL = "Exponential";
	private static final String DAILY = "DAILY";
	private static final String CALENDAR = "CALENDAR";
	private static final String DS_HIGH_CODE = "PX_HIGH";
	private static final String DS_LOW_CODE = "PX_LOW";
	private static final String DS_CLOSE_CODE = "PX_LAST";

	@Scheduled(cron = "0 10 05 * * ?")
	@Override
	public void execute() {
		log.info("Atr execute " + yesterday());

		final List<String> securities = dbm.getSecForAtr();
		log.trace("securities:" + securities.size());

		final AtrRequest r = new AtrRequest(yesterday(), yesterday(), securities, EXPONENTIAL, 7, DAILY, CALENDAR);

		r.execute("Jobber/Atr");

		final Map<String, Map<Date, Double>> a = r.getAnswer();
		for (String security : securities) {
			final Map<Date, Double> d = a.get(security);

			if (null != d) {
				for (Date date_time : d.keySet()) {
					final Double atr_value = d.get(date_time);
					log.info("security:" + security + ", date_time:" + date_time + ", atr_value:" + atr_value);
					dbm.putAtrData(security, date_time, atr_value, 7, EXPONENTIAL,
							DS_HIGH_CODE, DS_LOW_CODE, DS_CLOSE_CODE, DAILY, CALENDAR);
				}
			}
		}
	}
}
