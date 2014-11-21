/**
 * 
 */
package ru.prbb.analytics.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.EquitiesItem;
import ru.prbb.analytics.domain.PortfolioItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * @author RBr
 * 
 */
@Repository
public class EquitiesDaoImpl extends BaseDaoImpl implements EquitiesDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<PortfolioItem> findAllPortfolio() {
		// select id_sec, ticker, deal_name, date_insert from portfolio_equity_v
		final List<PortfolioItem> list = new ArrayList<>();
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findCombo(String query) {
		// "select id, security_code as name from dbo.mo_WebGet_ajaxEquity_v"
		// " where lower(security_code) like ?"
		final List<SimpleItem> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findComboInvestmentPortfolio(String query) {
		// select name from dbo.investment_portfolio
		final List<SimpleItem> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<EquitiesItem> findAllEquities(String filter, Long security_id, Integer fund_flag) {
		String sql = "{call dbo.anca_WebGet_EquityFilter_sp}";
		Query q;
		if (Utils.isEmpty(filter) && Utils.isEmpty(security_id)) {
			q = em.createNativeQuery(sql, EquitiesItem.class);
		} else {
			sql = "{call dbo.anca_WebGet_EquityFilter_sp ?, ?, ?}";
			q = em.createNativeQuery(sql, EquitiesItem.class)
					.setParameter(1, filter)
					.setParameter(2, fund_flag)
					.setParameter(3, security_id);
		}
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> comboFilter(String query) {
		String sql = "select name from dbo.anca_WebGet_ajaxEquityFilter_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return Utils.toSimpleItem(getResultList(q, sql));
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> comboEquities(String query) {
		String sql = "select id, name from dbo.anca_WebGet_ajaxEquity_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return getResultList(q, sql);
	}
}
