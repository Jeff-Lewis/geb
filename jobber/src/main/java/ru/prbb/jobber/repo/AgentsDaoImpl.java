package ru.prbb.jobber.repo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.AgentItem;

/**
 * Список зарегистрированных агентов
 * 
 * @author ruslan
 */
@Service
public class AgentsDaoImpl implements AgentsDao {
	private Logger log = LoggerFactory.getLogger(getClass());

	private Map<InetAddress, AgentItem> list = new HashMap<>();

	@Override
	public Collection<AgentItem> list() {
		synchronized (list) {
			List<AgentItem> res = new ArrayList<>(list.size());
			res.add(getSubscr());
			for (AgentItem agent : list.values()) {
				if (agent.isActive()) {
					res.add(agent);
				}
			}
			return res;
		}
	}

	@Override
	public boolean add(InetAddress host) {
		synchronized (list) {
			AgentItem ai = list.get(host);
			if (null == ai) {
				ai = new AgentItem(host);
				return list.put(host, ai) == null;
			} else {
				ai.update();
				return false;
			}
		}
	}

	@Override
	public boolean remove(InetAddress host) {
		synchronized (list) {
			return list.remove(host) != null;
		}
	}

	private final Random random = new Random(System.currentTimeMillis());

	@Override
	public AgentItem getRandom() {
		if (list.isEmpty()) {
			return getSubscr();
		}
		synchronized (list) {
			AgentItem ai;
			//do {
				int idx = random.nextInt(list.size());
				ai = (AgentItem) list.values().toArray()[idx];
			//} while (ai.isNotBusy());
			return ai;
		}
	}

	private AgentItem subscr;

	@Override
	public AgentItem getSubscr() {
		if (subscr == null) {
			String host = "172.16.15.117";
			try {
				subscr = new AgentItem(InetAddress.getByName(host));
			} catch (UnknownHostException e) {
				log.error("Get InetAddress for " + host);
				throw new RuntimeException(e);
			}
		}
		subscr.setStatus("Subscription agent");
		return subscr;
	}
}
