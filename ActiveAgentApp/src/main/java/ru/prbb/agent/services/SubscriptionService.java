/**
 * 
 */
package ru.prbb.agent.services;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import ru.prbb.agent.model.SubscriptionServer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Подписка блумберга
 * 
 * @author RBr
 */
@Service
public class SubscriptionService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private TaskScheduler scheduler;

	private final List<SubscriptionChecker> servers = new ArrayList<>();

	@PostConstruct
	public void init() {
		add("172.23.153.164:8080");
		add("172.16.15.36:10180");
		add("172.16.15.36:10190");

		for (SubscriptionChecker server : servers) {
			scheduler.scheduleWithFixedDelay(server, 5000);
		}
	}

	@PreDestroy
	public void done() {
		log.info("Stop all subscription threads");
		for (SubscriptionChecker server : servers) {
			server.terminate();
		}
	}

	private boolean add(String host) {
		try {
			SubscriptionServer server = new SubscriptionServer("http://" + host + "/Jobber/Subscription");
			log.info("Add SubscriptionServer {}", server);
			return servers.add(new SubscriptionChecker(mapper.copy(), server));
		} catch (URISyntaxException e) {
			log.error("Add SubscriptionServer " + host, e);
		}

		return false;
	}
}
