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

import ru.prbb.middleoffice.domain.NoConformityItem;

/**
 * Нет соответствия
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class NoConformityDaoImpl implements NoConformityDao
{
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<NoConformityItem> show() {
		String sql = "execute dbo.mo_WebGet_DealBlmTickerUnSet_sp";
		Query q = em.createNativeQuery(sql, NoConformityItem.class);
		return q.getResultList();
	}

	@Override
	public void delete(Long[] ids) {
		String sql = "execute dbo.mo_WebSet_dTickerUnSetDeals_sp ?";
		// TODO em.createNativeQuery(sql).setParameter(1, id);
	}

}
