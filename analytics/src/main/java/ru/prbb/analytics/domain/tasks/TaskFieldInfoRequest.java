package ru.prbb.analytics.domain.tasks;

import java.util.Map;

public class TaskFieldInfoRequest extends TaskData {

	private static final long serialVersionUID = 1L;

	private String code;

	public TaskFieldInfoRequest(String name) {
		super(name);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Object> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
