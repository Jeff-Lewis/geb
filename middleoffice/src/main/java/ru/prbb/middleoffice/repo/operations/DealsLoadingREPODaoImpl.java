/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * @author RBr
 */
@Repository
public class DealsLoadingREPODaoImpl extends BaseDaoImpl implements DealsLoadingREPODao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(Record r) {
		String sql = "{call dbo.mo_WebSet_putRepoDeals_sp ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(1, r.date);
		q.setParameter(2, r.time);
		q.setParameter(3, r.deal_num);
		q.setParameter(4, r.deal_security);
		q.setParameter(5, r.oper);
		q.setParameter(6, r.price);
		q.setParameter(7, r.quantity);
		q.setParameter(8, r.account);
		q.setParameter(9, r.rate);
		q.setParameter(10, r.days);
		q.setParameter(11, r.currency);
		q.setParameter(12, r.trade_system);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

}
