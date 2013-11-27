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
public class NoConformityItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String TradeNum;
	private String Date;
	private String SecShortName;
	private String Operation;

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
	 * @return the tradeNum
	 */
	public String getTradeNum() {
		return TradeNum;
	}

	/**
	 * @param tradeNum
	 *            the tradeNum to set
	 */
	public void setTradeNum(String tradeNum) {
		TradeNum = tradeNum;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return Date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		Date = date;
	}

	/**
	 * @return the secShortName
	 */
	public String getSecShortName() {
		return SecShortName;
	}

	/**
	 * @param secShortName
	 *            the secShortName to set
	 */
	public void setSecShortName(String secShortName) {
		SecShortName = secShortName;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return Operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(String operation) {
		Operation = operation;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NoConformityItem) {
			return id.equals(((NoConformityItem) obj).id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return TradeNum + '(' + id + ')';
	}
}
