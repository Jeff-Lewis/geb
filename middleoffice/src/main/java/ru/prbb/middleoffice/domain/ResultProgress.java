/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 */
public class ResultProgress implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean success;
	private Double value;
	private String text;

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

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultProgress [value=");
		builder.append(value);
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}
}
