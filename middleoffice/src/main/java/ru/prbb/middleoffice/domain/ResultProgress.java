/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 */
public class ResultProgress extends Result implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String VALUE = "value";
	private static final String TEXT = "text";

	public ResultProgress(double value, String text) {
		super(Boolean.TRUE);
		put(VALUE, value);
		put(TEXT, text);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultProgress [");
		builder.append(get(VALUE));
		builder.append(", ");
		builder.append(get(TEXT));
		builder.append("]");
		return builder.toString();
	}
}
