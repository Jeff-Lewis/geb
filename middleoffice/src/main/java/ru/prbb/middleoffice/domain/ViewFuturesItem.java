/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class ViewFuturesItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id_sec;
	private String ticker;
	private String deal_name;
	private BigDecimal coef;
	private String name;
	private String date_insert;

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
	 * @return the deal_name
	 */
	public String getDeal_name() {
		return deal_name;
	}

	/**
	 * @param deal_name
	 *            the deal_name to set
	 */
	public void setDeal_name(String deal_name) {
		this.deal_name = deal_name;
	}

	/**
	 * @return the coef
	 */
	public BigDecimal getCoef() {
		return coef;
	}

	/**
	 * @param coef
	 *            the coef to set
	 */
	public void setCoef(BigDecimal coef) {
		this.coef = coef;
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
	 * @return the date_insert
	 */
	public String getDate_insert() {
		return date_insert;
	}

	/**
	 * @param date_insert
	 *            the date_insert to set
	 */
	public void setDate_insert(String date_insert) {
		this.date_insert = date_insert;
	}
}
