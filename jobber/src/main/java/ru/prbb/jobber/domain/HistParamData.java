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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HistParamData [security=");
		builder.append(security);
		builder.append(", params=");
		builder.append(params);
		builder.append(", date=");
		builder.append(date);
		builder.append(", value=");
		builder.append(value);
		builder.append(", period=");
		builder.append(period);
		builder.append(", curncy=");
		builder.append(curncy);
		builder.append(", calendar=");
		builder.append(calendar);
		builder.append("]");
		return builder.toString();
	}
}
