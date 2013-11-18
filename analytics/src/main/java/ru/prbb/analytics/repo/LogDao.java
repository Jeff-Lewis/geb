/**
 * 
 */
package ru.prbb.analytics.repo;

import java.util.List;
import java.util.Map;

import ru.prbb.analytics.domain.LogContactItem;
import ru.prbb.analytics.domain.LogMessagesItem;

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
	List<LogContactItem> getLogContacts(String start, String stop);

	/**
	 * @param type
	 * @param start
	 * @param stop
	 * @return
	 */
	List<LogMessagesItem> getLogMessages(String type, String start, String stop);

	/**
	 * @return
	 */
	List<Map<String, Object>> getLogSubscription();

}
