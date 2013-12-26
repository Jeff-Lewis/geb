/**
 * 
 */
package ru.prbb.analytics.repo;

import java.util.List;

import ru.prbb.analytics.domain.EquitiesItem;
import ru.prbb.analytics.domain.PortfolioItem;
import ru.prbb.analytics.domain.SimpleItem;

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

	/**
	 * dbo.anca_WebGet_EquityFilter_sp
	 * 
	 * @param filter
	 *            varchar(255) = 'Все',
	 * @param fund_flag
	 *            int = 0,
	 * @param security_id
	 *            numeric(18) = null
	 * 
	 */
	List<EquitiesItem> findAllEquities(String filter, Long security_id, Integer fund_flag);

	/**
	 * select name from dbo.anca_WebGet_ajaxEquityFilter_v
	 * 
	 * @param query
	 * 
	 * @return
	 */
	List<SimpleItem> comboFilter(String query);

	/**
	 * select id, name from dbo.anca_WebGet_ajaxEquity_v
	 * 
	 * @param query
	 * @return
	 */
	List<SimpleItem> comboEquities(String query);

}
