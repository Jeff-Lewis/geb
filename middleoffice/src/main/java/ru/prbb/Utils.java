/**
 * 
 */
package ru.prbb;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author RBr
 * 
 */
public class Utils {

	private static final Locale LOCALE = new Locale("RU", "ru");

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
	 * @param object
	 * @return
	 */
	public static String toString(Object object) {
		if (null != object) {
			return object.toString();
		}
		return null;
	}

	/**
	 * @param object
	 * @return
	 */
	public static BigDecimal toDouble(Object object) {
		if (null != object) {
			return (BigDecimal) object;
		}
		return null;
	}

	/**
	 * @param object
	 * @return
	 */
	public static Timestamp toTimestamp(Object object) {
		if (null != object) {
			return (Timestamp) object;
		}
		return null;
	}

	/**
	 * @param object
	 * @return
	 */
	public static Long toLong(Object object) {
		if (null != object) {
			return ((BigDecimal) object).longValue();
		}
		return null;
	}

	/**
	 * Декодирует строку даты для SQL
	 * 
	 * @param date
	 *            строка даты в формате yyyy-MM-dd
	 * @return объект <code>java.sql.Date</code>
	 */
	public static Date parseDate(String date) {
		if (isNotEmpty(date)) {
			try {
				if (date.length() > 10) {
					date = date.substring(0, 10);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", LOCALE);
				long time = sdf.parse(date).getTime();
				return new Date(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}
		}
		return null;
	}

}
