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

import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Редактирование фьючерсов
 * 
 * @author RBr
 * 
 */
@Repository
public class ViewFuturesDaoImpl extends BaseDaoImpl implements ViewFuturesDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void put(Long id_sec, String deal, Long futures) {
		String sql = "{call dbo.blm_cmdt_mapping ?, ?, ?, 2}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_sec)
				.setParameter(2, deal)
				.setParameter(3, futures);
		storeSql(sql, q);
		executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void del(Long id_sec, String deal) {
		String sql = "{call dbo.blm_cmdt_delete ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_sec)
				.setParameter(2, deal);
		storeSql(sql, q);
		executeUpdate(q, sql);
	}

}
