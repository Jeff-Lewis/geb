/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class CountryTaxItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@Column(name="security_type")
	private String securityType;
	private String name;
	private String alpha3;
	@Column(name="recipient_name")
	private String recipientName;
	@Column(name="recipient_code")
	private String recipientCode;
	private String broker;
	private String value;
	@Column(name="date_begin")
	private String dateBegin;
	@Column(name="date_end")
	private String dateEnd;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSecurityType() {
		return securityType;
	}
	
	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlpha3() {
		return alpha3;
	}
	
	public void setAlpha3(String alpha3) {
		this.alpha3 = alpha3;
	}
	
	public String getRecipientName() {
		return recipientName;
	}
	
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	
	public String getRecipientCode() {
		return recipientCode;
	}
	
	public void setRecipientCode(String recipientCode) {
		this.recipientCode = recipientCode;
	}
	
	public String getBroker() {
		return broker;
	}
	
	public void setBroker(String broker) {
		this.broker = broker;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDateBegin() {
		return dateBegin;
	}
	
	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}
	
	public String getDateEnd() {
		return dateEnd;
	}
	
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
}
