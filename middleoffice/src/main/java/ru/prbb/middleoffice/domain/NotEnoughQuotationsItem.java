/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class NotEnoughQuotationsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id_sec;
	private String SecurityCode;
	private String SecurityType;
	private String FirstTradeDate;

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
	 * @return the securityCode
	 */
	public String getSecurityCode() {
		return SecurityCode;
	}

	/**
	 * @param securityCode
	 *            the securityCode to set
	 */
	public void setSecurityCode(String securityCode) {
		SecurityCode = securityCode;
	}

	/**
	 * @return the securityType
	 */
	public String getSecurityType() {
		return SecurityType;
	}

	/**
	 * @param securityType
	 *            the securityType to set
	 */
	public void setSecurityType(String securityType) {
		SecurityType = securityType;
	}

	/**
	 * @return the firstTradeDate
	 */
	public String getFirstTradeDate() {
		return FirstTradeDate;
	}

	/**
	 * @param firstTradeDate
	 *            the firstTradeDate to set
	 */
	public void setFirstTradeDate(String firstTradeDate) {
		FirstTradeDate = firstTradeDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotEnoughQuotationsItem) {
			return id_sec.equals(((NotEnoughQuotationsItem) obj).id_sec);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id_sec.hashCode();
	}

	@Override
	public String toString() {
		return SecurityCode + '(' + id_sec + ')';
	}
}
