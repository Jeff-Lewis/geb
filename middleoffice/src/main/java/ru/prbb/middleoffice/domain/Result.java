/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Result SUCCESS = new Result(true);
	public static final Result FAIL = new Result(false);

	private final boolean success;

	private Result(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}
}
