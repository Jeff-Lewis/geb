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
	 * @param answer
	 */
	void execute(String[] securities, String over, Map<String, Map<String, Map<String, String>>> answer);

	/**
	 * @return
	 */
	List<SimpleItem> findParams();

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> comboFilterOverride(String query);

}
