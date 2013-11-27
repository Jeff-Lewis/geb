/**
 * 
 */
package ru.prbb.analytics.repo.portfolio;

import java.util.List;

import ru.prbb.analytics.domain.ViewPortfolioItem;
import ru.prbb.analytics.domain.ViewPortfolioSecurityItem;

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
	List<ViewPortfolioSecurityItem> getSecurities();

	/**
	 * @return
	 */
	List<ViewPortfolioItem> getPortfolio();

	/**
	 * @param ids
	 * @return
	 */
	int[] put(String[] ids);

	/**
	 * @param ids
	 * @return
	 */
	int[] del(String[] ids);

}
