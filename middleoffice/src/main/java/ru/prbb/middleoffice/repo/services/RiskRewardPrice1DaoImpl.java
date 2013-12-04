/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.RiskRewardPrice1Item;

/**
 * @author RBr
 * 
 */
@Repository
@Transactional
public class RiskRewardPrice1DaoImpl implements RiskRewardPrice1Dao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<RiskRewardPrice1Item> findAll() {
		String sql = "{call dbo.mo_WebGet_SecuritiesAttributes_sp}";
		Query q = em.createNativeQuery(sql, RiskRewardPrice1Item.class);
		return q.getResultList();
	}

	@Override
	public RiskRewardPrice1Item findById(Long id) {
		String sql = "{call dbo.mo_WebGet_SecuritiesAttributes_sp ?}";
		Query q = em.createNativeQuery(sql, RiskRewardPrice1Item.class)
				.setParameter(1, id);
		return (RiskRewardPrice1Item) q.getSingleResult();
	}

	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_dSecuritiesAttributes_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

}
