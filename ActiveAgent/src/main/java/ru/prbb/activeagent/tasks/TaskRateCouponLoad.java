package ru.prbb.activeagent.tasks;

import java.util.List;
import java.util.Map;

public class TaskRateCouponLoad extends TaskData {

	private static final long serialVersionUID = 1L;

	private String[] ids;

	public TaskRateCouponLoad(String name) {
		super(name);
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public List<Map<String, Object>> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
