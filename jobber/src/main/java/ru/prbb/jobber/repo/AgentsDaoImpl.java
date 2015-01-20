package ru.prbb.jobber.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.AgentItem;

/**
 * Список зарегистрированных агентов
 * 
 * @author ruslan
 */
@Service
public class AgentsDaoImpl implements AgentsDao {

	private Set<AgentItem> list = new HashSet<>();

	@Override
	public Collection<String> list() {
		synchronized (list) {
			List<String> res = new ArrayList<>(list.size());
			for (AgentItem agent : list) {
				if (agent.isActive()) {
					res.add(agent.getHost());
				}
			}
			return res;
		}
	}

	@Override
	public boolean add(String host) {
		synchronized (list) {
			return list.add(new AgentItem(host));
		}
	}

	@Override
	public boolean remove(String host) {
		synchronized (list) {
			return list.remove(new AgentItem(host));
		}
	}

}
