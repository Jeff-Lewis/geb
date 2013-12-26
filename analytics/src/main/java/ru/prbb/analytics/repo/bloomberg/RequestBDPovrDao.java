/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;
import java.util.Set;

import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDP с override
 * 
 * @author RBr
 * 
 */
public interface RequestBDPovrDao {

	/**
	 * @param security
	 * @param params
	 * @param over
	 * @param period
	 */
	void execute(String[] security, String[] params, String over, String period);

	/**
	 * @param security
	 * @param params
	 * @param over
	 * @param _currency
	 */
	void execute(String[] security, String[] params, String over, Set<String> _currency);

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
