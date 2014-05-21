/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.SecurityRiscsItem;

/**
 * Заданные параметры риска
 * 
 * @author RBr
 * 
 */
@Repository
public class SecurityRiscsDaoImpl implements SecurityRiscsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityRiscsItem> findAll(Long security_id, Long fund_id, Integer batch, Long p_id, Date date) {
		String sql = "{call dbo.mo_WebGet_SecurityRiscs_sp null, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql, SecurityRiscsItem.class)
				.setParameter(1, security_id)
				.setParameter(4, fund_id)
				.setParameter(3, batch)
				.setParameter(2, p_id)
				.setParameter(5, date);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public SecurityRiscsItem findById(Long id) {
		String sql = "{call dbo.mo_WebGet_SecurityRiscs_sp ?}";
		Query q = em.createNativeQuery(sql, SecurityRiscsItem.class)
				.setParameter(1, id);
		return (SecurityRiscsItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_dSecurityRiscs_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, Long client_id, Long fund_id, Integer batch,
			BigDecimal risk_ath, BigDecimal risk_avg, BigDecimal stop_loss,
			Date date_begin, Date date_end, String comment) {
		String sql = "{call dbo.mo_WebSet_setSecurityRiscs_sp ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql, SecurityRiscsItem.class)
				.setParameter(1, id)
				.setParameter(2, client_id)
				.setParameter(3, fund_id)
				.setParameter(4, batch)
				.setParameter(5, risk_ath)
				.setParameter(6, risk_avg)
				.setParameter(7, stop_loss)
				.setParameter(8, date_begin)
				.setParameter(9, date_end)
				.setParameter(10, comment);
		return q.executeUpdate();
	}
}
