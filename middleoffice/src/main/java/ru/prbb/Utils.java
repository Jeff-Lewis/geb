/**
 * 
 */
package ru.prbb;

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

}
