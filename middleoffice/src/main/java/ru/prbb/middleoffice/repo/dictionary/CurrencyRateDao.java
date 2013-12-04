/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.CurrencyRateItem;

/**
 * Курсы валют
 * 
 * @author RBr
 * 
 */
public interface CurrencyRateDao {

	/**
	 * @param parseDate
	 * @param parseString
	 * @return
	 */
	List<CurrencyRateItem> findAll(Date parseDate, String parseString);

}
