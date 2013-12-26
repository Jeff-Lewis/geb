/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class SecurityRiscsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String client;
	private String fund;
	private String security_code;
	private String batch;
	private String risk_ath;
	private String risk_avg;
	private String stop_loss;
	private String date_begin;
	private String date_end;
	private String comment;

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
	public String getBatch() {
		return batch;
	}

	/**
	 * @param batch
	 *            the batch to set
	 */
	public void setBatch(String batch) {
		this.batch = batch;
	}

	/**
	 * @return the risk_ath
	 */
	public String getRisk_ath() {
		return risk_ath;
	}

	/**
	 * @param risk_ath
	 *            the risk_ath to set
	 */
	public void setRisk_ath(String risk_ath) {
		this.risk_ath = risk_ath;
	}

	/**
	 * @return the risk_avg
	 */
	public String getRisk_avg() {
		return risk_avg;
	}

	/**
	 * @param risk_avg
	 *            the risk_avg to set
	 */
	public void setRisk_avg(String risk_avg) {
		this.risk_avg = risk_avg;
	}

	/**
	 * @return the stop_loss
	 */
	public String getStop_loss() {
		return stop_loss;
	}

	/**
	 * @param stop_loss
	 *            the stop_loss to set
	 */
	public void setStop_loss(String stop_loss) {
		this.stop_loss = stop_loss;
	}

	/**
	 * @return the date_begin
	 */
	public String getDate_begin() {
		return date_begin;
	}

	/**
	 * @param date_begin
	 *            the date_begin to set
	 */
	public void setDate_begin(String date_begin) {
		this.date_begin = date_begin;
	}

	/**
	 * @return the date_end
	 */
	public String getDate_end() {
		return date_end;
	}

	/**
	 * @param date_end
	 *            the date_end to set
	 */
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
