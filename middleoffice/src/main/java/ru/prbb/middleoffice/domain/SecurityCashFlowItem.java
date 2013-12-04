/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class SecurityCashFlowItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String security_code;
	private String fst_settle_date;
	private String bond;

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
	 * @return the fst_settle_date
	 */
	public String getFst_settle_date() {
		return fst_settle_date;
	}

	/**
	 * @param fst_settle_date
	 *            the fst_settle_date to set
	 */
	public void setFst_settle_date(String fst_settle_date) {
		this.fst_settle_date = fst_settle_date;
	}

	/**
	 * @return the bond
	 */
	public String getBond() {
		return bond;
	}

	/**
	 * @param bond
	 *            the bond to set
	 */
	public void setBond(String bond) {
		this.bond = bond;
	}
}
