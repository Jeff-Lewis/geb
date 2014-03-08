/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 */
@Entity
public class CompanyAllItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_sec")
	private Long id;
	private String security_code;
	private String short_name;

	public Long getId() {
		return id;
	}

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
	 * @return the short_name
	 */
	public String getShort_name() {
		return short_name;
	}

	/**
	 * @param short_name
	 *            the short_name to set
	 */
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

}
