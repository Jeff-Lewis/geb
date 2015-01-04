/**
 * 
 */
package ru.prbb.jobber.domain;

import java.io.Serializable;

/**
 * Вернуть данные для обновления в браузере<br>
 * <br>
 * <code>
 * success - true<br>
 * item - Object
 * </code>
 * 
 * @author RBr
 */
public class ResultData extends Result implements Serializable {

	private static final long serialVersionUID = 1L;

	public ResultData(Object item) {
		super(Boolean.TRUE);
		put("item", item);
	}
}
