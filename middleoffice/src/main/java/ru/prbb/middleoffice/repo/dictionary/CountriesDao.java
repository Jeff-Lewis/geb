/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.CountryItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Страны
 * 
 * @author RBr
 * 
 */
public interface CountriesDao {

	/**
	 * @return
	 */
	public List<CountryItem> findAll();

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findCombo(String query);

}