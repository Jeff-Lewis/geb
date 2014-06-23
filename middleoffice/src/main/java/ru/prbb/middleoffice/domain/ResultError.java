/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 */
public class ResultError extends Result implements Serializable {

	private static final long serialVersionUID = 1L;

	public ResultError(String error) {
		super(Boolean.FALSE);
		put("error", error);
	}

}
