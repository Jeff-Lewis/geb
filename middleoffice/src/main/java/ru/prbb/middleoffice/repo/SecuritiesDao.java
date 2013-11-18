/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.List;

import ru.prbb.middleoffice.domain.SecurityItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Список инструментов и фильтр для списка
 * 
 * @author RBr
 * 
 */
public interface SecuritiesDao {

	/**
	 * @param filter
	 * @param security
	 * @return
	 */
	List<SecurityItem> findAll(String filter, Long security);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findCombo(String query);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboFilter(String query);

}
