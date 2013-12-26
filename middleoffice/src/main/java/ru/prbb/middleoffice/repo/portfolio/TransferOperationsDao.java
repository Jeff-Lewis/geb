/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.TransferOperationsItem;

/**
 * Список перекидок
 * 
 * @author RBr
 * 
 */
public interface TransferOperationsDao {

	/**
	 * 
	 * @param begin
	 * @param end
	 * @param security
	 * @return
	 */
	List<TransferOperationsItem> findAll(Date begin, Date end, Long security);

	/**
	 * @param ids
	 */
	void deleteById(Long[] ids);

	/**
	 * @param ids
	 * @param field
	 * @param value
	 */
	void updateById(Long[] ids, String field, String value);

}
