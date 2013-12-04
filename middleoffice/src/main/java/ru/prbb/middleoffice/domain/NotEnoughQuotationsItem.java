/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class NotEnoughQuotationsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_sec")
	private Long securityId;
	@Column(name = "SecurityCode")
	private String securityCode;
	@Column(name = "SecurityType")
	private String securityType;
	@Column(name = "FirstTradeDate")
	private String dateFirstTrade;

	/**
	 * @return the securityId
	 */
	public Long getSecurityId() {
		return securityId;
	}

	/**
	 * @param securityId
	 *            the securityId to set
	 */
	public void setSecurityId(Long securityId) {
		this.securityId = securityId;
	}

	/**
	 * @return the securityCode
	 */
	public String getSecurityCode() {
		return securityCode;
	}

	/**
	 * @param securityCode
	 *            the securityCode to set
	 */
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	/**
	 * @return the securityType
	 */
	public String getSecurityType() {
		return securityType;
	}

	/**
	 * @param securityType
	 *            the securityType to set
	 */
	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	/**
	 * @return the dateFirstTrade
	 */
	public String getDateFirstTrade() {
		return dateFirstTrade;
	}

	/**
	 * @param dateFirstTrade
	 *            the dateFirstTrade to set
	 */
	public void setDateFirstTrade(String dateFirstTrade) {
		this.dateFirstTrade = dateFirstTrade;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotEnoughQuotationsItem) {
			return securityId.equals(((NotEnoughQuotationsItem) obj).securityId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return securityId.hashCode();
	}

	@Override
	public String toString() {
		return securityCode + '(' + securityId + ')';
	}
}
