package ru.prbb.jobber.domain;

public class AgentTask {

	private String json;
	private String result;

	public AgentTask(String json) {
		this.json = json;
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
}
