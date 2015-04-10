package ru.prbb.activeagent.tasks;

import java.util.Map;

public class TaskRequestOverrideQuarter extends TaskData {

	private static final long serialVersionUID = 1L;

	private String over;
	private String[] securities;
	private String[] fields;
	private String[] currencies;

	public TaskRequestOverrideQuarter(String name) {
		super(name);
	}

	public String getOver() {
		return over;
	}

	public void setOver(String over) {
		this.over = over;
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

	public String[] getCurrencies() {
		return currencies;
	}

	public void setCurrencies(String[] currencies) {
		this.currencies = currencies;
	}

	public Map<String, Map<String, Map<String, String>>> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
