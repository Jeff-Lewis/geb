/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.RiskRewardPrice1Item;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * @author RBr
 * 
 */
@Repository
public class RiskRewardPrice1DaoImpl extends BaseDaoImpl implements RiskRewardPrice1Dao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<RiskRewardPrice1Item> findAll() {
		String sql = "{call dbo.mo_WebGet_SecuritiesAttributes_sp}";
		Query q = em.createNativeQuery(sql, RiskRewardPrice1Item.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public RiskRewardPrice1Item findById(Long id) {
		String sql = "{call dbo.mo_WebGet_SecuritiesAttributes_sp ?}";
		Query q = em.createNativeQuery(sql, RiskRewardPrice1Item.class)
				.setParameter(1, id);
		return (RiskRewardPrice1Item) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_dSecuritiesAttributes_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return q.executeUpdate();
	}

}
