package ru.prbb.analytics.domain.tasks;

import java.util.Map;

public class TaskBdpRequest extends TaskData {

	private static final long serialVersionUID = 1L;

	private String[] securities;
	private String[] fields;

	public TaskBdpRequest(String name) {
		super(name);
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

	public Map<String, Map<String, String>> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
