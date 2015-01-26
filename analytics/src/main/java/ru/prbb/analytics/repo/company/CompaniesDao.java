/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.sql.Date;
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

	int delQuarters(Long id, String code, String period, Date date, String currency);

	/**
	 * @param id
	 * @return
	 */
	List<CompaniesYearItem> findYears(Long id);

	int delYears(Long id, String code, String period, Date date, String currency);

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

	/**
	 * @param id
	 * @param id_doc
	 * @return
	 */
	CompaniesFileItem fileGetById(Long id, Long id_doc);

	/**
	 * @param id
	 * @param id_doc
	 * @return
	 */
	byte[] fileGetContentById(Long id, Long id_doc);

	/**
	 * @param id
	 * @param id_doc
	 * @return
	 */
	int fileDeleteById(Long id, Long id_doc);

	/**
	 * @param id
	 * @return
	 */
	List<SimpleItem> getEquityVars(Long id);

	/**
	 * @param id
	 * @param type
	 * @param baseYear
	 * @param calcYear
	 * @return
	 */
	int addEps(Long id, String type, Integer baseYear, Integer calcYear);

	/**
	 * @param id
	 * @param type
	 * @return
	 */
	int delEps(Long id, String type);

	/**
	 * @param id
	 * @param type
	 * @param baseYear
	 * @param calcYear
	 * @return
	 */
	int addBookVal(Long id, String type, Integer baseYear, Integer calcYear);

	/**
	 * @param id
	 * @param type
	 * @return
	 */
	int delBookVal(Long id, String type);

	/**
	 * @param id
	 * @param variable
	 * @param expression
	 * @param comment
	 * @return
	 */
	int addFormula(Long id, String variable, String expression, String comment);

	/**
	 * @param id
	 * @param variable
	 * @return
	 */
	int delFormula(Long id, String variable);

	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param content
	 * @return
	 */
	int fileUpload(Long id, String name, String type, byte[] content);

	/**
	 * @param id
	 * @return
	 */
	int delHistData(Long id);

}
