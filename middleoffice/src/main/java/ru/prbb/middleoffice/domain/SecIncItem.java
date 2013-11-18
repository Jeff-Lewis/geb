/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class SecIncItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long sip;
	private String security_code;
	private String short_name;
	private String country;
	private String country_code;
	private String security_incorporation_period;
	private String tax_value;
	private String tax_period;

	public Long getSip() {
		return sip;
	}

	public void setSip(Long sip) {
		this.sip = sip;
	}

	public String getSecurity_code() {
		return security_code;
	}

	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getSecurity_incorporation_period() {
		return security_incorporation_period;
	}

	public void setSecurity_incorporation_period(String security_incorporation_period) {
		this.security_incorporation_period = security_incorporation_period;
	}

	public String getTax_value() {
		return tax_value;
	}

	public void setTax_value(String tax_value) {
		this.tax_value = tax_value;
	}

	public String getTax_period() {
		return tax_period;
	}

	public void setTax_period(String tax_period) {
		this.tax_period = tax_period;
	}
}
