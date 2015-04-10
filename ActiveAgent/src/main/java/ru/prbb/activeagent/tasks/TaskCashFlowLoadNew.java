package ru.prbb.activeagent.tasks;

import java.util.List;
import java.util.Map;

public class TaskCashFlowLoadNew extends TaskData {

	private static final long serialVersionUID = 1L;

	private String[] ids;
	private String[] dates;

	public TaskCashFlowLoadNew(String name) {
		super(name);
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getDates() {
		return dates;
	}

	public void setDates(String[] dates) {
		this.dates = dates;
	}

	public List<Map<String, Object>> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
