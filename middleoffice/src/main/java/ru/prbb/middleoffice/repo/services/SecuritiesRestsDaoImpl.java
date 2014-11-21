/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.SecuritiesRestsItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * @author RBr
 * 
 */
@Repository
public class SecuritiesRestsDaoImpl extends BaseDaoImpl implements SecuritiesRestsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SecuritiesRestsItem> execute(Long security, Long client, Long fund, Integer batch, Date date) {
		String sql = "{call dbo.mo_WebGet_securities_rests_sp null, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql, SecuritiesRestsItem.class)
				.setParameter(1, security)
				.setParameter(2, fund)
				.setParameter(3, batch)
				.setParameter(4, client)
				.setParameter(5, date);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, Byte checkFlag) {
		String sql = "{call dbo.mo_WebSet_set_securities_rests_sp ?, ?}";
		Query q = em.createNativeQuery(sql, SecuritiesRestsItem.class)
				.setParameter(1, id)
				.setParameter(2, checkFlag);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

}
