/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.FuturesCoefficientItem;
import ru.prbb.middleoffice.domain.FuturesItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Фьючерсы
 * 
 * @author RBr
 * 
 */
@Repository
public class FuturesDaoImpl extends BaseDaoImpl implements FuturesDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<FuturesItem> findAll() {
		String sql = "{call dbo.mo_WebGet_SelectFuturesAlias_sp 'LS'}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<FuturesItem> res = new ArrayList<>(list.size());
		for (Object obj: list) {
			Object[] arr = (Object[]) obj;
			FuturesItem item = new FuturesItem();
			item.setFuturesId(Utils.toLong(arr[0]));
			item.setCoefId(Utils.toLong(arr[1]));
			item.setFutures(Utils.toString(arr[2]));
			item.setTradeSystem(Utils.toString(arr[3]));
			item.setCoefficient(Utils.toDouble(arr[4]));
			item.setComment(Utils.toString(arr[5]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public FuturesItem findById(Long futures_id) {
		String sql = "{call dbo.mo_WebGet_SelectFuturesAlias_sp 'FA', ?, null}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, futures_id);
		Object[] arr = (Object[]) getSingleResult(q, sql);
		FuturesItem item = new FuturesItem();
		item.setFuturesId(Utils.toLong(arr[0]));
		item.setFutures(Utils.toString(arr[1]));
		return item;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public FuturesCoefficientItem findCoefficientById(Long coef_id) {
		String sql = "{call dbo.mo_WebGet_SelectFuturesAlias_sp 'CF', null, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, coef_id);
		Object[] arr = (Object[]) getSingleResult(q, sql);
		FuturesCoefficientItem item = new FuturesCoefficientItem();
		item.setCoefId(Utils.toLong(arr[0]));
		item.setFuturesId(Utils.toLong(arr[1]));
		item.setTradeSystemId(Utils.toLong(arr[2]));
		item.setFutures(Utils.toString(arr[3]));
		item.setTradeSystem(Utils.toString(arr[4]));
		item.setCoefficient(Utils.toDouble(arr[5]));
		item.setComment(Utils.toString(arr[6]));
		return item;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String name, Number coef, String comment, Long sys_id) {
		Object futures_alias_id;
		{
			String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'i', 'fa', ?, null, null, null, null, null}";
			Query q = em.createNativeQuery(sql)
					.setParameter(1, name);
			storeSql(sql, q);
			futures_alias_id = getSingleResult(q, sql);
		}
		{
			String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'i', 'fc', null, null, ?, ?, ?, ?}";
			Query q = em.createNativeQuery(sql)
					.setParameter(1, coef)
					.setParameter(2, comment)
					.setParameter(3, sys_id)
					.setParameter(4, futures_alias_id);
			storeSql(sql, q);
			@SuppressWarnings("unused")
			Object futures_coef_id = getSingleResult(q, sql);
			return 2;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int putCoefficient(Long futures_alias_id, Number coef, String comment, Long sys_id) {
		String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'i', 'fc', null, null, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, coef)
				.setParameter(2, comment)
				.setParameter(3, sys_id)
				.setParameter(4, futures_alias_id);
		storeSql(sql, q);
		@SuppressWarnings("unused")
		Object futures_coef_id = getSingleResult(q, sql);
		return 1;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long futures_alias_id, String name) {
		String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'u', 'fa', ?, null, null, null, null, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name)
				.setParameter(2, futures_alias_id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateCoefficientById(Long futures_coef_id, Number coef, String comment, Long sys_id, Long futures_alias_id) {
		String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'u', 'fc', null, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, futures_coef_id)
				.setParameter(2, coef)
				.setParameter(3, comment)
				.setParameter(4, sys_id)
				.setParameter(5, futures_alias_id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'd', 'fa', null, null, null, null, null, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteCoefficientById(Long id) {
		String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'd', 'fc', null, ?, null, null, null, null}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxFuturesAlias_v";
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
