/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class ResultProgress implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final ResultProgress FAIL = new ResultProgress();

	private final boolean success;
	private final Double value;
	private final String text;

	public ResultProgress() {
		this.success = false;
		this.value = null;
		this.text = null;
	}

	public ResultProgress(double value, String text) {
		this.success = true;
		this.value = value;
		this.text = text;
	}

	public boolean isSuccess() {
		return success;
	}

	public Double getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
}
