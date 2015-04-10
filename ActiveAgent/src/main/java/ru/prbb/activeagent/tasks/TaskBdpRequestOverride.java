package ru.prbb.activeagent.tasks;

import java.util.Map;

public class TaskBdpRequestOverride extends TaskData {

	private static final long serialVersionUID = 1L;

	private String period;
	private String over;
	private String[] securities;
	private String[] fields;

	public TaskBdpRequestOverride(String name) {
		super(name);
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getOver() {
		return over;
	}

	public void setOver(String over) {
		this.over = over;
	}

	public String[] getSecurities() {
		return securities;
	}

	public void setSecurities(String[] securities) {
		this.securities = securities;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public Map<String, Map<String, Map<String, String>>> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
