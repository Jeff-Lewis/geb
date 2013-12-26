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
public class EquitiesItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id_sec;
	private String security_code;
	private String short_name;
	private String calculation_crncy;

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

	/**
	 * @return the calculation_crncy
	 */
	public String getCalculation_crncy() {
		return calculation_crncy;
	}

	/**
	 * @param calculation_crncy
	 *            the calculation_crncy to set
	 */
	public void setCalculation_crncy(String calculation_crncy) {
		this.calculation_crncy = calculation_crncy;
	}

	@Override
	public int hashCode() {
		return id_sec.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (null == obj)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquitiesItem other = (EquitiesItem) obj;
		if (id_sec == null) {
			if (other.id_sec != null)
				return false;
		} else if (!id_sec.equals(other.id_sec))
			return false;
		return true;
	}
}
