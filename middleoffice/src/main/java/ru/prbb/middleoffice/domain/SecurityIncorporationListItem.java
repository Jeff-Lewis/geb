/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Регистрация инструментов
 * 
 * @author RBr
 * 
 */
@Entity
public class SecurityIncorporationListItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long sip;
	private String security_code;
	private String short_name;
	private String country;
	private String security_incorporation_period;
	private BigDecimal tax_value;
	private String tax_period;
	/**
	 * @return the sip
	 */
	public Long getSip() {
		return sip;
	}
	/**
	 * @param sip the sip to set
	 */
	public void setSip(Long sip) {
		this.sip = sip;
	}
	/**
	 * @return the security_code
	 */
	public String getSecurity_code() {
		return security_code;
	}
	/**
	 * @param security_code the security_code to set
	 */
	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}
	/**
	 * @return the short_name
	 */
	public String getShort_name() {
		return short_name;
	}
	/**
	 * @param short_name the short_name to set
	 */
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the security_incorporation_period
	 */
	public String getSecurity_incorporation_period() {
		return security_incorporation_period;
	}
	/**
	 * @param security_incorporation_period the security_incorporation_period to set
	 */
	public void setSecurity_incorporation_period(String security_incorporation_period) {
		this.security_incorporation_period = security_incorporation_period;
	}
	/**
	 * @return the tax_value
	 */
	public BigDecimal getTax_value() {
		return tax_value;
	}
	/**
	 * @param tax_value the tax_value to set
	 */
	public void setTax_value(BigDecimal tax_value) {
		this.tax_value = tax_value;
	}
	/**
	 * @return the tax_period
	 */
	public String getTax_period() {
		return tax_period;
	}
	/**
	 * @param tax_period the tax_period to set
	 */
	public void setTax_period(String tax_period) {
		this.tax_period = tax_period;
	}
}
