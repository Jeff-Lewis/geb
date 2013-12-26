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

import ru.prbb.middleoffice.domain.DealsPatternItem;

/**
 * Сохраненные шаблоны
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class DealsPatternDaoImpl implements DealsPatternDao
{
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<DealsPatternItem> show() {
		String sql = "select id, file_name, file_type, date_insert from dbo.DealsTemplateStorage";
		Query q = em.createNativeQuery(sql, DealsPatternItem.class);
		return q.getResultList();
	}

	@Override
	public int deleteById(Long id) {
		String sql = "delete from dbo.DealsTemplateStorage where id = ?";
		Query q = em.createNativeQuery(sql).setParameter(1, id);
		return 0;//q.executeUpdate();
	}

}
