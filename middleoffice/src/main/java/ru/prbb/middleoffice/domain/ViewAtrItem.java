/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author RBr
 * 
 */
public class ViewAtrItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id_sec;
	private String security_code;
	private String date_time;
	private Number ATR;
	private String atr_period;
	private String algorithm;
	private String ds_high_code;
	private String ds_low_code;
	private String ds_close_code;
	private String period_type;
	private String calendar;
	private String date_insert;

	/**
	 * @return the id_sec
	 */
	public Long getId_sec() {
		return id_sec;
	}

	/**
	 * @param id_sec
	 *            the id_sec to set
	 */
	public void setId_sec(Long id_sec) {
		this.id_sec = id_sec;
	}

	/**
	 * @return the security_code
	 */
	public String getSecurity_code() {
		return security_code;
	}

	/**
	 * @param security_code
	 *            the security_code to set
	 */
	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	/**
	 * @return the date_time
	 */
	public String getDate_time() {
		return date_time;
	}

	/**
	 * @param date_time
	 *            the date_time to set
	 */
	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	/**
	 * @return the aTR
	 */
	public Number getATR() {
		return ATR;
	}

	/**
	 * @param atr
	 *            the aTR to set
	 */
	public void setATR(Number atr) {
		ATR = atr;
	}

	/**
	 * @return the atr_period
	 */
	public String getAtr_period() {
		return atr_period;
	}

	/**
	 * @param atr_period
	 *            the atr_period to set
	 */
	public void setAtr_period(String atr_period) {
		this.atr_period = atr_period;
	}

	/**
	 * @return the algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm
	 *            the algorithm to set
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * @return the ds_high_code
	 */
	public String getDs_high_code() {
		return ds_high_code;
	}

	/**
	 * @param ds_high_code
	 *            the ds_high_code to set
	 */
	public void setDs_high_code(String ds_high_code) {
		this.ds_high_code = ds_high_code;
	}

	/**
	 * @return the ds_low_code
	 */
	public String getDs_low_code() {
		return ds_low_code;
	}

	/**
	 * @param ds_low_code
	 *            the ds_low_code to set
	 */
	public void setDs_low_code(String ds_low_code) {
		this.ds_low_code = ds_low_code;
	}

	/**
	 * @return the ds_close_code
	 */
	public String getDs_close_code() {
		return ds_close_code;
	}

	/**
	 * @param ds_close_code
	 *            the ds_close_code to set
	 */
	public void setDs_close_code(String ds_close_code) {
		this.ds_close_code = ds_close_code;
	}

	/**
	 * @return the period_type
	 */
	public String getPeriod_type() {
		return period_type;
	}

	/**
	 * @param period_type
	 *            the period_type to set
	 */
	public void setPeriod_type(String period_type) {
		this.period_type = period_type;
	}

	/**
	 * @return the calendar
	 */
	public String getCalendar() {
		return calendar;
	}

	/**
	 * @param calendar
	 *            the calendar to set
	 */
	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

	/**
	 * @return the date_insert
	 */
	public String getDate_insert() {
		return date_insert;
	}

	/**
	 * @param date_insert
	 *            the date_insert to set
	 */
	public void setDate_insert(String date_insert) {
		this.date_insert = date_insert;
	}
}
