/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.BrokersCoverageItem;

/**
 * Покрытие брокеров
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class BrokersCoverageDaoImpl implements BrokersCoverageDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<BrokersCoverageItem> execute() {
		String sql = "{call dbo.anca_WebGet_EquityBrokerCoverage_sp}";
		return em.createQuery(sql, BrokersCoverageItem.class).getResultList();
	}

	@Override
	public int change(Long id, String broker, Integer value) {
		String sql = "{call dbo.anca_WebSet_setEquityBrokerCoverage_sp :id, :broker, :value}";
		return em.createQuery(sql).setParameter(1, id).setParameter(2, broker).setParameter(3, value).executeUpdate();
	}
}
