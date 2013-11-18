/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import ru.prbb.analytics.domain.ViewExceptionsItem;

/**
 * Отчёт по исключениям
 * 
 * @author RBr
 * 
 */
public interface ViewExceptionsDao {

	/**
	 * @return
	 */
	List<ViewExceptionsItem> execute();

}
