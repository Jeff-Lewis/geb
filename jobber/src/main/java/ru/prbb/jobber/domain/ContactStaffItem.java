/**
 * 
 */
package ru.prbb.jobber.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class ContactStaffItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long cid;
	private String value;

	/**
	 * @return the cid
	 */
	public Long getCid() {
		return cid;
	}

	/**
	 * @param cid
	 *            the cid to set
	 */
	public void setCid(Long cid) {
		this.cid = cid;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
