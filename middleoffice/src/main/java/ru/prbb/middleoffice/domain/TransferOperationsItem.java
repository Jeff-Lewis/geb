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
public class TransferOperationsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String Client;
	private String Accout;
	private String Security;
	private String Currency;
	private Integer TransferQuantity;
	private BigDecimal TransferPrice;
	private String TransferDate;
	private String SourceFund;
	private Integer SourceBatch;
	private Integer SourceQuantity;
	private String SourceOperation;
	private String DestinationFund;
	private Integer DestinationBatch;
	private String DestinationOperation;
	private String Comment;
	private Byte Funding;

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
		return Client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(String client) {
		Client = client;
	}

	/**
	 * @return the accout
	 */
	public String getAccout() {
		return Accout;
	}

	/**
	 * @param accout
	 *            the accout to set
	 */
	public void setAccout(String accout) {
		Accout = accout;
	}

	/**
	 * @return the security
	 */
	public String getSecurity() {
		return Security;
	}

	/**
	 * @param security
	 *            the security to set
	 */
	public void setSecurity(String security) {
		Security = security;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return Currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		Currency = currency;
	}

	/**
	 * @return the transferQuantity
	 */
	public Integer getTransferQuantity() {
		return TransferQuantity;
	}

	/**
	 * @param transferQuantity
	 *            the transferQuantity to set
	 */
	public void setTransferQuantity(Integer transferQuantity) {
		TransferQuantity = transferQuantity;
	}

	/**
	 * @return the transferPrice
	 */
	public BigDecimal getTransferPrice() {
		return TransferPrice;
	}

	/**
	 * @param transferPrice
	 *            the transferPrice to set
	 */
	public void setTransferPrice(BigDecimal transferPrice) {
		TransferPrice = transferPrice;
	}

	/**
	 * @return the transferDate
	 */
	public String getTransferDate() {
		return TransferDate;
	}

	/**
	 * @param transferDate
	 *            the transferDate to set
	 */
	public void setTransferDate(String transferDate) {
		TransferDate = transferDate;
	}

	/**
	 * @return the sourceFund
	 */
	public String getSourceFund() {
		return SourceFund;
	}

	/**
	 * @param sourceFund
	 *            the sourceFund to set
	 */
	public void setSourceFund(String sourceFund) {
		SourceFund = sourceFund;
	}

	/**
	 * @return the sourceBatch
	 */
	public Integer getSourceBatch() {
		return SourceBatch;
	}

	/**
	 * @param sourceBatch
	 *            the sourceBatch to set
	 */
	public void setSourceBatch(Integer sourceBatch) {
		SourceBatch = sourceBatch;
	}

	/**
	 * @return the sourceQuantity
	 */
	public Integer getSourceQuantity() {
		return SourceQuantity;
	}

	/**
	 * @param sourceQuantity
	 *            the sourceQuantity to set
	 */
	public void setSourceQuantity(Integer sourceQuantity) {
		SourceQuantity = sourceQuantity;
	}

	/**
	 * @return the sourceOperation
	 */
	public String getSourceOperation() {
		return SourceOperation;
	}

	/**
	 * @param sourceOperation
	 *            the sourceOperation to set
	 */
	public void setSourceOperation(String sourceOperation) {
		SourceOperation = sourceOperation;
	}

	/**
	 * @return the destinationFund
	 */
	public String getDestinationFund() {
		return DestinationFund;
	}

	/**
	 * @param destinationFund
	 *            the destinationFund to set
	 */
	public void setDestinationFund(String destinationFund) {
		DestinationFund = destinationFund;
	}

	/**
	 * @return the destinationBatch
	 */
	public Integer getDestinationBatch() {
		return DestinationBatch;
	}

	/**
	 * @param destinationBatch
	 *            the destinationBatch to set
	 */
	public void setDestinationBatch(Integer destinationBatch) {
		DestinationBatch = destinationBatch;
	}

	/**
	 * @return the destinationOperation
	 */
	public String getDestinationOperation() {
		return DestinationOperation;
	}

	/**
	 * @param destinationOperation
	 *            the destinationOperation to set
	 */
	public void setDestinationOperation(String destinationOperation) {
		DestinationOperation = destinationOperation;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return Comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		Comment = comment;
	}

	/**
	 * @return the funding
	 */
	public Byte getFunding() {
		return Funding;
	}

	/**
	 * @param funding
	 *            the funding to set
	 */
	public void setFunding(Byte funding) {
		Funding = funding;
	}
}
