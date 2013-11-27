/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.List;

import ru.prbb.middleoffice.domain.LogContactItem;
import ru.prbb.middleoffice.domain.LogMessagesItem;
import ru.prbb.middleoffice.domain.SubscriptionItem;

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
	List<SubscriptionItem> getLogSubscription();

}
