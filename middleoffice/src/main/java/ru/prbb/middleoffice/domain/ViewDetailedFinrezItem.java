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
public class ViewDetailedFinrezItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "Client")
	private String client;
	@Column(name = "Portfolio")
	private String portfolio;
	@Column(name = "Security_id")
	private Long security_id;
	@Column(name = "Security_code")
	private String security_code;
	@Column(name = "Batch")
	private Integer batch;
	@Column(name = "Deals_realised_profit_id")
	private Long deals_realised_profit_id;
	@Id
	@Column(name = "Deal_id")
	private Long deal_id;
	@Column(name = "TradeNum")
	private String tradeNum;
	@Column(name = "Trade_date")
	private String trade_date;
	@Column(name = "Operation")
	private String operation;
	@Column(name = "Realised_profit")
	private BigDecimal realised_profit;
	@Column(name = "Initial_quantity")
	private Integer initial_quantity;
	@Column(name = "Deal_quantity")
	private Integer deal_quantity;
	@Column(name = "Avg_price")
	private BigDecimal avg_price;
	@Column(name = "Avg_price_usd")
	private BigDecimal avg_price_usd;
	@Column(name = "Deal_price")
	private BigDecimal deal_price;
	@Column(name = "Currency")
	private String currency;
	@Column(name = "Funding")
	private Integer funding;
	@Column(name = "Date_insert")
	private String date_insert;
	@Column(name = "Account")
	private String account;
	@Column(name = "Initiator")
	private String initiator;

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
	 * @return the security_id
	 */
	public Long getSecurity_id() {
		return security_id;
	}

	/**
	 * @param security_id
	 *            the security_id to set
	 */
	public void setSecurity_id(Long security_id) {
		this.security_id = security_id;
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
	 * @return the deals_realised_profit_id
	 */
	public Long getDeals_realised_profit_id() {
		return deals_realised_profit_id;
	}

	/**
	 * @param deals_realised_profit_id
	 *            the deals_realised_profit_id to set
	 */
	public void setDeals_realised_profit_id(Long deals_realised_profit_id) {
		this.deals_realised_profit_id = deals_realised_profit_id;
	}

	/**
	 * @return the seal_id
	 */
	public Long getDeal_id() {
		return deal_id;
	}

	/**
	 * @param deal_id
	 *            the deal_id to set
	 */
	public void setDeal_id(Long deal_id) {
		this.deal_id = deal_id;
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
	 * @return the trade_date
	 */
	public String getTrade_date() {
		return trade_date;
	}

	/**
	 * @param trade_date
	 *            the trade_date to set
	 */
	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
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
	 * @return the realised_profit
	 */
	public BigDecimal getRealised_profit() {
		return realised_profit;
	}

	/**
	 * @param realised_profit
	 *            the realised_profit to set
	 */
	public void setRealised_profit(BigDecimal realised_profit) {
		this.realised_profit = realised_profit;
	}

	/**
	 * @return the initial_quantity
	 */
	public Integer getInitial_quantity() {
		return initial_quantity;
	}

	/**
	 * @param initial_quantity
	 *            the initial_quantity to set
	 */
	public void setInitial_quantity(Integer initial_quantity) {
		this.initial_quantity = initial_quantity;
	}

	/**
	 * @return the deal_quantity
	 */
	public Integer getDeal_quantity() {
		return deal_quantity;
	}

	/**
	 * @param deal_quantity
	 *            the deal_quantity to set
	 */
	public void setDeal_quantity(Integer deal_quantity) {
		this.deal_quantity = deal_quantity;
	}

	/**
	 * @return the avg_price
	 */
	public BigDecimal getAvg_price() {
		return avg_price;
	}

	/**
	 * @param avg_price
	 *            the avg_price to set
	 */
	public void setAvg_price(BigDecimal avg_price) {
		this.avg_price = avg_price;
	}

	/**
	 * @return the avg_price_usd
	 */
	public BigDecimal getAvg_price_usd() {
		return avg_price_usd;
	}

	/**
	 * @param avg_price_usd
	 *            the avg_price_usd to set
	 */
	public void setAvg_price_usd(BigDecimal avg_price_usd) {
		this.avg_price_usd = avg_price_usd;
	}

	/**
	 * @return the deal_price
	 */
	public BigDecimal getDeal_price() {
		return deal_price;
	}

	/**
	 * @param deal_price
	 *            the deal_price to set
	 */
	public void setDeal_price(BigDecimal deal_price) {
		this.deal_price = deal_price;
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

	
	public String getInitiator() {
		return initiator;
	}

	
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
}
