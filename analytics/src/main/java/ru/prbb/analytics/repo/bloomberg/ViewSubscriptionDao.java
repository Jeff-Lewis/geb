/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;

import ru.prbb.analytics.domain.ViewSubscriptionItem;

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
	 */
	void put(String name, String comment);

	/**
	 * @param id
	 */
	void delete(Long id);

	/**
	 * @param id
	 */
	void start(Long id);

	/**
	 * @param id
	 */
	void stop(Long id);

}
