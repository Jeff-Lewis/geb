/**
 * 
 */
package ru.prbb;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author RBr
 * 
 */
public class Utils {

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

}
