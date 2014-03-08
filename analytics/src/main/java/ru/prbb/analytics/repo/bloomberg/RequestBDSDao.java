/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;
import java.util.Map;

import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDS запрос
 * 
 * @author RBr
 * 
 */
public interface RequestBDSDao {

	/**
	 * @param securities
	 * @param answer
	 */
	void execute(String[] securities, Map<String, Object> answer);

	/**
	 * @return
	 */
	List<SimpleItem> findParams();

}
