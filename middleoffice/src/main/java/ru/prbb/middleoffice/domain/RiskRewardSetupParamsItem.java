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
public class RiskRewardSetupParamsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String security_code;
	private Double slip;
	private Double risk_theor;
	private Double risk_pract;
	private Double discount;
	private String date_begin;
	private String date_end;
	private String date_insert;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSecurity_code() {
		return security_code;
	}

	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	public Double getSlip() {
		return slip;
	}

	public void setSlip(Double slip) {
		this.slip = slip;
	}

	public Double getRisk_theor() {
		return risk_theor;
	}

	public void setRisk_theor(Double risk_theor) {
		this.risk_theor = risk_theor;
	}

	public Double getRisk_pract() {
		return risk_pract;
	}

	public void setRisk_pract(Double risk_pract) {
		this.risk_pract = risk_pract;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getDate_begin() {
		return date_begin;
	}

	public void setDate_begin(String date_begin) {
		this.date_begin = date_begin;
	}

	public String getDate_end() {
		return date_end;
	}

	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}

	public String getDate_insert() {
		return date_insert;
	}

	public void setDate_insert(String date_insert) {
		this.date_insert = date_insert;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RiskRewardSetupParamsItem) {
			return id.equals(((RiskRewardSetupParamsItem) obj).id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return security_code + '(' + id + ')';
	}
}
