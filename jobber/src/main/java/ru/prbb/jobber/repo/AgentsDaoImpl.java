package ru.prbb.jobber.repo;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.AgentItem;

/**
 * Список зарегистрированных агентов
 * 
 * @author RBr
 */
@Service
public class AgentsDaoImpl implements AgentsDao {

	private Logger log = LoggerFactory.getLogger(getClass());

	private Map<InetAddress, AgentItem> list = new HashMap<>();

	@Override
	public Collection<AgentItem> list() {
		synchronized (list) {
			List<AgentItem> res = new ArrayList<>(list.size());
			for (AgentItem agent : list.values()) {
				if (agent.isActive()) {
					//
				}
				res.add(agent);
			}
			return res;
		}
	}

	@Override
	public boolean add(InetAddress host, Integer port) {
		synchronized (list) {
			AgentItem ai = list.get(host);
			if (null == ai) {
				ai = new AgentItem(host, port);
				log.info("Added Agent on host {}:{}", host, port);
				return list.put(host, ai) == null;
			} else {
				ai.update();
				log.info("Update Agent on host {}:{}", host, port);
				return false;
			}
		}
	}

	@Override
	public boolean remove(InetAddress host) {
		synchronized (list) {
			log.info("Remove Agent on host {}", host);
			return list.remove(host) != null;
		}
	}

	private Iterator<AgentItem> it;

	@Override
	public AgentItem nextAgent() {
		if (list.isEmpty()) {
			log.error("Agents don't registered");
			throw new IllegalStateException("Agents don't registered");
		}

		if (list.size() == 1) {
			return list.values().iterator().next();
		}

		synchronized (list) {
			if (null == it)
				it = list.values().iterator();

			if (it.hasNext()) {
				AgentItem agent = it.next();
				log.info("Using Agent on host {}", agent.getHost());
				return agent;
			}

			it = list.values().iterator();

			if (it.hasNext()) {
				AgentItem agent = it.next();
				log.info("Using Agent on host {}", agent.getHost());
				return agent;
			}

			log.error("No more Agents");
			throw new IllegalStateException("No more Agents");
		}
	}

}
