package ru.prbb.analytics.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.prbb.analytics.domain.tasks.TaskData;
import ru.prbb.analytics.domain.tasks.TaskItem;

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
			if (TaskData.Status.READY == data.getStatus()) {
				data.setStatus(TaskData.Status.WAIT);
				TaskItem task = new TaskItem();
				task.setId(data.getId());
				task.setType(data.getType());
				return task;
			}
		}
		return EMPTY_TASK;
	}

	public TaskData getTaskData(Long id) {
		for (TaskData data : tasks) {
			if (id.equals(data.getId())) {
				if (TaskData.Status.WAIT == data.getStatus()) {
					data.setStatus(TaskData.Status.WORK);
					return data;
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
					data.notify();
				}
			}
		}
	}

	public void execute(TaskData data) throws InterruptedException {
		log.info(data.getName());
		
		data.setId(random.nextLong());
		data.setStatus(TaskData.Status.READY);
		
		tasks.add(data);

		try {
			for (int iter = 120, r = 0, c = 0; c < iter; ++c) {
				try {
					synchronized (data) {
						data.wait(1000);
					}
				} catch (InterruptedException ignore) {
				}

				switch (data.getStatus()) {
				case WORK:
					iter = 1200;
					break;
					
				case DONE:
					return;
					
				case WAIT:
					if ((c - r) > 10) {
						data.setStatus(TaskData.Status.READY);
						r = c = 0;
					}
					break;
					
				default:
					r = c;
				}
			}
			
			throw new InterruptedException("Task execute timeout");
		} finally {
			tasks.remove(data);
		}
	}
}
