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
 * Редактирование акций
 * 
 * @author RBr
 * 
 */
@Repository
public class ViewShareDaoImpl implements ViewShareDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void put(Long id_sec, String deal) {
		String sql = "{call dbo.blm_cmdt_mapping ?, ?, null, 1}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_sec)
				.setParameter(2, deal);
		q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void del(Long id_sec, String deal) {
		String sql = "{call dbo.blm_cmdt_delete ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_sec)
				.setParameter(2, deal);
		q.executeUpdate();
	}

}
