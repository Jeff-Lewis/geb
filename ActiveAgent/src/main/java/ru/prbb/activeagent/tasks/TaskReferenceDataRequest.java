package ru.prbb.activeagent.tasks;

public class TaskReferenceDataRequest extends TaskData {

	private static final long serialVersionUID = 1L;

	private String[] securities;
	private String[] fields;

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

}
