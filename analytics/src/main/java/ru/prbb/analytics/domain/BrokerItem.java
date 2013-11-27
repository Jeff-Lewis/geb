/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class BrokerItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String full_name;
	private Integer rating;
	private String bloomberg_code;
	private Integer cover_russian;
	private String short_name;

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
	public Integer getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(Integer rating) {
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
	public Integer getCover_russian() {
		return cover_russian;
	}

	/**
	 * @param cover_russian
	 *            the cover_russian to set
	 */
	public void setCover_russian(Integer cover_russian) {
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
