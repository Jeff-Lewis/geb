/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import ru.prbb.Utils;

/**
 * @author RBr
 * 
 */
@Entity
public class ViewCompaniesEpsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id_sec;
	private String sector;
	@Column(name = "EPS")
	private String eps;
	private String related_security;
	private String security_code;

	
	public ViewCompaniesEpsItem() {
	}

	public ViewCompaniesEpsItem(Object[] arr) {
		int idx = 0;
		id_sec = Utils.toLong(arr[idx++]);
		sector = Utils.toString(arr[idx++]);
		eps = Utils.toString(arr[idx++]);
		related_security = Utils.toString(arr[idx++]);
		security_code = Utils.toString(arr[idx++]);
	}

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
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param sector
	 *            the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}

	/**
	 * @return the EPS
	 */
	public String getEPS() {
		return eps;
	}

	/**
	 * @param eps
	 *            the EPS to set
	 */
	public void setEPS(String eps) {
		this.eps = eps;
	}

	/**
	 * @return the related_security
	 */
	public String getRelated_security() {
		return related_security;
	}

	/**
	 * @param related_security
	 *            the related_security to set
	 */
	public void setRelated_security(String related_security) {
		this.related_security = related_security;
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
}
