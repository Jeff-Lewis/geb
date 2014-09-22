package ru.prbb.agent.jobber;

import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.bloomberg.model.SecForJobRequest;

@Service
public class JobberBdpOverrideService extends JobberService {

	@Scheduled(cron = "0 00 06 * * ?")
	@Override
	public void execute() {
		log.info("BdpOverride execute");

		final List<SecForJobRequest> info = dbm.getLoadEstimatesPeersData();
		log.trace("info size:" + info.size());

		Map<String, Map<String, String>> a = bs.executeBdpOverrideLoad(info);
	}
}
