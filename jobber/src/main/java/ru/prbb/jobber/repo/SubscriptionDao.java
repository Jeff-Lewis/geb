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
	 */
	public int subsUpdate(String security_code, String last_price, String last_chng);

}
