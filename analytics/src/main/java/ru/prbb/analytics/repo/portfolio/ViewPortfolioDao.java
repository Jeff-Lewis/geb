/**
 * 
 */
package ru.prbb.analytics.repo.portfolio;

import java.util.List;

import ru.prbb.analytics.domain.ViewPortfolioItem;

/**
 * Добавление организаций в Portfolio
 * 
 * @author RBr
 * 
 */
public interface ViewPortfolioDao {

	/**
	 * @return
	 */
	List<ViewPortfolioItem> getSecurities();

	/**
	 * @return
	 */
	List<ViewPortfolioItem> getPortfolio();

	/**
	 * @param ids
	 */
	void put(String[] ids);

	/**
	 * @param ids
	 */
	void del(String[] ids);

}
