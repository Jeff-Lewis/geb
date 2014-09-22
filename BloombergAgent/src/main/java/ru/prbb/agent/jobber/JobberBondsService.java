/**
 * 
 */
package ru.prbb.agent.jobber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.agent.jobber.DBManager.CurrentData;
import ru.prbb.bloomberg.request.BdpRequest;

/**
 * @author RBr
 */
@Service
public class JobberBondsService extends JobberService {

	private final String[] fields = {
			"CHG_PCT_1D", "YLD_YTM_MID"
	};

	@Scheduled(cron = "0 59 11-18 * * ?")
	@Override
	public void execute() {
		log.info("Bonds execute");

		final List<String> securities = dbm.getSecForBonds();
		log.trace("securities:" + securities.size());

		final BdpRequest r = new BdpRequest(toArray(securities), fields);

		r.execute("Jobber/Bonds");

		final List<CurrentData> data = new ArrayList<CurrentData>();

		final Map<String, Map<String, String>> answer = r.getAnswer();
		for (String security : securities) {
			final Map<String, String> values = answer.get(security);
			for (String field : fields) {
				if (values.containsKey(field)) {
					data.add(new CurrentData(security, field, values.get(field)));
				}
			}
		}

		dbm.putCurrentData(data);
	}
}
