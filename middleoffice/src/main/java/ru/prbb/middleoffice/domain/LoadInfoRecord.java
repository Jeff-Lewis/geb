/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 *
 */
public class LoadInfoRecord implements Serializable, Comparable<LoadInfoRecord> {

	private static final long serialVersionUID = 1L;

	private final String row;
	private final String error;

	public LoadInfoRecord(String row, String error) {
		this.row = row;
		this.error = error;
	}

	public String getRow() {
		return row;
	}

	public String getError() {
		return error;
	}

	@Override
	public int compareTo(LoadInfoRecord o) {
		return new Long(row).compareTo(new Long(o.row));
	}
}
