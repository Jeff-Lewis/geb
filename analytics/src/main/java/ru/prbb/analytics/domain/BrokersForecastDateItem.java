/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author RBr
 * 
 */
@Entity
public class BrokersForecastDateItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "value")
	private Timestamp date;
	@Column(name = "display")
	private String value;

	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
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

	@Transient
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	public String getDisplay() {
		return sdf.format(date.getTime());
	}
}
