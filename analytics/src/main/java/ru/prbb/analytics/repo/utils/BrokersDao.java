/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.List;

import ru.prbb.analytics.domain.BrokerItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * Справочник брокеров
 * 
 * @author RBr
 * 
 */
public interface BrokersDao {

	/**
	 * @return
	 */
	public List<BrokerItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public BrokerItem findById(Long id);

	/**
	 * 
	 * @param full_name
	 * @param rating
	 * @param bloomberg_code
	 * @param cover_russian
	 * @param short_name
	 * @return 
	 */
	public int put(String full_name, Integer rating, String bloomberg_code,
			Integer cover_russian, String short_name);

	/**
	 * 
	 * @param id
	 * @param name
	 * @param comment
	 * @return 
	 */
	public int updateById(Long id, String full_name, Integer rating, String bloomberg_code,
			Integer cover_russian, String short_name);

	/**
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findCombo(String query);

}