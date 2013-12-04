/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.List;

import ru.prbb.middleoffice.domain.PortfolioItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewFuturesItem;

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
	 * @return
	 */
	List<PortfolioItem> findAllSwaps();

	/**
	 * @return
	 */
	List<PortfolioItem> findAllBonds();

	/**
	 * @return
	 */
	List<ViewFuturesItem> findAllFutures();

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
