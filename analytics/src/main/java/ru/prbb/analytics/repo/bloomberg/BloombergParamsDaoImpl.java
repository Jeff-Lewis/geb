/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.SimpleItem;

/**
 * Параметры запросов
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class BloombergParamsDaoImpl implements BloombergParamsDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<SimpleItem> findPeriod() {
		String sql = "select period_id as id, name from dbo.period_type";
		return em.createQuery(sql, SimpleItem.class).getResultList();
	}

	@Override
	public List<SimpleItem> findCalendar() {
		String sql = "select calendar_id as id,  name from calendar_type";
		return em.createQuery(sql, SimpleItem.class).getResultList();
	}

}
