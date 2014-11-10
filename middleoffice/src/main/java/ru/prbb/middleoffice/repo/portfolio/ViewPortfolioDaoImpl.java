/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.ViewPortfolioItem;
import ru.prbb.middleoffice.domain.ViewPortfolioTransferItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Текущий портфель
 * 
 * @author RBr
 * 
 */
@Repository
public class ViewPortfolioDaoImpl extends BaseDaoImpl implements ViewPortfolioDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<ViewPortfolioItem> executeSelect(Date date, Long security) {
		String sql = "{call dbo.mo_WebGet_SelectPlReport_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, date)
				.setParameter(2, security);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<ViewPortfolioItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			ViewPortfolioItem item = new ViewPortfolioItem();
			item.setReport_date(Utils.toDate(arr[0]));
			item.setClient(Utils.toString(arr[1]));
			item.setFund(Utils.toString(arr[2]));
			item.setSecurity_code(Utils.toString(arr[3]));
			item.setShort_name(Utils.toString(arr[4]));
			item.setBatch(Utils.toInteger(arr[5]));
			item.setUsd_funding(Utils.toString(arr[6]));
			item.setCurrency(Utils.toString(arr[7]));
			item.setQuantity(Utils.toInteger(arr[8]));
			item.setAvg_price(Utils.toDouble(arr[9]));
			item.setLast_price(Utils.toDouble(arr[10]));
			item.setNkd(Utils.toDouble(arr[11]));
			item.setPosition(Utils.toDouble(arr[12]));
			item.setPosition_rep_date(Utils.toDouble(arr[13]));
			item.setRevaluation(Utils.toDouble(arr[14]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<ViewPortfolioTransferItem> executeSelect(Date date) {
		String sql = "{call dbo.mo_WebGet_SelectPlReport_sp ?, null, 1}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, date);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<ViewPortfolioTransferItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			ViewPortfolioTransferItem item = new ViewPortfolioTransferItem();
			item.setId(Utils.toLong(arr[0]));
			item.setDated(Utils.toDate(arr[1]));
			item.setClient(Utils.toString(arr[2]));
			item.setFund(Utils.toString(arr[3]));
			item.setSecurity_code(Utils.toString(arr[4]));
			item.setBatch(Utils.toInteger(arr[5]));
			item.setQuantity(Utils.toInteger(arr[6]));
			item.setAvg_price(Utils.toDouble(arr[7]));
			item.setAvg_price_usd(Utils.toDouble(arr[8]));
			item.setCurrency(Utils.toString(arr[9]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED/*, timeout = 24 * 60 * 60*/)
	@Override
	public int executeCalc(Date begin_date, Long id_sec, Long client) {
//		String sql = "{call dbo.mo_WebSet_PlReport_sp ?, null, ?}";
		String sql = "{call dbo.PlPortfolioOnDate ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, begin_date)
				.setParameter(2, id_sec);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int executeDelete(Date begin_date, Long id_sec) {
		String sql = "{call dbo.mo_dFinResAndRests_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, begin_date)
				.setParameter(2, id_sec);
		storeSql(sql, q);
		return q.executeUpdate();
	}

}
