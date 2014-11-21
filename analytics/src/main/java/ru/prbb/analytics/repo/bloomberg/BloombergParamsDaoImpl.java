/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * Параметры запросов
 * 
 * @author RBr
 * 
 */
@Service
public class BloombergParamsDaoImpl extends BaseDaoImpl implements BloombergParamsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findPeriod(String query) {
		String sql = "select period_id as id, name from dbo.period_type";
		Query q = em.createNativeQuery(sql, SimpleItem.class);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCalendar(String query) {
		String sql = "select calendar_id as id, name from dbo.calendar_type";
		Query q = em.createNativeQuery(sql, SimpleItem.class);
		return getResultList(q, sql);
	}

}
