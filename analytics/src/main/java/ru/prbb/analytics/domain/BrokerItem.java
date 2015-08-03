/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import ru.prbb.Utils;

/**
 * @author RBr
 */
//@Entity
public class BrokerItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "full_name")
	private String full_name;
	@Column(name = "rating")
	private Number rating;
	@Column(name = "bloomberg_code")
	private String bloomberg_code;
	@Column(name = "cover_russian")
	private Number cover_russian;
	@Column(name = "short_name")
	private String short_name;

	public BrokerItem() {
	}

	public BrokerItem(Object[] arr) {
		int idx = 0;
		id = Utils.toLong(arr[idx++]);
		full_name = Utils.toString(arr[idx++]);
		rating = Utils.toInteger(arr[idx++]);
		bloomberg_code = Utils.toString(arr[idx++]);
		cover_russian = Utils.toInteger(arr[idx++]);
		short_name = Utils.toString(arr[idx++]);
	}

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
	 * @return the full_name
	 */
	public String getFull_name() {
		return full_name;
	}

	/**
	 * @param full_name
	 *            the full_name to set
	 */
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	/**
	 * @return the rating
	 */
	public Number getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(Number rating) {
		this.rating = rating;
	}

	/**
	 * @return the bloomberg_code
	 */
	public String getBloomberg_code() {
		return bloomberg_code;
	}

	/**
	 * @param bloomberg_code
	 *            the bloomberg_code to set
	 */
	public void setBloomberg_code(String bloomberg_code) {
		this.bloomberg_code = bloomberg_code;
	}

	/**
	 * @return the cover_russian
	 */
	public Number getCover_russian() {
		return cover_russian;
	}

	/**
	 * @param cover_russian
	 *            the cover_russian to set
	 */
	public void setCover_russian(Number cover_russian) {
		this.cover_russian = cover_russian;
	}

	/**
	 * @return the short_name
	 */
	public String getShort_name() {
		return short_name;
	}

	/**
	 * @param short_name
	 *            the short_name to set
	 */
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
}
