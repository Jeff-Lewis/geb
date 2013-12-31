/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class TransferOperationsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@Column(name = "Batch")
	private Integer batch;
	@Column(name = "Security")
	private String security;
	@Column(name = "Operation")
	private String operation;
	@Column(name = "Quantity")
	private Integer quantity;
	@Column(name = "Price")
	private BigDecimal price;
	@Column(name = "Currency")
	private String currency;
	@Column(name = "TradeDate")
	private String tradeDate;
	@Column(name = "TradeSystem")
	private String tradeSystem;
	@Column(name = "Broker")
	private String broker;
	@Column(name = "Account")
	private String account;
	@Column(name = "Client")
	private String client;
	@Column(name = "Portfolio")
	private String portfolio;
	@Column(name = "Funding")
	private Integer funding;

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
	 * @return the security
	 */
	public String getSecurity() {
		return security;
	}

	/**
	 * @param security
	 *            the security to set
	 */
	public void setSecurity(String security) {
		this.security = security;
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
	 * @return the tradeDate
	 */
	public String getTradeDate() {
		return tradeDate;
	}

	/**
	 * @param tradeDate
	 *            the tradeDate to set
	 */
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	/**
	 * @return the tradeSystem
	 */
	public String getTradeSystem() {
		return tradeSystem;
	}

	/**
	 * @param tradeSystem
	 *            the tradeSystem to set
	 */
	public void setTradeSystem(String tradeSystem) {
		this.tradeSystem = tradeSystem;
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
	 * @return the portfolio
	 */
	public String getPortfolio() {
		return portfolio;
	}

	/**
	 * @param portfolio
	 *            the portfolio to set
	 */
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * @return the funding
	 */
	public Integer getFunding() {
		return funding;
	}

	/**
	 * @param funding
	 *            the funding to set
	 */
	public void setFunding(Integer funding) {
		this.funding = funding;
	}
}
