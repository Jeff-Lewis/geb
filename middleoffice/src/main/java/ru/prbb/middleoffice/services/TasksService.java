package ru.prbb.middleoffice.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.tasks.TaskData;
import ru.prbb.middleoffice.domain.tasks.TaskItem;

@Service
public class TasksService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static final TaskItem EMPTY_TASK;

	static {
		EMPTY_TASK = new TaskItem();
		EMPTY_TASK.setId(Long.valueOf(0));
		EMPTY_TASK.setType("empty_task");
	}

	private final Random random = new Random(System.currentTimeMillis());

	private Map<TaskItem, TaskData> tasks = Collections.synchronizedMap(
			new HashMap<TaskItem, TaskData>());

	public TaskItem getTask() {
		for (Entry<TaskItem, TaskData> entry : tasks.entrySet()) {
			TaskData data = entry.getValue();
			synchronized (data) {
				if (TaskData.Status.READY == data.getStatus()) {
					data.setStatus(TaskData.Status.WAIT);
					return entry.getKey();
				}
			}
		}
		return EMPTY_TASK;
	}

	public TaskData getTaskData(Long id) {
		for (Entry<TaskItem, TaskData> entry : tasks.entrySet()) {
			if (id.equals(entry.getKey().getId())) {
				TaskData data = entry.getValue();
				if (TaskData.Status.WAIT == data.getStatus()) {
					data.setStatus(TaskData.Status.WORK);
					return data;
				}
			}
		}
		return new TaskData(EMPTY_TASK.getType());
	}

	public void updateTaskData(Long id, String str) {
		for (Entry<TaskItem, TaskData> entry : tasks.entrySet()) {
			if (id.equals(entry.getKey().getId())) {
				TaskData data = entry.getValue();
				if (TaskData.Status.WORK == data.getStatus()) {
					data.update(str);
				}
			}
		}
	}

	public void execute(TaskData data) throws InterruptedException {
		TaskItem task = new TaskItem();
		task.setId(random.nextLong());
		task.setType(data.getClass().getSimpleName());

		tasks.put(task, data);

		synchronized (data) {
			for (int iter = 120, c = 0; c < iter; ++c) {
				try {
					data.wait(1000);
				} catch (InterruptedException ignore) {
				}

				if (TaskData.Status.WORK == data.getStatus()) {
					iter = 1200;
					continue;
				}

				if (TaskData.Status.DONE == data.getStatus()) {
					tasks.remove(task);
					return;
				}
			}
		}
		throw new InterruptedException("Task execute timeout");
	}
}
