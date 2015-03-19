package ru.prbb.middleoffice.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.AgentTask;

@Service
public class AgentTaskService {

	private List<AgentTask> tasks = Collections.synchronizedList(new ArrayList<AgentTask>());

	public void add(AgentTask task) {
		tasks.add(task);
	}

	public AgentTask next() {
		for (AgentTask task : tasks) {
			if (task.getResult() == null) {
				task.setResult("WAIT");
				return task;
			}
		}
		return null;
	}

	public AgentTask find(long idTask) {
		for (AgentTask task : tasks) {
			if (task.getIdTask() == idTask) {
				return task;
			}
		}
		return null;
	}

	public void remove(AgentTask task) {
		tasks.remove(task);
	}
}
