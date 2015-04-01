/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.List;

import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SubscriptionItem;

/**
 * @author RBr
 * 
 */
public interface SubscriptionDao {

	/**
	 * Список подписок
	 */
	public List<SubscriptionItem> getSubscriptions();

	/**
	 * Список компаний в подписке
	 */
	public List<SecurityItem> getSubscriptionSecurities(Long id);

	/**
	 * Сохранить данные в БД
	 * 
	 * @param data
	 *            [ {security_code, last_price, last_chng} ]
	 */
	public int[] subsUpdate(List<String[]> data);

}
