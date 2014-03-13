/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
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

/**
 * Дивиденды
 * 
 * @author RBr
 * 
 */
@Repository
public class CouponsDaoImpl implements CouponsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CouponItem> findAll(Long security, Long client, Long broker, Long operation, Date begin, Date end) {
		String sql = "{call dbo.mo_WebGet_Coupons_sp null, ?, ?, ?, null, ?, ?, ?}";
		Query q = em.createNativeQuery(sql, CouponItem.class)
				.setParameter(1, security)
				.setParameter(2, client)
				.setParameter(3, broker)
				.setParameter(4, operation)
				.setParameter(5, begin)
				.setParameter(6, end);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public CouponItem findById(Long id) {
		String sql = "{call dbo.mo_WebGet_Coupons_sp ?}";
		Query q = em.createNativeQuery(sql, CouponItem.class)
				.setParameter(1, id);
		return (CouponItem) q.getSingleResult();
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
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, Date receive) {
		String sql = "{call dbo.mo_WebSet_uActualCoupons_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, receive);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateAttrById(Long id, String type, String value) {
		String sql = "{call dbo.mo_WebSet_setCouponAttributes_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, type)
				.setParameter(3, value);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_dCoupons_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
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
		return q.getResultList();
	}
}
