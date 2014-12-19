/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.List;

import ru.prbb.middleoffice.domain.PortfolioItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewFuturesItem;
import ru.prbb.middleoffice.domain.ViewOptionsItem;

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
	 * @return
	 */
	List<ViewOptionsItem> findAllOptions();

	/**
	 * mo_WebGet_ajaxEquity_v
	 * 
	 * @param query
	 * @return
	 */
	List<SimpleItem> findCombo(String query);

	/**
	 * investment_portfolio
	 * 
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboInvestmentPortfolio(String query);

	/**
	 * mo_WebGet_Portfolio_v
	 * 
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboPortfolio(String query);

}
