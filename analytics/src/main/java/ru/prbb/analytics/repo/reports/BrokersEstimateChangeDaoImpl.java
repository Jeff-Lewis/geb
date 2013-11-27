/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.BrokersEstimateChangeItem;

/**
 * Изменение оценок брокеров
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class BrokersEstimateChangeDaoImpl implements BrokersEstimateChangeDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<BrokersEstimateChangeItem> execute() {
		String sql = "{call dbo.anca_WebGet_BrokerEstimatesChange_sp}";
		return em.createQuery(sql, BrokersEstimateChangeItem.class).getResultList();
	}

}
