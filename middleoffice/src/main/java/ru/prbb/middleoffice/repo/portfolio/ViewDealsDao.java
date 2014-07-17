/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.ViewDealsItem;

/**
 * Список сделок
 * 
 * @author RBr
 * 
 */
public interface ViewDealsDao {

	/**
	 * @param begin
	 * @param end
	 * @param security
	 * @param client
	 * @param funds
	 * @param initiator
	 * @return
	 */
	List<ViewDealsItem> findAll(Date begin, Date end, Long security, Long client, Long funds, Long initiator);

	/**
	 * @param deals
	 */
	void deleteById(Long[] deals);

	/**
	 * @param deals
	 * @param field
	 * @param value
	 */
	void updateById(Long[] deals, String field, String value);

}
