package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;

public class PortfolioWatchListItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "short_name")
	private String shortName;
	@Column(name = "security_code")
	private String securityCode;
	@Column(name = "period_id")
	private Number period;

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public Number getPeriod() {
		return period;
	}

	public void setPeriod(Number period) {
		this.period = period;
	}

}
