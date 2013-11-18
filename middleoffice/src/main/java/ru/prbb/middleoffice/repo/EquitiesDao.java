/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.List;

import ru.prbb.middleoffice.domain.PortfolioItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * @author RBr
 *
 */
public interface EquitiesDao {

	/**
	 * @return
	 */
	List<PortfolioItem> findAllPortfolio();

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findCombo(String query);

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findComboInvestmentPortfolio(String query);

}
