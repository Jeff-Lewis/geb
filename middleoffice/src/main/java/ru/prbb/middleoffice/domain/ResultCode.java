/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 */
public class ResultCode extends Result implements Serializable {

	private static final long serialVersionUID = 1L;

	public ResultCode(Boolean success, String code) {
		super(success);
		put("code", code);
	}

}
