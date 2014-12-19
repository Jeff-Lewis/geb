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
import ru.prbb.middleoffice.domain.OptionsCoefficientItem;
import ru.prbb.middleoffice.domain.OptionsItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Фьючерсы
 * 
 * @author RBr
 * 
 */
@Repository
public class OptionsDaoImpl extends BaseDaoImpl implements OptionsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<OptionsItem> findAll() {
		String sql = "{call dbo.mo_WebGet_SelectOptionsAlias_sp 'LS'}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<OptionsItem> res = new ArrayList<>(list.size());
		for (Object obj: list) {
			Object[] arr = (Object[]) obj;
			OptionsItem item = new OptionsItem();
			item.setOptionsId(Utils.toLong(arr[0]));
			item.setCoefId(Utils.toLong(arr[1]));
			item.setOptions(Utils.toString(arr[2]));
			item.setTradeSystem(Utils.toString(arr[3]));
			item.setCoefficient(Utils.toDouble(arr[4]));
			item.setComment(Utils.toString(arr[5]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public OptionsItem findById(Long futures_id) {
		String sql = "{call dbo.mo_WebGet_SelectOptionsAlias_sp 'OA', ?, null}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, futures_id);
		Object[] arr = (Object[]) getSingleResult(q, sql);
		OptionsItem item = new OptionsItem();
		item.setOptionsId(Utils.toLong(arr[0]));
		item.setOptions(Utils.toString(arr[1]));
		return item;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public OptionsCoefficientItem findCoefficientById(Long coef_id) {
		String sql = "{call dbo.mo_WebGet_SelectOptionsAlias_sp 'CO', null, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, coef_id);
		Object[] arr = (Object[]) getSingleResult(q, sql);
		OptionsCoefficientItem item = new OptionsCoefficientItem();
		item.setCoefId(Utils.toLong(arr[0]));
		item.setOptionsId(Utils.toLong(arr[1]));
		item.setTradeSystemId(Utils.toLong(arr[2]));
		item.setOptions(Utils.toString(arr[3]));
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
			String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'i', 'oa', ?, null, null, null, null, null}";
			Query q = em.createNativeQuery(sql)
					.setParameter(1, name);
			storeSql(sql, q);
			futures_alias_id = getSingleResult(q, sql);
		}
		{
			String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'i', 'oc', null, null, ?, ?, ?, ?}";
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
		String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'i', 'oc', null, null, ?, ?, ?, ?}";
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
		String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'u', 'oa', ?, null, null, null, null, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name)
				.setParameter(2, futures_alias_id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateCoefficientById(Long futures_coef_id, Number coef, String comment, Long sys_id, Long futures_alias_id) {
		String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'u', 'oc', null, ?, ?, ?, ?, ?}";
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
		String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'd', 'oa', null, null, null, null, null, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteCoefficientById(Long id) {
		String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'd', 'oc', null, ?, null, null, null, null}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxOptionsAlias_v";
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
