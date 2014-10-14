/**
 * 
 */
package ru.prbb.jobber.domain;

import java.sql.Date;

/**
 * @author RBr
 * 
 */
public class AtrDataItem {
	public final String security;
	public final Date date_time;
	public final Double atr_value;
	public static final Integer atr_period = 7;
	public static final String algorithm = "Exponential";
	public static final String ds_high_code = "PX_HIGH";
	public static final String ds_low_code = "PX_LOW";
	public static final String ds_close_code = "PX_LAST";
	public static final String period = "DAILY";
	public static final String calendar = "CALENDAR";

	public AtrDataItem(String security, Date date_time, Double atr_value) {
		this.security = security;
		this.date_time = date_time;
		this.atr_value = atr_value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AtrDataItem [security=");
		builder.append(security);
		builder.append(", date_time=");
		builder.append(date_time);
		builder.append(", atr_value=");
		builder.append(atr_value);
		builder.append("]");
		return builder.toString();
	}
}
