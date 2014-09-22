package ru.prbb.agent.jobber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.agent.jobber.DBManager.HistParamData;
import ru.prbb.bloomberg.model.SecForJobRequest;
import ru.prbb.bloomberg.request.HistoricalDataRequest;

@Service
public class JobberHistDataService extends JobberService {

	private final String[] fields = {
			"EQY_WEIGHTED_AVG_PX",
			"PX_HIGH", "PX_LAST", "PX_LOW", "PX_VOLUME",
			"TOT_BUY_REC", "TOT_HOLD_REC", "TOT_SELL_REC"
	};

	@Scheduled(cron = "0 00 07 * * ?")
	@Override
	public void execute() {
		final String _date = new SimpleDateFormat("yyyy-MM-dd").format(yesterday());

		log.info("HistData execute date:" + _date);

		final List<SecForJobRequest> securities = dbm.getSecForHistData();
		log.trace("securities size:" + securities.size());

		final Set<String> currencies = new HashSet<String>();
		for (SecForJobRequest item : securities) {
			currencies.add(item.iso);
		}

		final List<String> cursec = new ArrayList<String>();
		for (SecForJobRequest item : securities) {
			cursec.add(item.iso + item.code);
		}

		String date = toBloomberg(yesterday());
		final HistoricalDataRequest r = new HistoricalDataRequest(date,date, toArray(cursec), fields, toArray(currencies));

		r.execute("Jobber/HistData");

		final List<HistParamData> data = new ArrayList<HistParamData>();

		final Map<String, Map<String, Map<String, String>>> answer = r.getAnswer();
		for (String currency : currencies) {
			for (String cs : cursec) {
				if (cs.startsWith(currency)) {
					final String security = cs.substring(currency.length());

					final Map<String, String> values = answer.get(cs).get(yesterday());
					if (null == values) {
						log.error("Нет информации для " + security);
						continue;
					}

					for (String field : fields) {
						if (values.containsKey(field)) {
							final String value = values.get(field);
							log.trace("security:" + security + ", field:" + field + ", date:" + _date
									+ ", value:" + value + ", currency:" + currency);
							data.add(new HistParamData(security, field, _date, value, "DAILY", currency, "CALENDAR"));
						}
					}
				}
			}
		}

		dbm.putHistParamsData(data);
	}
}
