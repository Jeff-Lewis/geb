/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class ResultData extends Result implements Serializable {

	private static final long serialVersionUID = 1L;

	public ResultData(Object item) {
		super(Boolean.TRUE);
		put("item", item);
	}
}
