/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.List;

import ru.prbb.jobber.domain.SendMessageItem;
import ru.prbb.jobber.domain.SendingItem;
import ru.prbb.jobber.domain.SimpleItem;

/**
 * Рассылка E-mail и SMS
 * 
 * @author RBr
 */
public interface SendingDao {

	/**
	 * @param group
	 * @return
	 */
	List<String> getMailByGroup(String group);

	/**
	 * @param group
	 * @return
	 */
	List<String> getPhoneByGroup(String group);

	List<SendingItem> send(List<SendMessageItem> items);

	List<SendingItem> sendMail(String text, List<String> emails, String subject);

	/**
	 * @param text
	 * @param phones
	 * @param type
	 *            1 – автоматическая, 2 – ручная отправка
	 * @return
	 */
	List<SendingItem> sendSms(String text, List<String> phones, Number type);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboPhone(String query);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboMail(String query);

	/**
	 * @return
	 */
	String getAnalitic();

	/**
	 * @return
	 */
	String getTrader();

}
