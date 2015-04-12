package ru.prbb.activeagent.tasks;


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

}
