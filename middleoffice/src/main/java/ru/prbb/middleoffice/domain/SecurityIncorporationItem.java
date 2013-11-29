/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Регистрация инструментов
 * 
 * @author RBr
 * 
 */
@Entity
public class SecurityIncorporationItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String security_code;
	private String country;
	private String date_begin;
	private String date_end;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the security_code
	 */
	public String getSecurity_code() {
		return security_code;
	}

	/**
	 * @param security_code
	 *            the security_code to set
	 */
	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the date_begin
	 */
	public String getDate_begin() {
		return date_begin;
	}

	/**
	 * @param date_begin
	 *            the date_begin to set
	 */
	public void setDate_begin(String date_begin) {
		this.date_begin = date_begin;
	}

	/**
	 * @return the date_end
	 */
	public String getDate_end() {
		return date_end;
	}

	/**
	 * @param date_end
	 *            the date_end to set
	 */
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
}
