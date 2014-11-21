/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

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
import ru.prbb.middleoffice.domain.CouponItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Дивиденды
 * 
 * @author RBr
 * 
 */
@Repository
public class CouponsDaoImpl extends BaseDaoImpl implements CouponsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<CouponItem> findAll(Long security, Long client, Long broker, Long operation, Date begin, Date end) {
		String sql = "{call dbo.mo_WebGet_Coupons_sp null, ?, ?, ?, null, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, security)
				.setParameter(2, client)
				.setParameter(3, broker)
				.setParameter(4, operation)
				.setParameter(5, begin)
				.setParameter(6, end);
		@SuppressWarnings("unchecked")
		List<Object[]> list = getResultList(q, sql);
		List<CouponItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			int i = 0;
			CouponItem item = new CouponItem();
			item.setId_sec(Utils.toLong(arr[i++]));
			item.setSecurity_code(Utils.toString(arr[i++]));
			item.setShort_name(Utils.toString(arr[i++]));
			item.setClient(Utils.toString(arr[i++]));
			item.setFund(Utils.toString(arr[i++]));
			item.setBroker(Utils.toString(arr[i++]));
			item.setAccount(Utils.toString(arr[i++]));
			item.setCurrency(Utils.toString(arr[i++]));
			item.setRecord_date(Utils.toSqlDate(arr[i++]));
			item.setQuantity(Utils.toInteger(arr[i++]));
			item.setCoupon_per_share(Utils.toDouble(arr[i++]));
			item.setReceive_date(Utils.toSqlDate(arr[i++]));
			item.setReal_coupon_per_share(Utils.toDouble(arr[i++]));
			item.setStatus(Utils.toString(arr[i++]));
			item.setEstimate(Utils.toDouble(arr[i++]));
			item.setReal_coupons(Utils.toDouble(arr[i++]));
			item.setExtra_costs(Utils.toDouble(arr[i++]));
			item.setTax_value(Utils.toDouble(arr[i++]));
			item.setCountry(Utils.toString(arr[i++]));
			item.setOper(Utils.toString(arr[i++]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public CouponItem findById(Long id) {
		String sql = "{call dbo.mo_WebGet_Coupons_sp ?}";
		Query q = em.createNativeQuery(sql, CouponItem.class)
				.setParameter(1, id);
		return (CouponItem) getSingleResult(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(Long security, Long account, Long fund, Long currency,
			Date record, Date receive, Integer quantity,
			Double coupon_per_share, Double extra_costs_per_share, Long coupon_oper_id) {
		String sql = "{call dbo.mo_WebSet_putCoupons_sp ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, security)
				.setParameter(2, account)
				.setParameter(3, fund)
				.setParameter(4, currency)
				.setParameter(5, record)
				.setParameter(6, receive)
				.setParameter(7, quantity)
				.setParameter(8, coupon_per_share)
				.setParameter(9, extra_costs_per_share)
				.setParameter(10, coupon_oper_id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, Date receive) {
		String sql = "{call dbo.mo_WebSet_uActualCoupons_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, receive);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateAttrById(Long id, String type, String value) {
		String sql = "{call dbo.mo_WebSet_setCouponAttributes_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, type)
				.setParameter(3, value);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_dCoupons_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findComboOperations(String query) {
		String sql = "select id, oper_name as name from dbo.mo_WebGet_ajaxCouponOperations_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(oper_name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return getResultList(q, sql);
	}
}
