/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.util.List;

import ru.prbb.analytics.domain.CompanyItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * Список компаний
 * 
 * @author RBr
 * 
 */
public interface CompaniesDao {

	/**
	 * @return
	 */
	List<CompanyItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	CompanyItem findById(Long id);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboCurrencies(String query);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboGroupSvod(String query);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboPeriod(String query);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboEps(String query);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboVariables(String query);

}
