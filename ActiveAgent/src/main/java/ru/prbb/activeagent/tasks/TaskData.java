package ru.prbb.activeagent.tasks;

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

	public void setResult(Object result) {
	}

	public void setType(String type) {
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

	
	public void setName(String name) {
		this.name = name;
	}

	public void update(String data) throws Exception {
		if ("DONE".equals(data)) {
			status = Status.DONE;
			return;
		}

		if (data.startsWith("ERROR:")) {
			throw new Exception(data.substring(6));
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
