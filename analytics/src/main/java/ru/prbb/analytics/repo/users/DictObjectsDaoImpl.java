package ru.prbb.analytics.repo.users;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.DictObjectItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * 
 * @author BrihlyaevRA
 *
 */
@Service
public class DictObjectsDaoImpl extends BaseDaoImpl implements DictObjectsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<DictObjectItem> findAll() {
		String sql = "{call dbo.WebGet_SelectObjects_sp}";
		Query q = em.createNativeQuery(sql, DictObjectItem.class);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public DictObjectItem findById(Long id) {
		String sql = "{call dbo.WebGet_SelectObjects_sp ?}";
		Query q = em.createNativeQuery(sql, DictObjectItem.class)
				.setParameter(1, id);
		return (DictObjectItem) getSingleResult(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String name, String comment) {
		String sql = "{call dbo.WebSet_iudObjects_sp  'i', null, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name)
				.setParameter(2, comment);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, String name, String comment) {
		String sql = "{call dbo.WebSet_iudObjects_sp  'u', ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name)
				.setParameter(3, comment);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.WebSet_iudObjects_sp  'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}
}
