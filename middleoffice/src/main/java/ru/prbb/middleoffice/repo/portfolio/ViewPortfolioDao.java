/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.ViewPortfolioItem;

/**
 * Текущий портфель
 * 
 * @author RBr
 * 
 */
public interface ViewPortfolioDao {

	/**
	 * @param date
	 * @param security
	 * @return
	 */
	List<ViewPortfolioItem> executeSelect(Date date, Long security);

	/**
	 * @param date
	 * @param security
	 */
	void executeCalc(Date date, Long security);

}
