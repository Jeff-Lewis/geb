package ru.prbb.jobber.domain.tasks;

import java.util.Map;

public class TaskBdpOverrideLoad extends TaskData {

	private static final long serialVersionUID = 1L;

	private String[] securities;
	private String[] currencies;

	public TaskBdpOverrideLoad(String name) {
		super(name);
	}

	public String[] getSecurities() {
		return securities;
	}

	public void setSecurities(String[] securities) {
		this.securities = securities;
	}

	public String[] getCurrencies() {
		return currencies;
	}

	public void setCurrencies(String[] currencies) {
		this.currencies = currencies;
	}

	public Map<String, Map<String, String>> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
