/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.util.List;
import java.util.Map;

import ru.prbb.analytics.domain.CompaniesExceptionItem;
import ru.prbb.analytics.domain.CompaniesFileItem;
import ru.prbb.analytics.domain.CompaniesItem;
import ru.prbb.analytics.domain.CompaniesListItem;
import ru.prbb.analytics.domain.CompaniesQuarterItem;
import ru.prbb.analytics.domain.CompaniesYearItem;
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
	List<CompaniesListItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	CompaniesItem findById(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<CompaniesQuarterItem> findQuarters(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<CompaniesYearItem> findYears(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<CompaniesFileItem> findFiles(Long id);

	/**
	 * @param id
	 * @param params
	 */
	void updateById(Long id, Map<String, String> params);

	/**
	 * @param id
	 * @return
	 */
	List<CompaniesExceptionItem> findVarException(Long id);

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
