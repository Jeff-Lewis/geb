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
	List<ViewPortfolioItem> findAll();

	/**
	 * @return
	 */
	List<ViewPortfolioItem> findAllPortfolio();

	/**
	 * @param ids
	 * @return
	 */
	int[] put(Long[] ids);

	/**
	 * @param ids
	 * @return
	 */
	int[] del(Long[] ids);

}
