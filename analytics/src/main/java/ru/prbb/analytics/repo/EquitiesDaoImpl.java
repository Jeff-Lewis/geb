/**
 * 
 */
package ru.prbb.analytics.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.EquityItem;
import ru.prbb.analytics.domain.PortfolioItem;
import ru.prbb.analytics.domain.SimpleItem;

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
	@Override
	public List<EquityItem> findAllEquities(String filter, Long security_id, Integer fund_flag) {
		String sql = "{call dbo.anca_WebGet_EquityFilter_sp :filter, :fund_flag, :security_id}";
		return em.createQuery(sql, EquityItem.class)
				.setParameter(1, filter).setParameter(2, fund_flag).setParameter(3, security_id)
				.getResultList();
	}

	@Override
	public List<SimpleItem> comboFilter() {
		String sql = "select null as id, name from dbo.anca_WebGet_ajaxEquityFilter_v";
		return em.createQuery(sql, SimpleItem.class).getResultList();
	}

	@Override
	public List<SimpleItem> comboEquities(String query) {
		String sql = "select id, name from dbo.anca_WebGet_ajaxEquity_v";
		if (Utils.isEmpty(query)) {
			return em.createQuery(sql, SimpleItem.class).getResultList();
		} else {
			sql += " where lower(name) like :q";
			query = '%' + query.toLowerCase() + '%';
			return em.createQuery(sql, SimpleItem.class).setParameter(1, query).getResultList();
		}
	}
}
