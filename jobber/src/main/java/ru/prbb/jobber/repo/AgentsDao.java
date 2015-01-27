package ru.prbb.jobber.repo;

import java.net.InetAddress;
import java.util.Collection;

import ru.prbb.jobber.domain.AgentItem;

/**
 * Список зарегистрированных агентов
 * 
 * @author RBr
 */
public interface AgentsDao {

	Collection<AgentItem> list();

	boolean add(InetAddress host);

	boolean remove(InetAddress host);

	AgentItem nextAgent();

}
