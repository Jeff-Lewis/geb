/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.CouponItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Дивиденды
 * 
 * @author RBr
 * 
 */
public interface CouponsDao {

	/**
	 * @param security
	 * @param client
	 * @param broker
	 * @param operation
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<CouponItem> findAll(Long security, Long client, Long broker, Long operation, Date begin, Date end);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public CouponItem findById(Long id);

	/**
	 * @param security
	 * @param account
	 * @param fund
	 * @param currency
	 * @param record
	 * @param receive
	 * @param quantity
	 * @param coupon_per_share
	 * @param extra_costs_per_share
	 * @param coupon_oper_id
	 * @return
	 */
	public int put(Long security, Long account, Long fund, Long currency,
			Date record, Date receive, Integer quantity,
			Double coupon_per_share, Double extra_costs_per_share, Long coupon_oper_id);

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

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findComboOperations(String query);

}