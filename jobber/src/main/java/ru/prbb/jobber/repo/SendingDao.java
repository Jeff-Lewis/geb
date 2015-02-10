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

	SendingItem sendMail(String text, String email, String subject);

	SendingItem sendSms(String text, String phone);

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
