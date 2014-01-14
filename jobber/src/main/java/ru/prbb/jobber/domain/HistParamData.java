/**
 * 
 */
package ru.prbb.jobber.domain;

public class HistParamData {
	public final String security;
	public final String params;
	public final String date;
	public final String value;
	public final String period;
	public final String curncy;
	public final String calendar;

	public HistParamData(String security, String params, String date, String value, String period, String curncy,
			String calendar) {
		this.security = security;
		this.params = params;
		this.date = date;
		this.value = value;
		this.period = period;
		this.curncy = curncy;
		this.calendar = calendar;
	}
}