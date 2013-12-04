/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class HolidaysWeekItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String country;
	private Integer day_week;
	private String start;
	private String stop;

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the day_week
	 */
	public Integer getDay_week() {
		return day_week;
	}

	/**
	 * @param day_week
	 *            the day_week to set
	 */
	public void setDay_week(Integer day_week) {
		this.day_week = day_week;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the stop
	 */
	public String getStop() {
		return stop;
	}

	/**
	 * @param stop
	 *            the stop to set
	 */
	public void setStop(String stop) {
		this.stop = stop;
	}
}
