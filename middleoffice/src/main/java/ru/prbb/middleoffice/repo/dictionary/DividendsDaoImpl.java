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

import ru.prbb.middleoffice.domain.DividendItem;

/**
 * Дивиденды
 * 
 * @author RBr
 * 
 */
@Repository
public class DividendsDaoImpl implements DividendsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<DividendItem> findAll(Long security, Long client, Long broker, Long account, Date begin, Date end) {
		String sql = "{call dbo.mo_WebGet_Dividends_sp null, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql, DividendItem.class)
				.setParameter(1, security)
				.setParameter(2, client)
				.setParameter(3, broker)
				.setParameter(4, account)
				.setParameter(5, begin)
				.setParameter(6, end);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public DividendItem findById(Long id) {
		String sql = "{call dbo.mo_WebGet_Dividends_sp ?}";
		Query q = em.createNativeQuery(sql, DividendItem.class)
				.setParameter(1, id);
		return (DividendItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(Long security, Long account, Long fund, Long currency,
			Date record, Date receive, Integer quantity,
			Double dividend_per_share, Double extra_costs_per_share) {
		String sql = "{call dbo.mo_WebSet_putDividends_sp ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, security)
				.setParameter(2, account)
				.setParameter(3, fund)
				.setParameter(4, currency)
				.setParameter(5, record)
				.setParameter(6, receive)
				.setParameter(7, quantity)
				.setParameter(8, dividend_per_share)
				.setParameter(9, extra_costs_per_share);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, Date receive) {
		String sql = "{call dbo.mo_WebSet_uActualDividends_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, receive);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateAttrById(Long id, String type, String value) {
		String sql = "{call dbo.mo_WebSet_setDividendAttributes_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, type)
				.setParameter(3, value);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_dDividends_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

}
