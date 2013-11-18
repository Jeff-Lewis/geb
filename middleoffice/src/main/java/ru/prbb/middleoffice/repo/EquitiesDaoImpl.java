/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.PortfolioItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * @author RBr
 *
 */
@Repository
@Transactional
public class EquitiesDaoImpl implements EquitiesDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<PortfolioItem> findAllPortfolio() {
		// select id_sec, ticker, deal_name, date_insert from portfolio_equity_v
		final List<PortfolioItem> list = new ArrayList<PortfolioItem>();
		for (long i = 1; i < 11; ++i) {
			final PortfolioItem item = new PortfolioItem();
			item.setId_sec(i);
			item.setDeal_name("deal_name" + i);
			item.setTicker("ticker" + i);
			item.setDate_insert(null);
			list.add(item);
		}
		return list;
	}

	@Override
	public List<SimpleItem> findCombo(String query) {
		// "select id, security_code as name from dbo.mo_WebGet_ajaxEquity_v"
		// " where lower(security_code) like ?"
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (int i = 0; i < 10; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}

	@Override
	public List<SimpleItem> findComboInvestmentPortfolio(String query) {
		// select name from dbo.investment_portfolio
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (int i = 0; i < 10; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}
}
