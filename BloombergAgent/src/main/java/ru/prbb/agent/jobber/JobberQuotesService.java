package ru.prbb.agent.jobber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.agent.jobber.DBManager.QuoteData;
import ru.prbb.bloomberg.request.HistoricalDataRequest;

@Service
public class JobberQuotesService extends JobberService {

	private static final String PX_LAST = "PX_LAST";

	@Scheduled(cron = "0 00 05 * * ?")
	@Override
	public void execute() {
		final String _date = new SimpleDateFormat("yyyy-MM-dd").format(yesterday());

		log.info("Quotes execute date:" + _date);

		final List<String> securities = dbm.getSecForQuotes();
		log.trace("securities size:" + securities.size());

		String date = toBloomberg(yesterday());
		final HistoricalDataRequest r = new HistoricalDataRequest(date, date, toArray(securities), toArray(PX_LAST));

		r.execute("Jobber/Quotes");

		final List<QuoteData> data = new ArrayList<QuoteData>();

		final Map<String, Map<String, Map<String, String>>> answer = r.getAnswer();
		for (String security : securities) {
			final Map<String, String> values = answer.get(security).get(date);
			if (null == values) {
				log.error("Нет информации для " + security);
				continue;
			}

			if (values.containsKey(PX_LAST)) {
				try {
					final double value = Double.parseDouble(values.get(PX_LAST));
					log.trace("security:" + security + ", value:" + value + ", date:" + _date);
					data.add(new QuoteData(security, value, _date));
				} catch (NumberFormatException e) {
					log.error(e);
				}
			}
		}

		dbm.putQuotes(data);
	}
}
