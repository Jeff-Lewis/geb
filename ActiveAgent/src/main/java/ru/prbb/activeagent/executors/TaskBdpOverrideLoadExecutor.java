package ru.prbb.activeagent.executors;

import ru.prbb.activeagent.tasks.TaskBdpOverrideLoad;

public class TaskBdpOverrideLoadExecutor extends TaskBdpOverrideExecutor {

	@Override
	public String getType() {
		return TaskBdpOverrideLoad.class.getSimpleName();
	}
}
