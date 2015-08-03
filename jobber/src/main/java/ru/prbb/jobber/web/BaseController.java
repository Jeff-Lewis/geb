package ru.prbb.jobber.web;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.jobber.domain.Result;
import ru.prbb.jobber.domain.ResultError;

public abstract class BaseController {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected Object[] toArray(Object... args) {
		return args;
	}
	/**
	 * Проверить строку на содержимое
	 * 
	 * @param s
	 *            проверяемая строка
	 * @return <code>true</code>, если пустая или содержит только пробельные символы
	 */
	public static boolean isEmpty(String s) {
		if (null != s) {
			for (int i = 0; i < s.length(); i++) {
				if (!Character.isSpaceChar(s.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Проверить число на содержимое
	 * 
	 * @param n
	 *            проверяемое число
	 * @return <code>true</code>, если <code>null</code> или <code>0</code>
	 */
	protected boolean isEmpty(Number n) {
		if (null != n) {
			return n.doubleValue() == 0; // FIXME Проверка на 0
		}
		return true;
	}

	/**
	 * Проверить строку на содержимое
	 * 
	 * @param s
	 *            проверяемая строка
	 * @return <code>true</code>, если содержит символы
	 */
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	/**
	 * Декодирует строку даты для SQL
	 * 
	 * @param date
	 *            строка даты в формате yyyy-MM-ddThh:mm:ss
	 * @return null при некорректном входе
	 */
	protected Timestamp parseDateTime(String date) {
		if (isNotEmpty(date)) {
			try {
				return Timestamp.valueOf(date.replace('T', ' '));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				System.err.println(e);
			}
		}
		return null;
	}

	@ExceptionHandler
	@ResponseBody
	private Result handleException(Exception ex) {
		String message = ex.getMessage();

		if (ex instanceof PersistenceException) {
			try {
				Throwable cause = ex.getCause();
				SQLException sqlException = (SQLException) cause.getCause();
				message = sqlException.getMessage();
			} catch (Exception e) {
				log.error("catch PersistenceException", e);
			}
		}

		if (Utils.isEmpty(message)) {
			message = ex.toString();
		}

		return new ResultError(message);
	}

}
