package ru.prbb.agent.services;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.prbb.agent.model.JobServer;

@Repository
public class JobServerRepository {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final List<JobServer> servers = new ArrayList<>();

	private int index;

	@PostConstruct
	public void init() {
			index = 0;
			add("http://172.23.153.164:8080/analytics/AgentTask");
			add("http://172.23.153.164:8080/Jobber/AgentTask");
			add("http://172.23.153.164:8080/middleoffice/AgentTask");
//			add("172.16.15.36:10180");
//			add("172.16.15.36:10190");
	}

	public Iterator<JobServer> getServers() {
		return servers.iterator();
	}

	public JobServer next() {
		if (servers.isEmpty())
			throw new RuntimeException("Server's list is empty");
		if (index >= servers.size())
			index = 0;
		return servers.get(index++);
	}

	public boolean add(String host) {
		try {
			return servers.add(new JobServer(host));
		} catch (URISyntaxException e) {
			log.error("Add " + host, e);
		}
		return false;
	}

	public boolean remove(String host) {
		for (JobServer server : servers) {
			if (host.contains(server.getHost())) {
				return servers.remove(server);
			}
		}
		return false;
	}

}
