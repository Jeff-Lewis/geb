package ru.prbb;
/**
 * 
 */


import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author RBr
 * 
 */
public class Utils {

	public static final Locale LOCALE = new Locale("RU", "ru");

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
	public static boolean isEmpty(Number n) {
		if (null != n) {
			return n.doubleValue() != 0; // FIXME Проверка на 0
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
	 * Подготовить строку для записи в БД<br>
	 * <code>
	 * null -> null<br>
	 * "" -> null<br>
	 * " " -> null<br>
	 * "s" -> "s"
	 * </code>
	 * 
	 * @param s
	 * @return null для пустой строки
	 */
	public static String parseString(String s) {
		if (isNotEmpty(s)) {
			return s;
		}
		return null;
	}

	/**
	 * Подготовить число для записи в БД<br>
	 * <code>
	 * null -> null<br>
	 * 0 -> null<br>
	 * x -> x<br>
	 * </code>
	 * 
	 * @param n
	 * @return null для пустого числа
	 */
	public static Long parseLong(Long n) {
		if ((null != n) && (0 != n)) {
			return n;
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

	/**
	 * Приведение к типу String
	 * 
	 * @param object
	 *            объект из БД
	 * @return String
	 */
	public static String toString(Object object) {
		if (null != object) {
			return object.toString();
		}
		return null;
	}

	/**
	 * Приведение к типу BigDecimal
	 * 
	 * @param object
	 *            объект из БД
	 * @return BigDecimal
	 */
	public static BigDecimal toDouble(Object object) {
		if (null != object) {
			return (BigDecimal) object;
		}
		return null;
	}

	/**
	 * Приведение к типу Long
	 * 
	 * @param object
	 *            объект из БД
	 * @return Long
	 */
	public static Long toLong(Object object) {
		if (null != object) {
			return ((BigDecimal) object).longValue();
		}
		return null;
	}

	/**
	 * Приведение к типу Integer
	 * 
	 * @param object
	 *            объект из БД
	 * @return Integer
	 */
	public static Integer toInteger(Object object) {
		if (null != object) {
			return (Integer) object;
		}
		return null;
	}

	/**
	 * Приведение к типу Byte
	 * 
	 * @param object
	 *            объект из БД
	 * @return Byte
	 */
	public static Byte toByte(Object object) {
		if (null != object) {
			return (Byte) object;
		}
		return null;
	}

	/**
	 * @param object
	 * @return
	 */
	public static String toTimestamp(Object object) {
		if (null != object) {
			return object.toString();
		}
		return null;
	}

	/**
	 * @param object
	 * @return
	 */
	public static String toDate(Object object) {
		if (null != object) {
			return object.toString();
		}
		return null;
	}

	/**
	 * @param object
	 * @return
	 */
	public static String toTime(Object object) {
		if (null != object) {
			return object.toString();
		}
		return null;
	}

	/***************************************************************************************/

	/**
	 * Форматирует дату
	 * 
	 * @param date
	 * @return yyyyMMdd
	 */
	public static String toStringYMD2(java.util.Date date) {
		return (date == null) ? "" : new SimpleDateFormat("yyyyMMdd", LOCALE).format(date);
	}

	public static SimpleDateFormat createDateFormatYMD() {
		return new SimpleDateFormat("yyyy-MM-dd", LOCALE);
	}
}
