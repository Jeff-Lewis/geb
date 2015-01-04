/**
 * 
 */
package ru.prbb.jobber.domain;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author RBr
 * 
 */
public class Result extends HashMap<String, Object> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Result SUCCESS = new Result(Boolean.TRUE);
	public static final Result FAIL = new Result(Boolean.FALSE);

	protected Result(Boolean success) {
		super(4, 1);
		put("success", success);
	}

}
