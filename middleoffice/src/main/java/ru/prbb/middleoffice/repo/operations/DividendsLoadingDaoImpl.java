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
public class DividendsLoadingDaoImpl extends BaseDaoImpl implements DividendsLoadingDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(Record r) {
		String sql = "{call dbo.mo_xlsLoadDividends_sp ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(1, r.security_code);
		q.setParameter(2, r.record_date);
		q.setParameter(3, r.div_per_share);
		q.setParameter(4, r.quantity);
		q.setParameter(5, r.receive_date);
		q.setParameter(6, r.account);
		q.setParameter(7, r.extra_costs);
		q.setParameter(8, r.currency);
		q.setParameter(9, r.fund);
		storeSql(sql, q);
		return q.executeUpdate();
	}

}
