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

import ru.prbb.middleoffice.domain.DealsPatternItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Сохраненные шаблоны
 * 
 * @author RBr
 * 
 */
@Repository
public class DealsPatternDaoImpl extends BaseDaoImpl implements DealsPatternDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<DealsPatternItem> show() {
		String sql = "select id, file_name, file_type, date_insert from dbo.DealsTemplateStorage";
		Query q = em.createNativeQuery(sql, DealsPatternItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public DealsPatternItem getById(Long id) {
		String sql = "select id, file_name, file_type, date_insert"
				+ " from dbo.DealsTemplateStorage where id=?";
		Query q = em.createNativeQuery(sql, DealsPatternItem.class)
				.setParameter(1, id);
		return (DealsPatternItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "delete from dbo.DealsTemplateStorage where id=?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public byte[] getFileById(Long id) {
		String sql = "select file from dbo.DealsTemplateStorage where id=?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return (byte[]) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int add(String name, String type, byte[] bytes) {
		String sql = "insert into dbo.DealsTemplateStorage" +
				" (file, file_name, file_type) values(?, ?, ?)";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, bytes)
				.setParameter(2, name)
				.setParameter(3, type);
		storeSql(sql, q);
		return q.executeUpdate();
	}
}
