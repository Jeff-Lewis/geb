/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;

import ru.prbb.Utils;

/**
 * @author RBr
 * 
 */
public class ViewQuotesItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "id_sec")
	private Long id_sec;
	@Column(name = "SecurityCode")
	private String securityCode;
	@Column(name = "ShortName")
	private String shortName;
	@Column(name = "SecurityType")
	private String securityType;
	@Column(name = "QuoteDate")
	private String quoteDate;
	@Column(name = "Price")
	private Number price;
	@Column(name = "Closeprice")
	private Number closeprice;

	
	public ViewQuotesItem() {
	}

	public ViewQuotesItem(Object[] arr) {
		setId_sec(Utils.toLong(arr[0]));
		setSecurityCode(Utils.toString(arr[1]));
		setShortName(Utils.toString(arr[2]));
		setSecurityType(Utils.toString(arr[3]));
		setQuoteDate(Utils.toDate(arr[4]));
		setPrice(Utils.toDouble(arr[5]));
		setCloseprice(Utils.toDouble(arr[6]));
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
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName
	 *            the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
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
	 * @return the quoteDate
	 */
	public String getQuoteDate() {
		return quoteDate;
	}

	/**
	 * @param quoteDate
	 *            the quoteDate to set
	 */
	public void setQuoteDate(String quoteDate) {
		this.quoteDate = quoteDate;
	}

	/**
	 * @return the price
	 */
	public Number getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Number price) {
		this.price = price;
	}

	/**
	 * @return the closeprice
	 */
	public Number getCloseprice() {
		return closeprice;
	}

	/**
	 * @param closeprice
	 *            the closeprice to set
	 */
	public void setCloseprice(Number closeprice) {
		this.closeprice = closeprice;
	}
}
