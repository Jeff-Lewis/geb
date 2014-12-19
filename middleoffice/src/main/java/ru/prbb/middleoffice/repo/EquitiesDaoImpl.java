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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.PortfolioItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewFuturesItem;
import ru.prbb.middleoffice.domain.ViewOptionsItem;

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
		String sql = "select id_sec, ticker, deal_name, date_insert from dbo.portfolio_equity_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<PortfolioItem> res = new ArrayList<>(list.size());
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<PortfolioItem> findAllSwaps() {
		String sql = "{call mo_WebGet_SecuritiesDealNameMapping_sp 4}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<PortfolioItem> res = new ArrayList<>(list.size());
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<PortfolioItem> findAllBonds() {
		String sql = "{call dbo.mo_WebGet_SecuritiesDealNameMapping_sp 5}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<PortfolioItem> res = new ArrayList<>(list.size());
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<ViewFuturesItem> findAllFutures() {
		String sql = "select * from dbo.portfolio_cmdt_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<ViewFuturesItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			ViewFuturesItem item = new ViewFuturesItem();
			int col = 0;
			item.setId_sec(Utils.toLong(arr[col++]));
			item.setTicker(Utils.toString(arr[col++]));
			item.setDeal_name(Utils.toString(arr[col++]));
			item.setName(Utils.toString(arr[col++]));
			item.setDate_insert(Utils.toTimestamp(arr[col++]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<ViewOptionsItem> findAllOptions() {
		String sql = "select * from dbo.portfolio_options_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<ViewOptionsItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			ViewOptionsItem item = new ViewOptionsItem();
			int col = 0;
			item.setId_sec(Utils.toLong(arr[col++]));
			item.setTicker(Utils.toString(arr[col++]));
			item.setDeal_name(Utils.toString(arr[col++]));
			item.setName(Utils.toString(arr[col++]));
			item.setDate_insert(Utils.toTimestamp(arr[col++]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findComboPortfolio(String query) {
		String sql = "select id_sec as id, security_code as name from dbo.mo_WebGet_Portfolio_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(security_code) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return getResultList(q, sql);
	}
}
