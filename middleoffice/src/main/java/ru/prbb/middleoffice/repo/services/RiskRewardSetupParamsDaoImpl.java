/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.RiskRewardSetupParamsItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * @author RBr
 * 
 */
@Repository
public class RiskRewardSetupParamsDaoImpl extends BaseDaoImpl implements RiskRewardSetupParamsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<RiskRewardSetupParamsItem> findAll(Long security, Date date) {
		String sql = "{call dbo.mo_WebGet_SecurityParams_sp null, ?, ?}";
		Query q = em.createNativeQuery(sql, RiskRewardSetupParamsItem.class)
				.setParameter(1, security)
				.setParameter(2, date);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public RiskRewardSetupParamsItem findById(Long id) {
		String sql = "{call dbo.mo_WebGet_SecurityParams_sp ?}";
		Query q = em.createNativeQuery(sql, RiskRewardSetupParamsItem.class)
				.setParameter(1, id);
		return (RiskRewardSetupParamsItem) getSingleResult(q, sql);
	}

}
