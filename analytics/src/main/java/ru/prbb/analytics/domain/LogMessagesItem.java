/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author RBr
 * 
 */
public class LogMessagesItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long sl_id;
	private String name;
	private String value;
	private String text;
	private String status;
	private Date date_insert;

	/**
	 * @return the sl_id
	 */
	public Long getSl_id() {
		return sl_id;
	}

	/**
	 * @param sl_id
	 *            the sl_id to set
	 */
	public void setSl_id(Long sl_id) {
		this.sl_id = sl_id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the date_insert
	 */
	public Date getDate_insert() {
		return date_insert;
	}

	/**
	 * @param date_insert
	 *            the date_insert to set
	 */
	public void setDate_insert(Date date_insert) {
		this.date_insert = date_insert;
	}

}
