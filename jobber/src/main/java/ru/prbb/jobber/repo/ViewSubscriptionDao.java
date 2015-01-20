/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.List;

import ru.prbb.jobber.domain.SecuritySubscrItem;
import ru.prbb.jobber.domain.ViewSubscriptionItem;

/**
 * Subscription
 * 
 * @author RBr
 * 
 */
public interface ViewSubscriptionDao {

	/**
	 * @return
	 */
	List<ViewSubscriptionItem> findAll();

	/**
	 * @param name
	 * @param comment
	 * @return
	 */
	int put(String name, String comment);

	/**
	 * 
	 * @param id
	 * @return
	 */
	ViewSubscriptionItem findById(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(Long id);

	/**
	 * @param id
	 * @return
	 */
	int start(Long id);

	/**
	 * @param id
	 * @return
	 */
	int stop(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<SecuritySubscrItem> findAllSecurities();

	/**
	 * @param id
	 * @return
	 */
	List<SecuritySubscrItem> findAllSecurities(Long id);

	/**
	 * 
	 * @param id
	 * @param ids
	 * @return
	 */
	int[] staffAdd(Long id, Long[] ids);

	/**
	 * 
	 * @param id
	 * @param ids
	 * @return
	 */
	int[] staffDel(Long id, Long[] ids);

}
