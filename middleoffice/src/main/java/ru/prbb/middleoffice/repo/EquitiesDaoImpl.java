/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.PortfolioItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewFuturesItem;

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
		String sql = "select id_sec, ticker, deal_name, date_insert from portfolio_equity_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<PortfolioItem> res = new ArrayList<PortfolioItem>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			PortfolioItem item = new PortfolioItem();
			item.setId_sec(Utils.toLong(arr[0]));
			item.setTicker(Utils.toString(arr[1]));
			item.setDeal_name(Utils.toString(arr[2]));
			item.setDate_insert(Utils.toTimestamp(arr[3]));
			res.add(item);
		}
		return res;
	}

	@Override
	public List<PortfolioItem> findAllSwaps() {
		String sql = "{call mo_WebGet_SecuritiesDealNameMapping_sp 4}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<PortfolioItem> res = new ArrayList<PortfolioItem>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			PortfolioItem item = new PortfolioItem();
			item.setId_sec(Utils.toLong(arr[0]));
			item.setTicker(Utils.toString(arr[1]));
			item.setDeal_name(Utils.toString(arr[2]));
			item.setDate_insert(Utils.toTimestamp(arr[3]));
			res.add(item);
		}
		return res;
	}

	@Override
	public List<PortfolioItem> findAllBonds() {
		String sql = "{call dbo.mo_WebGet_SecuritiesDealNameMapping_sp 5}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<PortfolioItem> res = new ArrayList<PortfolioItem>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			PortfolioItem item = new PortfolioItem();
			item.setId_sec(Utils.toLong(arr[0]));
			item.setTicker(Utils.toString(arr[2]));
			item.setDeal_name(Utils.toString(arr[3]));
			item.setDate_insert(Utils.toTimestamp(arr[4]));
			res.add(item);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewFuturesItem> findAllFutures() {
		String sql = "select * from portfolio_cmdt_v";
		Query q = em.createNativeQuery(sql, ViewFuturesItem.class);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, security_code as name from dbo.mo_WebGet_ajaxEquity_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(security_code) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findComboInvestmentPortfolio(String query) {
		String sql = "select p_id as id, name from dbo.investment_portfolio";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return q.getResultList();
	}
}
