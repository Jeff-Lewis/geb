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

import ru.prbb.middleoffice.domain.NoConformityItem;

/**
 * Нет соответствия
 * 
 * @author RBr
 * 
 */
@Repository
public class NoConformityDaoImpl implements NoConformityDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<NoConformityItem> show() {
		String sql = "{call dbo.mo_WebGet_DealBlmTickerUnSet_sp}";
		Query q = em.createNativeQuery(sql, NoConformityItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void delete(Long[] ids) {
		String sql = "{call dbo.mo_WebSet_dTickerUnSetDeals_sp ?}";
		Query q = em.createNativeQuery(sql);
		int[] res = new int[ids.length];
		int i = 0;
		for (Long id : ids) {
			q.setParameter(1, id);
			res[i++] = q.executeUpdate();
		}
	}

}
