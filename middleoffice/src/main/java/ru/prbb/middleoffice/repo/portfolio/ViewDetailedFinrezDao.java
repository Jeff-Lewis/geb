/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.ViewDetailedFinrezItem;

/**
 * Текущий финрез
 * 
 * @author RBr
 * 
 */
public interface ViewDetailedFinrezDao {

	/**
	 * 
	 * @param security
	 * @param begin
	 * @param end
	 * @param client
	 * @param fund
	 * @return
	 */
	List<ViewDetailedFinrezItem> executeSelect(Long security, Date begin, Date end, Long client, Long fund);

}
