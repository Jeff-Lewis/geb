/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
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
	 * @param securityType
	 * @param country
	 * @param broker
	 * @param value
	 * @param dateBegin
	 * @return
	 */
	public int put(Long securityType, Long country, Long broker, Double value, Date dateBegin);

	/**
	 * 
	 * @param id
	 * @param value
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public int updateById(Long id, Double value, Date dateBegin, Date dateEnd);

	/**
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);

}