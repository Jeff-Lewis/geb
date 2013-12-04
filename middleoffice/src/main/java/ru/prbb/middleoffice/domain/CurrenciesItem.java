/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Валюты
 * 
 * @author RBr
 * 
 */
@Entity
public class CurrenciesItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long code;
	private String iso;
	private String name;

	/**
	 * @return the code
	 */
	public Long getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(Long code) {
		this.code = code;
	}

	/**
	 * @return the iso
	 */
	public String getIso() {
		return iso;
	}

	/**
	 * @param iso
	 *            the iso to set
	 */
	public void setIso(String iso) {
		this.iso = iso;
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
