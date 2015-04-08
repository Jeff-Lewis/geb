package ru.prbb.analytics.domain.tasks;

import java.util.Map;

public class TaskBdhRequest extends TaskData {

	private static final long serialVersionUID = 1L;

	private String dateStart;
	private String dateEnd;
	private String period;
	private String calendar;
	private String[] currencies;
	private String[] securities;
	private String[] fields;

	public TaskBdhRequest(String name) {
		super(name);
	}

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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCalendar() {
		return calendar;
	}

	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

	public String[] getCurrencies() {
		return currencies;
	}

	public void setCurrencies(String[] currencies) {
		this.currencies = currencies;
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

	public Map<String, Map<String, Map<String, String>>> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
