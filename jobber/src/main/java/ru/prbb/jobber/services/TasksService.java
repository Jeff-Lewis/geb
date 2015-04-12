package ru.prbb.jobber.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.tasks.TaskData;
import ru.prbb.jobber.domain.tasks.TaskItem;

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

	private List<TaskData> tasks = Collections.synchronizedList(
			new ArrayList<TaskData>());

	public TaskItem getTask() {
		for (TaskData data : tasks) {
			synchronized (data) {
				if (TaskData.Status.READY == data.getStatus()) {
					data.setStatus(TaskData.Status.WAIT);
					TaskItem task = new TaskItem();
					task.setId(data.getId());
					task.setType(data.getType());
					return task;
				}
			}
		}
		return EMPTY_TASK;
	}

	public TaskData getTaskData(Long id) {
		for (TaskData data : tasks) {
			if (id.equals(data.getId())) {
				synchronized (data) {
					if (TaskData.Status.WAIT == data.getStatus()) {
						data.setStatus(TaskData.Status.WORK);
						return data;
					}
				}
			}
		}
		return null;
	}

	public void updateTaskData(Long id, String str) throws Exception {
		for (TaskData data : tasks) {
			if (id.equals(data.getId())) {
				if (TaskData.Status.WORK == data.getStatus()) {
					data.update(str);
				}
			}
		}
	}

	public void execute(TaskData data) throws InterruptedException {
		data.setId(random.nextLong());
		data.setStatus(TaskData.Status.READY);
		tasks.add(data);

		synchronized (data) {
			for (int iter = 120, c = 0; c < iter; ++c) {
				try {
					data.wait(1000);
				} catch (InterruptedException ignore) {
				}

				if (TaskData.Status.WAIT == data.getStatus() && ((c % 10) == 9)) {
					data.setStatus(TaskData.Status.READY);
					break;
				}

				if (TaskData.Status.WORK == data.getStatus()) {
					iter = 1200;
					continue;
				}

				if (TaskData.Status.DONE == data.getStatus()) {
					tasks.remove(data);
					return;
				}
			}
		}
		throw new InterruptedException("Task execute timeout");
	}
}
