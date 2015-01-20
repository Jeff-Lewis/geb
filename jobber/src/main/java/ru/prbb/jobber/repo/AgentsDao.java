package ru.prbb.jobber.repo;

import java.util.Collection;

public interface AgentsDao {

	Collection<String> list();
	
	boolean add(String host);

	boolean remove(String host);

}
