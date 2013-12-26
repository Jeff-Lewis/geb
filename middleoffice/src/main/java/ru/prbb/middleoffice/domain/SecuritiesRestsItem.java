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
public class SecuritiesRestsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Byte check_flag;
	@Id
	private Long id;
	private String security_code;
	private String rest_date;
	private String client;
	private String fund;
	private Integer quantity;
	private BigDecimal price;
	private String currency;
	private Integer batch;
	private Integer lock_rate;
	private BigDecimal price_usd;
	private Integer quantity_flag;
	private BigDecimal nkd;
	private BigDecimal nkd_usd;
	private BigDecimal yield;
	private String date_insert;

	/**
	 * @return the check_flag
	 */
	public Byte getCheck_flag() {
		return check_flag;
	}

	/**
	 * @param check_flag
	 *            the check_flag to set
	 */
	public void setCheck_flag(Byte check_flag) {
		this.check_flag = check_flag;
	}

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
	 * @return the rest_date
	 */
	public String getRest_date() {
		return rest_date;
	}

	/**
	 * @param rest_date
	 *            the rest_date to set
	 */
	public void setRest_date(String rest_date) {
		this.rest_date = rest_date;
	}

	/**
	 * @return the client
	 */
	public String getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(String client) {
		this.client = client;
	}

	/**
	 * @return the fund
	 */
	public String getFund() {
		return fund;
	}

	/**
	 * @param fund
	 *            the fund to set
	 */
	public void setFund(String fund) {
		this.fund = fund;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the batch
	 */
	public Integer getBatch() {
		return batch;
	}

	/**
	 * @param batch
	 *            the batch to set
	 */
	public void setBatch(Integer batch) {
		this.batch = batch;
	}

	/**
	 * @return the lock_rate
	 */
	public Integer getLock_rate() {
		return lock_rate;
	}

	/**
	 * @param lock_rate
	 *            the lock_rate to set
	 */
	public void setLock_rate(Integer lock_rate) {
		this.lock_rate = lock_rate;
	}

	/**
	 * @return the price_usd
	 */
	public BigDecimal getPrice_usd() {
		return price_usd;
	}

	/**
	 * @param price_usd
	 *            the price_usd to set
	 */
	public void setPrice_usd(BigDecimal price_usd) {
		this.price_usd = price_usd;
	}

	/**
	 * @return the quantity_flag
	 */
	public Integer getQuantity_flag() {
		return quantity_flag;
	}

	/**
	 * @param quantity_flag
	 *            the quantity_flag to set
	 */
	public void setQuantity_flag(Integer quantity_flag) {
		this.quantity_flag = quantity_flag;
	}

	/**
	 * @return the nkd
	 */
	public BigDecimal getNkd() {
		return nkd;
	}

	/**
	 * @param nkd
	 *            the nkd to set
	 */
	public void setNkd(BigDecimal nkd) {
		this.nkd = nkd;
	}

	/**
	 * @return the nkd_usd
	 */
	public BigDecimal getNkd_usd() {
		return nkd_usd;
	}

	/**
	 * @param nkd_usd
	 *            the nkd_usd to set
	 */
	public void setNkd_usd(BigDecimal nkd_usd) {
		this.nkd_usd = nkd_usd;
	}

	/**
	 * @return the yield
	 */
	public BigDecimal getYield() {
		return yield;
	}

	/**
	 * @param yield
	 *            the yield to set
	 */
	public void setYield(BigDecimal yield) {
		this.yield = yield;
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
