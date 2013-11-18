/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.CountryTaxItem;

/**
 * Налоги по странам
 * 
 * @author RBr
 * 
 */
public interface CountryTaxesDao {

	/**
	 * @return
	 */
	public List<CountryTaxItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public CountryTaxItem findById(Long id);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public Long put(CountryTaxItem value);

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, CountryTaxItem value);

	/**
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id);

}