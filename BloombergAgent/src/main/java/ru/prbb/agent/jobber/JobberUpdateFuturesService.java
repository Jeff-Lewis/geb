package ru.prbb.agent.jobber;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.Utils;
import ru.prbb.agent.jobber.DBManager.Security;
import ru.prbb.agent.jobber.DBManager.UpdateFutureData;
import ru.prbb.bloomberg.request.BdpRequest;

@Service
public class JobberUpdateFuturesService extends JobberService {

	private static final String SECURITY_NAME = "SECURITY_NAME";
	private static final String NAME = "NAME";
	private static final String SHORT_NAME = "SHORT_NAME";
	private static final String FTD = "FUT_FIRST_TRADE_DT";
	private static final String LTD = "LAST_TRADEABLE_DT";

	@Scheduled(cron = "0 15 04 * * ?")
	@Override
	public void execute() {
		log.info("UpdateFutures execute");

		final List<Security> securities = dbm.getSecForUpdateFutures();
		log.trace("securities size:" + securities.size());

		final String[] _securities = new String[securities.size()];
		for (int i = 0; i < securities.size(); i++) {
			_securities[i] = securities.get(i).code;
		}
		final BdpRequest r = new BdpRequest(_securities,
				toArray(SECURITY_NAME, NAME, SHORT_NAME, FTD, LTD));

		r.execute("Jobber/Update securities");

		final List<UpdateFutureData> data = new ArrayList<UpdateFutureData>();

		final Map<String, Map<String, String>> answer = r.getAnswer();
		for (Security security : securities) {
			if (answer.containsKey(security.code)) {
				final Map<String, String> values = answer.get(security.code);
				final String secName = values.get(SECURITY_NAME);
				final String name = values.get(NAME);
				final String shortName = values.get(SHORT_NAME);
				final Date ftd = Utils.parseDate(values.get(FTD));
				final Date ltd = Utils.parseDate(values.get(LTD));
				log.trace("code:" + security.code + ", secName:" + secName + ", name:" + name + ", shortName:"
						+ shortName + ", firstTD:" + ftd + ", lastTD:" + ltd);
				data.add(new UpdateFutureData(security.id, secName, name, shortName, ftd, ltd));
			} else {
				log.error("No answer for " + security.code);
			}
		}

		dbm.putUpdatesFutures(data);
	}
}
