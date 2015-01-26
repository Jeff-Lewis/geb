package ru.prbb.jobber.repo;

import java.net.InetAddress;
import java.util.Collection;

import ru.prbb.jobber.domain.AgentItem;

public interface AgentsDao {

	Collection<AgentItem> list();
	
	boolean add(InetAddress host);

	boolean remove(InetAddress host);
	
	AgentItem getRandom();

	AgentItem getSubscr();

}
