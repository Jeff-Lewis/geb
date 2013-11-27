/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class CompanyItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id_sec;
	private String id_isin;
	private String security_code;
	private String security_name;
	private String currency;
	private String indstry_grp;

	/**
	 * @return the id_sec
	 */
	public Long getId_sec() {
		return id_sec;
	}

	/**
	 * @param id_sec
	 *            the id_sec to set
	 */
	public void setId_sec(Long id_sec) {
		this.id_sec = id_sec;
	}

	/**
	 * @return the id_isin
	 */
	public String getId_isin() {
		return id_isin;
	}

	/**
	 * @param id_isin
	 *            the id_isin to set
	 */
	public void setId_isin(String id_isin) {
		this.id_isin = id_isin;
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
	 * @return the security_name
	 */
	public String getSecurity_name() {
		return security_name;
	}

	/**
	 * @param security_name
	 *            the security_name to set
	 */
	public void setSecurity_name(String security_name) {
		this.security_name = security_name;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the indstry_grp
	 */
	public String getIndstry_grp() {
		return indstry_grp;
	}

	/**
	 * @param indstry_grp
	 *            the indstry_grp to set
	 */
	public void setIndstry_grp(String indstry_grp) {
		this.indstry_grp = indstry_grp;
	}
}
