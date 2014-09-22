package ru.prbb.agent.jobber;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.agent.jobber.DBManager.AnalystData;
import ru.prbb.bloomberg.model.SecForJobRequest;
import ru.prbb.bloomberg.request.BdsRequest;
import ru.prbb.bloomberg.request.BdsRequest.BEST_ANALYST_RECS_BULK;

@Service
public class JobberBdsService extends JobberService {

	@Scheduled(cron = "0 00 03 * * ?")
	@Override
	public void execute() {
		log.info("GET SECURITY FOR BDS REQUEST!!!!");

		final List<SecForJobRequest> infos = dbm.getLoadEstimatesPeersData();
		log.trace("info=" + infos.size());
		String[] securities = new String[infos.size()];
		for (int i = 0; i < infos.size(); i++) {
			securities[i] = infos.get(i).code;
		}

		final BdsRequest r = new BdsRequest(securities, toArray("BEST_ANALYST_RECS_BULK", "BLOOMBERG_PEERS"));
		r.execute("Jobber/BDS");

		for (SecForJobRequest info : infos) {
			final String security = info.code;

			final List<BEST_ANALYST_RECS_BULK> baItems = r.getBestAnalyst().get(security);
			for (BEST_ANALYST_RECS_BULK item : baItems) {
				final AnalystData d = new AnalystData(security,
						item.firm,
						item.analyst,
						item.recom,
						item.rating,
						item.action_code,
						item.target_price,
						item.period,
						item.date,
						item.barr,
						item.year_return);
				dbm.putAnalysData(d);
			}

			final List<String> ptItems = r.getPeerTicker().get(security);
			for (String peer : ptItems) {
				dbm.putPeersProc(security, peer);
			}
		}
	}
}
