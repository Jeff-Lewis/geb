/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.ViewAtrItem;

/**
 * Отображение ATR
 * 
 * @author RBr
 * 
 */
public interface ViewAtrDao {

	/**
	 * @param begin
	 * @param end
	 * @param securities
	 * @return
	 */
	List<ViewAtrItem> execute(Date begin, Date end, Long[] securities);

}
