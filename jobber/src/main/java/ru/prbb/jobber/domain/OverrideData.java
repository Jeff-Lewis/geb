/**
 * 
 */
package ru.prbb.jobber.domain;


public class OverrideData {

	public final String security;
	public static final String param = "BEST_EPS_GAAP";
	public final String value;
	public final String period;
	public static final String blm_data_src_over = "BST";

	public OverrideData(String security, String value, String period) {
		this.security = security;
		this.value = value;
		this.period = period;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OverrideData [security=");
		builder.append(security);
		builder.append(", value=");
		builder.append(value);
		builder.append(", period=");
		builder.append(period);
		builder.append("]");
		return builder.toString();
	}
}
