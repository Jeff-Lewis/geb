package ru.prbb.middleoffice.domain;

public class AgentTask {

	private long idTask;
	private String json;
	private String result;

	public AgentTask(long idTask, String json) {
		this.idTask = idTask;
		this.json = json;
	}

	public long getIdTask() {
		return idTask;
	}

	public String getJson() {
		return json;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idTask ^ (idTask >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentTask other = (AgentTask) obj;
		if (idTask != other.idTask)
			return false;
		return true;
	}
}
