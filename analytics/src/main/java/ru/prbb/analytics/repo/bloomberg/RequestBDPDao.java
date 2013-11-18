/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;

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
	 * @param params
	 */
	void execute(String[] security, String[] params);

	/**
	 * @return
	 */
	List<SimpleItem> findParams();

}
