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
public class SwapItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String swap;
	private String related_security;

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
	 * @return the swap
	 */
	public String getSwap() {
		return swap;
	}

	/**
	 * @param swap
	 *            the swap to set
	 */
	public void setSwap(String swap) {
		this.swap = swap;
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
}
