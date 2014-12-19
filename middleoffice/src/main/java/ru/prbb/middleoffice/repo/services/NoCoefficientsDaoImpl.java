/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.NoCoefficientsItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Не хватает коэффициентов
 * 
 * @author RBr
 */
@Repository
public class NoCoefficientsDaoImpl extends BaseDaoImpl implements NoCoefficientsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	@Deprecated
	public List<NoCoefficientsItem> show() {
		String sql = "select id_sec, sys_id, security_code, TradeSystem from dbo.mo_WebGet_noCoef_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = getResultList(q, sql);
		List<NoCoefficientsItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			NoCoefficientsItem item = new NoCoefficientsItem();
			item.setSecurityId(Utils.toLong(arr[0]));
			item.setTradeSystemId(Utils.toLong(arr[1]));
			item.setSecurityCode(Utils.toString(arr[2]));
			item.setTradeSystem(Utils.toString(arr[3]));
			res.add(item);
		}
		return res;
	}

	public List<NoCoefficientsItem> showFutures() {
		String sql = "select id_sec, sys_id, security_code, TradeSystem from dbo.mo_WebGet_noCoefFutures_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = getResultList(q, sql);
		List<NoCoefficientsItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			NoCoefficientsItem item = new NoCoefficientsItem();
			item.setType("futures");
			item.setSecurityId(Utils.toLong(arr[0]));
			item.setTradeSystemId(Utils.toLong(arr[1]));
			item.setSecurityCode(Utils.toString(arr[2]));
			item.setTradeSystem(Utils.toString(arr[3]));
			res.add(item);
		}
		return res;
	}

	public List<NoCoefficientsItem> showOptions() {
		String sql = "select id_sec, sys_id, security_code, TradeSystem from dbo.mo_WebGet_noCoefOptions_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = getResultList(q, sql);
		List<NoCoefficientsItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			NoCoefficientsItem item = new NoCoefficientsItem();
			item.setType("options");
			item.setSecurityId(Utils.toLong(arr[0]));
			item.setTradeSystemId(Utils.toLong(arr[1]));
			item.setSecurityCode(Utils.toString(arr[2]));
			item.setTradeSystem(Utils.toString(arr[3]));
			res.add(item);
		}
		return res;
	}

}
