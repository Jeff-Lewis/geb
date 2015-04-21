/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;
import java.util.Map;

import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDP с override
 * 
 * @author RBr
 * 
 */
public interface RequestBDPovrDao {

	/**
	 * @param securities
	 * @param currencies 
	 * @param answer
	 */
	void execute(String[] securities, String[] currencies, String over, Map<String, Map<String, Map<String, String>>> answer);

	/**
	 * @param query 
	 * @return
	 */
	List<SimpleItem> findParams(String query);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> comboFilterOverride(String query);

}
