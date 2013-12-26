/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author RBr
 * 
 */
public class SubscriptionItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String security_type;
	private String ticker;
	private String name;
	private BigDecimal last;
	private BigDecimal lastchange;
	private String lastchangetime;
	private String attention;

	/**
	 * @return the security_type
	 */
	public String getSecurity_type() {
		return security_type;
	}

	/**
	 * @param security_type
	 *            the security_type to set
	 */
	public void setSecurity_type(String security_type) {
		this.security_type = security_type;
	}

	/**
	 * @return the ticker
	 */
	public String getTicker() {
		return ticker;
	}

	/**
	 * @param ticker
	 *            the ticker to set
	 */
	public void setTicker(String ticker) {
		this.ticker = ticker;
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

	/**
	 * @return the last
	 */
	public BigDecimal getLast() {
		return last;
	}

	/**
	 * @param last
	 *            the last to set
	 */
	public void setLast(BigDecimal last) {
		this.last = last;
	}

	/**
	 * @return the lastchange
	 */
	public BigDecimal getLastchange() {
		return lastchange;
	}

	/**
	 * @param lastchange
	 *            the lastchange to set
	 */
	public void setLastchange(BigDecimal lastchange) {
		this.lastchange = lastchange;
	}

	/**
	 * @return the lastchangetime
	 */
	public String getLastchangetime() {
		return lastchangetime;
	}

	/**
	 * @param lastchangetime
	 *            the lastchangetime to set
	 */
	public void setLastchangetime(String lastchangetime) {
		this.lastchangetime = lastchangetime;
	}

	/**
	 * @return the attention
	 */
	public String getAttention() {
		return attention;
	}

	/**
	 * @param attention
	 *            the attention to set
	 */
	public void setAttention(String attention) {
		this.attention = attention;
	}
}
