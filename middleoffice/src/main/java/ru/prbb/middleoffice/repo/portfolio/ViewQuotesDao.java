/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.ViewQuotesItem;

/**
 * Котировки
 * 
 * @author RBr
 * 
 */
public interface ViewQuotesDao {

	/**
	 * @param begin
	 * @param end
	 * @param securities
	 * @return
	 */
	List<ViewQuotesItem> execute(Date begin, Date end, Long[] securities);

}
