/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.List;

import ru.prbb.jobber.domain.LogContactItem;
import ru.prbb.jobber.domain.LogMessagesItem;
import ru.prbb.jobber.domain.LogSubscriptionItem;

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
	List<LogSubscriptionItem> getLogSubscription();

}
