package ru.prbb.jobber.domain.tasks;

import java.io.Serializable;

public class TaskData implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Status {
		READY,
		WAIT,
		WORK,
		DONE
	}

	private transient Status status;

	private Long id;
	private String name;

	public TaskData(String name) {
		this.name = name;
	}

	public String getType() {
		return getClass().getSimpleName();
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void update(String str) {
		if ("DONE".equals(str)) {
			status = Status.DONE;
			return;
		}
		
	}

	@Override
	public int hashCode() {
		return (id == null) ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskData other = (TaskData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskData [id=" + id + ", name=" + name + ", status=" + status + "]";
	}
}
