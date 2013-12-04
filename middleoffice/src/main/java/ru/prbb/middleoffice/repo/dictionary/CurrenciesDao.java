/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.CurrenciesItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Валюты
 * 
 * @author RBr
 * 
 */
public interface CurrenciesDao {

	/**
	 * @return
	 */
	List<CurrenciesItem> findAll();

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findCombo(String query);

}
