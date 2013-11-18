/**
 * 
 */
package ru.prbb.analytics.repo;

import java.util.List;

import ru.prbb.analytics.domain.EquityItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * @author RBr
 *
 */
public interface SecuritiesDao {

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> getFilter(String query);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> getSecurities(String query);

	/**
	 * @param filter
	 * @param security
	 * @return
	 */
	List<EquityItem> getSecurities(String filter, Long security, Integer fundamentals);

}
