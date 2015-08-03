package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;

import ru.prbb.Utils;

public class NoCoefficientsItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;
	@Column(name = "id_sec")
	private Long securityId;
	@Column(name = "sys_id")
	private Long tradeSystemId;
	@Column(name = "security_code")
	private String securityCode;
	@Column(name = "TradeSystem")
	private String tradeSystem;

	
	public NoCoefficientsItem() {
	}

	public NoCoefficientsItem(Object[] arr) {
		setSecurityId(Utils.toLong(arr[0]));
		setTradeSystemId(Utils.toLong(arr[1]));
		setSecurityCode(Utils.toString(arr[2]));
		setTradeSystem(Utils.toString(arr[3]));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSecurityId() {
		return securityId;
	}

	public void setSecurityId(Long securityId) {
		this.securityId = securityId;
	}

	public Long getTradeSystemId() {
		return tradeSystemId;
	}

	public void setTradeSystemId(Long tradeSystemId) {
		this.tradeSystemId = tradeSystemId;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getTradeSystem() {
		return tradeSystem;
	}

	public void setTradeSystem(String tradeSystem) {
		this.tradeSystem = tradeSystem;
	}
}
