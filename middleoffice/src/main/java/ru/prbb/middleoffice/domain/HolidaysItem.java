/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class HolidaysItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String country;
	private String date;
	private String name;
	private String time_start;
	private String time_stop;
	private Number sms;
	private Number portfolio;

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
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
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
	 * @return the time_start
	 */
	public String getTime_start() {
		return time_start;
	}

	/**
	 * @param time_start
	 *            the time_start to set
	 */
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	/**
	 * @return the time_stop
	 */
	public String getTime_stop() {
		return time_stop;
	}

	/**
	 * @param time_stop
	 *            the time_stop to set
	 */
	public void setTime_stop(String time_stop) {
		this.time_stop = time_stop;
	}

	/**
	 * @return the sms
	 */
	public Number getSms() {
		return sms;
	}

	/**
	 * @param sms
	 *            the sms to set
	 */
	public void setSms(Number sms) {
		this.sms = sms;
	}

	/**
	 * @return the portfolio
	 */
	public Number getPortfolio() {
		return portfolio;
	}

	/**
	 * @param portfolio
	 *            the portfolio to set
	 */
	public void setPortfolio(Number portfolio) {
		this.portfolio = portfolio;
	}
}
