package ru.prbb.jobber.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * Список зарегистрированных агентов
 * 
 * @author ruslan
 */
@Service
public class AgentsDaoImpl implements AgentsDao {

	private Set<String> list = new HashSet<>();
	private Iterator<String> it;

	public void clear() {
		list.clear();
	}

	@Override
	public Collection<String> list() {
		synchronized (list) {
			return new ArrayList<>(list);
		}
	}

	@Override
	public String next() {
		synchronized (list) {
			if (list.isEmpty())
				throw new IllegalStateException("List is empty");
			
			if (null == it || !it.hasNext()) {
				it = list.iterator();
			}
					
			return it.next();
		}
	}

	@Override
	public boolean add(String host) {
		synchronized (list) {
			return list.add(host);
		}
	}

	@Override
	public boolean remove(String host) {
		synchronized (list) {
			return list.remove(host);
		}
	}

}
