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
	 * Список подписок и их статус
	 * 
	 * @return
	 */
	public List<SubscriptionItem> getSubscriptions();

	/**
	 * Список компаний в подписке
	 * 
	 * @param id
	 * @return
	 */
	public List<SecurityItem> subsGetSecs(Long id);

	/**
	 * 
	 * @param security_code
	 * @param last_price
	 * @param last_chng
	 * @return
	 */
	public int subsUpdate(String security_code, String last_price, String last_chng);

}
