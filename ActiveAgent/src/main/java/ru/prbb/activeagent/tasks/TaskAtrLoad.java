package ru.prbb.activeagent.tasks;

public class TaskAtrLoad extends TaskData {

	private static final long serialVersionUID = 1L;

	private String dateStart;
	private String dateEnd;
	private String[] securities;
	private String maType;
	private Integer taPeriod;
	private String period;
	private String calendar;

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

	public String getMaType() {
		return maType;
	}

	public void setMaType(String maType) {
		this.maType = maType;
	}

	public Integer getTaPeriod() {
		return taPeriod;
	}

	public void setTaPeriod(Integer taPeriod) {
		this.taPeriod = taPeriod;
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

}
