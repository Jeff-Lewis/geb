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
 */
@Entity
public class ViewDealsItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@Column(name = "Batch")
	private Integer batch;
	@Column(name = "TradeNum")
	private String tradeNum;
	@Column(name = "SecShortName")
	private String secShortName;
	@Column(name = "Operation")
	private String operation;
	@Column(name = "Quantity")
	private Integer quantity;
	@Column(name = "Price")
	private BigDecimal price;
	@Column(name = "PriceNKD")
	private BigDecimal priceNKD;
	@Column(name = "Currency")
	private String currency;
	@Column(name = "TradeDate")
	private String tradeDate;
	@Column(name = "SettleDate")
	private String settleDate;
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
	private Byte funding;
	@Column(name = "Initiator")
	private String initiator;

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
	 * @return the tradeNum
	 */
	public String getTradeNum() {
		return tradeNum;
	}

	/**
	 * @param tradeNum
	 *            the tradeNum to set
	 */
	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}

	/**
	 * @return the secShortName
	 */
	public String getSecShortName() {
		return secShortName;
	}

	/**
	 * @param secShortName
	 *            the secShortName to set
	 */
	public void setSecShortName(String secShortName) {
		this.secShortName = secShortName;
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
	 * @return the priceNKD
	 */
	public BigDecimal getPriceNKD() {
		return priceNKD;
	}

	/**
	 * @param priceNKD
	 *            the priceNKD to set
	 */
	public void setPriceNKD(BigDecimal priceNKD) {
		this.priceNKD = priceNKD;
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
	 * @return the settleDate
	 */
	public String getSettleDate() {
		return settleDate;
	}

	/**
	 * @param settleDate
	 *            the settleDate to set
	 */
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
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
	public Byte getFunding() {
		return funding;
	}

	/**
	 * @param funding
	 *            the funding to set
	 */
	public void setFunding(Byte funding) {
		this.funding = funding;
	}

	
	public String getInitiator() {
		return initiator;
	}

	
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
}
