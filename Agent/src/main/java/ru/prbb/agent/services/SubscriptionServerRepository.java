package ru.prbb.agent.services;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.prbb.agent.domain.SubscriptionServer;

/**
 * 
 * @author ruslan
 *
 */
@Repository
public class SubscriptionServerRepository {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final List<SubscriptionServer> servers = new ArrayList<>();

	private int index;

	@PostConstruct
	public void init() {
			index = 0;
			add("http://172.23.153.164:8080/Jobber/Subscription");
//			add("http://172.16.15.36:10180/Jobber/Subscription");
//			add("http://172.16.15.36:10190/Jobber/Subscription");
	}

	public Iterator<SubscriptionServer> getServers() {
		return servers.iterator();
	}

	public SubscriptionServer next() {
		if (servers.isEmpty())
			throw new RuntimeException("Server's list is empty");
		if (index >= servers.size())
			index = 0;
		return servers.get(index++);
	}

	public boolean add(String host) {
		try {
			return servers.add(new SubscriptionServer(host));
		} catch (URISyntaxException e) {
			log.error("Add " + host, e);
		}
		return false;
	}

	public boolean remove(String host) {
		for (SubscriptionServer server : servers) {
			if (host.contains(server.getHost())) {
				return servers.remove(server);
			}
		}
		return false;
	}

}
