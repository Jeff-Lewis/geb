package ru.prbb.middleoffice.domain.tasks;

import java.io.Serializable;

public class TaskData implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Status {
		READY,
		WAIT,
		WORK,
		DONE
	}

	private transient Status status = Status.READY;

	private String name;

	public TaskData(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void update(String str) {
		// TODO Auto-generated method stub
		
	}
}
