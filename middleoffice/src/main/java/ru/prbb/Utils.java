/**
 * 
 */
package ru.prbb;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * @author RBr
 * 
 */
public class Utils {
	
	private static Logger log = LoggerFactory.getLogger(Utils.class);

	public static final Locale LOCALE = new Locale("RU", "ru");

	public static boolean isDebug() {
		String s = System.getenv("JAVA_DEBUG");
		return s != null ? Boolean.parseBoolean(s) : false;
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
	public static boolean isEmpty(Number n) {
		if (null != n) {
			return n.doubleValue() != 0; // FIXME Проверка на 0
		}
		return true;
	}

	/**
	 * Проверить число на содержимое
	 * 
	 * @param n
	 *            проверяемое число
	 * @return <code>true</code>, если не равно <code>0</code>
	 */
	public static boolean isNotEmpty(Number n) {
		if (null == n) {
			return false;
		}
		return (n.doubleValue() != 0);
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
	 * Подготовить число для записи в БД<br>
	 * <code>
	 * <ul>
	 * <li>null -> null
	 * <li>"" -> null
	 * <li>" " -> null
	 * <li>"n" -> n
	 * <li>"error" -> null
	 * </ul>
	 * </code>
	 * 
	 * @param s
	 * @return null для пустой строки
	 */
	public static Number parseNumber(String s) {
		if (isNotEmpty(s)) {
			try {
				return new Float(s);
			} catch (NumberFormatException e) {
				log.error("parseDouble:" + s, e);
			}
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
				return Date.valueOf(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.err);
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
	 * Приведение к типу Number
	 * 
	 * @param object
	 *            объект из БД
	 * @return Number
	 */
	public static Number toNumber(Object object) {
		if (object instanceof Number) {
			return (Number) object;
		}
		if (object != null) {
			try {
				String str = object.toString();
				return str.isEmpty() ? null : new BigDecimal(str);
			} catch (NumberFormatException e) {
				log.error("toNumber:'{}'", object, e);
			}
		}
		return null;
	}

	/**
	 * Приведение к типу Double
	 * 
	 * @param object
	 *            объект из БД
	 * @return Number
	 */
	public static Number toDouble(Object object) {
		return toNumber(object);
	}

	/**
	 * Приведение к типу Long
	 * 
	 * @param object
	 *            объект из БД
	 * @return Long
	 */
	public static Long toLong(Object object) {
		Number number = toNumber(object);
		if (null != number) {
			return number.longValue();
		}
		return null;
	}

	/**
	 * Приведение к типу Integer
	 * 
	 * @param object
	 *            объект из БД
	 * @return Number
	 */
	public static Number toInteger(Object object) {
		return toNumber(object);
	}

	/**
	 * Приведение к типу Byte
	 * 
	 * @param object
	 *            объект из БД
	 * @return Number
	 */
	public static Number toByte(Object object) {
		return toNumber(object);
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
	public static String toDatetime(Object object) {
		if (null != object) {
			String s = object.toString();
			return s;
		}
		return null;
	}

	/**
	 * @param object
	 * @return
	 */
	public static String toDate(Object object) {
		if (null != object) {
			String s = object.toString();
			if (s.length() > 10)
				s = s.substring(0, 10);
			return s;
		}
		return null;
	}

	/**
	 * @param object
	 * @return
	 */
	public static Date toSqlDate(Object object) {
		if (null != object) {
			try {
				return new Date(((java.util.Date) object).getTime());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(System.err);
			}
		}
		return null;
	}

	/**
	 * @param object
	 * @return
	 */
	public static String toTime(Object object) {
		if (null != object) {
			String s = object.toString();
			return s;
		}
		return null;
	}

	/**
	 * Получить имя колонки БД по имени поля Жава
	 * 
	 * @param field
	 *            имя поля
	 * @param _class
	 *            класс отображения
	 * @return имя поля
	 */
	public static String getColumnNameByField(String field, Class<?> _class) {
		try {
			Field f = _class.getDeclaredField(field);
			Column column = f.getAnnotation(Column.class);
			return column.name();
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
		}
		return field;
	}

	/**
	 * Из списка строк сделать список {id, name}, id c 1
	 * 
	 * @param list
	 *            name
	 * @return [ {id, name} ]
	 */
	public static List<SimpleItem> toSimpleItem(List<?> list) {
		List<SimpleItem> res = new ArrayList<>(list.size());
		long id = 0;
		for (Object object : list) {
			SimpleItem item = new SimpleItem();
			item.setId(++id);
			item.setName(Utils.toString(object));
			res.add(item);
		}
		return res;
	}

	public static Object[] toArray(Object... args) {
		return args;
	}

}
