/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;
import java.util.Map;

import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDH запрос
 * 
 * @author RBr
 * 
 */
public interface RequestBDHDao {

	/**
	 * @param securities
	 * @param currencies 
	 * @param answer
	 */
	void execute(String[] securities, String[] currencies, Map<String, Map<String, Map<String, String>>> answer);

	/**
	 * @param query 
	 * @return
	 */
	List<SimpleItem> findParams(String query);

}
