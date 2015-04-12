package ru.prbb.analytics.domain.tasks;

import java.util.HashMap;
import java.util.Map;

public class TaskBdpRequestOverrideQuarter extends TaskData {

	private static final long serialVersionUID = 1L;

	private String over;
	private String[] securities;
	private String[] fields;
	private String[] currencies;

	private transient final Map<String, Map<String, Map<String, String>>> result = new HashMap<>();

	public TaskBdpRequestOverrideQuarter(String name) {
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
		return result;
	}

	@Override
	protected void handleData(String data) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
