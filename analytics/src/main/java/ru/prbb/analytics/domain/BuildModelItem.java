/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Расчёт модели по компании
 * 
 * @author RBr
 * 
 */
@Entity
public class BuildModelItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long security_code;
	private String status;

	/**
	 * @return the security_code
	 */
	public Long getSecurity_code() {
		return security_code;
	}

	/**
	 * @param security_code
	 *            the security_code to set
	 */
	public void setSecurity_code(Long security_code) {
		this.security_code = security_code;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return security_code.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (null == obj)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildModelItem other = (BuildModelItem) obj;
		if (security_code == null) {
			if (other.security_code != null)
				return false;
		} else if (!security_code.equals(other.security_code))
			return false;
		return true;
	}
}
