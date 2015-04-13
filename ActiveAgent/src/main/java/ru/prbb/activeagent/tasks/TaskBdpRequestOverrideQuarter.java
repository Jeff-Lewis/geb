package ru.prbb.activeagent.tasks;

public class TaskBdpRequestOverrideQuarter extends TaskData {

	private static final long serialVersionUID = 1L;

	private String over;
	private String[] securities;
	private String[] fields;
	private String[] currencies;

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

}
