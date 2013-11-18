/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;
import java.util.Set;

import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDH запрос с EPS
 * 
 * @author RBr
 * 
 */
public interface RequestBDHepsDao {

	/**
	 * @param dateStart
	 * @param dateEnd
	 * @param period
	 * @param calendar
	 * @param security
	 * @param params
	 * @param _currency
	 */
	void execute(String dateStart, String dateEnd, String period, String calendar,
			String[] security, String[] params, Set<String> _currency);

	/**
	 * @return
	 */
	List<SimpleItem> findParams();

}
