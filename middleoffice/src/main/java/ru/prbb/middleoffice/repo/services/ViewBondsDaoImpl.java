/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Редактирование облигаций
 * 
 * @author RBr
 * 
 */
@Repository
public class ViewBondsDaoImpl implements ViewBondsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void put(Long id_sec, String deal) {
		String sql = "{call dbo.mo_WebSet_putDealsSecuritiesMapping_sp ?, ?, null, 5}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_sec)
				.setParameter(2, deal);
		q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void del(Long id_sec, String deal) {
		String sql = "{call dbo.mo_WebSet_dDealsSecuritiesMapping_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_sec)
				.setParameter(2, deal);
		q.executeUpdate();
	}

}
