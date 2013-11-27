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
public class NewParamItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;
	private String blmId;
	private String name;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the blmId
	 */
	public String getBlmId() {
		return blmId;
	}

	/**
	 * @param blmId
	 *            the blmId to set
	 */
	public void setBlmId(String blmId) {
		this.blmId = blmId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
