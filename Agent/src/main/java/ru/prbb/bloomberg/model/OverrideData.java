/**
 * 
 */
package ru.prbb.bloomberg.model;

import ru.prbb.bloomberg.request.BdpOverrideRequest;

public class OverrideData {
	public final String security;
	public static final String param = BdpOverrideRequest.BEST_EPS_GAAP;
	public final String value;
	public final String period;
	public static final String blm_data_src_over = "BST";

	public OverrideData(String security, String value, String period) {
		this.security = security;
		this.value = value;
		this.period = period;
	}
}