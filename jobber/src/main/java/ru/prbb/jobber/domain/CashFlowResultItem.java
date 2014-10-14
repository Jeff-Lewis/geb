/**
 * 
 */
package ru.prbb.jobber.domain;

import java.io.Serializable;

/**
 * @author RBr
 */
public class CashFlowResultItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String security;
	private String params;
	private String date;
	private Double value;
	private Double value2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getValue2() {
		return value2;
	}

	public void setValue2(Double value2) {
		this.value2 = value2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CashFlowResultItem [id=");
		builder.append(id);
		builder.append(", security=");
		builder.append(security);
		builder.append(", params=");
		builder.append(params);
		builder.append(", date=");
		builder.append(date);
		builder.append(", value=");
		builder.append(value);
		builder.append(", value2=");
		builder.append(value2);
		builder.append("]");
		return builder.toString();
	}

}
