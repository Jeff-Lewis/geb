/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import ru.prbb.Utils;

/**
 * @author RBr
 * 
 */
@Entity
public class ViewPortfolioItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id_sec;
	private String security_code;

	
	public ViewPortfolioItem() {
	}

	public ViewPortfolioItem(Object[] arr) {
		id_sec = Utils.toLong(arr[0]);
		security_code = Utils.toString(arr[1]);
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ViewPortfolioItem) {
			return id_sec.equals(((ViewPortfolioItem) obj).id_sec);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id_sec.hashCode();
	}

	@Override
	public String toString() {
		return security_code + '(' + id_sec + ')';
	}
}
