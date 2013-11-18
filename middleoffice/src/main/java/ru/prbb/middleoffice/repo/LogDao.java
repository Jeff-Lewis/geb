/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.List;
import java.util.Map;

/**
 * Журнал отправки сообщений<br>
 * Журнал изменений справочника контактов<br>
 * Журнал подписки
 * 
 * @author RBr
 * 
 */
public interface LogDao {

	/**
	 * @param start
	 * @param stop
	 * @return
	 */
	List<Map<String, Object>> getLogContacts(String start, String stop);

	/**
	 * @param type
	 * @param start
	 * @param stop
	 * @return
	 */
	List<Map<String, Object>> getLogMessages(String type, String start, String stop);

	/**
	 * @return
	 */
	List<Map<String, Object>> getLogSubscription();

}
