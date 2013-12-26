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
public class ViewDealsREPOItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String client;
	private String broker;
	private String account;
	private String security_code;
	private String currency;
	private String deal_num;
	private String operation;
	private String deal_date;
	private BigDecimal price1;
	private Integer quantity;
	private BigDecimal volume1;
	private Integer days;
	private String reverse_date;
	private BigDecimal rate;
	private BigDecimal price2;
	private BigDecimal volume2;

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
	 * @return the broker
	 */
	public String getBroker() {
		return broker;
	}

	/**
	 * @param broker
	 *            the broker to set
	 */
	public void setBroker(String broker) {
		this.broker = broker;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
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
	 * @return the deal_num
	 */
	public String getDeal_num() {
		return deal_num;
	}

	/**
	 * @param deal_num
	 *            the deal_num to set
	 */
	public void setDeal_num(String deal_num) {
		this.deal_num = deal_num;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the deal_date
	 */
	public String getDeal_date() {
		return deal_date;
	}

	/**
	 * @param deal_date
	 *            the deal_date to set
	 */
	public void setDeal_date(String deal_date) {
		this.deal_date = deal_date;
	}

	/**
	 * @return the price1
	 */
	public BigDecimal getPrice1() {
		return price1;
	}

	/**
	 * @param price1
	 *            the price1 to set
	 */
	public void setPrice1(BigDecimal price1) {
		this.price1 = price1;
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
	 * @return the volume1
	 */
	public BigDecimal getVolume1() {
		return volume1;
	}

	/**
	 * @param volume1
	 *            the volume1 to set
	 */
	public void setVolume1(BigDecimal volume1) {
		this.volume1 = volume1;
	}

	/**
	 * @return the days
	 */
	public Integer getDays() {
		return days;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(Integer days) {
		this.days = days;
	}

	/**
	 * @return the reverse_date
	 */
	public String getReverse_date() {
		return reverse_date;
	}

	/**
	 * @param reverse_date
	 *            the reverse_date to set
	 */
	public void setReverse_date(String reverse_date) {
		this.reverse_date = reverse_date;
	}

	/**
	 * @return the rate
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * @return the price2
	 */
	public BigDecimal getPrice2() {
		return price2;
	}

	/**
	 * @param price2
	 *            the price2 to set
	 */
	public void setPrice2(BigDecimal price2) {
		this.price2 = price2;
	}

	/**
	 * @return the volume2
	 */
	public BigDecimal getVolume2() {
		return volume2;
	}

	/**
	 * @param volume2
	 *            the volume2 to set
	 */
	public void setVolume2(BigDecimal volume2) {
		this.volume2 = volume2;
	}
}
