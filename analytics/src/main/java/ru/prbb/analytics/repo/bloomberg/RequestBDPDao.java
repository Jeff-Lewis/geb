/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;
import java.util.Map;

import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDP запрос
 * 
 * @author RBr
 * 
 */
public interface RequestBDPDao {

	/**
	 * @param security
	 * @param answer
	 */
	void execute(String[] security, Map<String, Map<String, String>> answer);

	/**
	 * @param query 
	 * @return
	 */
	List<SimpleItem> findParams(String query);

}
