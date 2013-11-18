/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;

import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDS запрос
 * 
 * @author RBr
 * 
 */
public interface RequestBDSDao {

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
