/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.DividendItem;

/**
 * Дивиденды
 * 
 * @author RBr
 * 
 */
public interface DividendsDao {

	/**
	 * 
	 * @param security
	 * @param client
	 * @param broker
	 * @param account
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<DividendItem> findAll(Long security, Long client, Long broker, Long account, Date begin, Date end);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public DividendItem findById(Long id);

	/**
	 * 
	 * @param security
	 * @param account
	 * @param currency
	 * @param record
	 * @param receive
	 * @param quantity
	 * @param dividend_per_share
	 * @param extra_costs_per_share
	 * @return
	 */
	public int put(Long security, Long account, Long currency,
			Date record, Date receive, Integer quantity,
			Double dividend_per_share, Double extra_costs_per_share);

	/**
	 * 
	 * @param id
	 * @param receive
	 * @return
	 */
	public int updateById(Long id, Date receive);

	/**
	 * 
	 * @param id
	 * @param type
	 * @param value
	 * @return
	 */
	public int updateAttrById(Long id, String type, String value);

	/**
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);

}