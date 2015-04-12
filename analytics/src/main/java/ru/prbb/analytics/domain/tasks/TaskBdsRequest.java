package ru.prbb.analytics.domain.tasks;

import java.util.Map;

public class TaskBdsRequest extends TaskData {

	private static final long serialVersionUID = 1L;

	private String[] securities;
	private String[] fields;

	public TaskBdsRequest(String name) {
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

	public Map<String, Object> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleData(String data) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
