/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findPeriod() {
		String sql = "select period_id as id, name from dbo.period_type";
		Query q = em.createNativeQuery(sql, SimpleItem.class);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCalendar() {
		String sql = "select calendar_id as id, name from dbo.calendar_type";
		Query q = em.createNativeQuery(sql, SimpleItem.class);
		return q.getResultList();
	}

}
