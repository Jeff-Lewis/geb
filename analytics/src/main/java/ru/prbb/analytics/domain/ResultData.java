/**
 * 
 */
package ru.prbb.analytics.domain;

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
 * 
 */
public class ResultData implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Object item;

	public ResultData(Object item) {
		this.item = item;
	}

	public Object getItem() {
		return item;
	}

	public boolean isSuccess() {
		return true;
	}
}
