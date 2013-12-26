/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.ViewDealsREPOItem;

/**
 * Сделки РЕПО
 * 
 * @author RBr
 * 
 */
public interface ViewDealsREPODao {

	/**
	 * @param begin
	 * @param end
	 * @param security
	 * @return
	 */
	List<ViewDealsREPOItem> findAll(Date begin, Date end, Long security);

	/**
	 * @param id
	 * @return
	 */
	ViewDealsREPOItem findById(Long id);

	/**
	 * 
	 * @param id
	 * @param rate
	 * @param quantity
	 * @param price
	 * @param days
	 * @return
	 */
	int updateById(Long id, Double rate, Integer quantity, Double price, Integer days);

	/**
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(Long id);

}
