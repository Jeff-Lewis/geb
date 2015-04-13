package ru.prbb.activeagent.tasks;

public class TaskHistoricalDataRequest extends TaskData {

	private static final long serialVersionUID = 1L;

	private String dateStart;
	private String dateEnd;
	private String[] securities;
	private String[] fields;
	private String[] currencies;

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
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
