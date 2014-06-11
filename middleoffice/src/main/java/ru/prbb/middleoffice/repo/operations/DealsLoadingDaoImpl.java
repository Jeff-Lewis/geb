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


/**
 * @author RBr
 *
 */
@Repository
public class DealsLoadingDaoImpl implements DealsLoadingDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(Record r) {
		String sql = "{call dbo.mo_WebSet_putDeals_sp ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(1, r.Batch);
		q.setParameter(2, r.TradeNum);
		q.setParameter(3, r.TradeTicker);
		q.setParameter(4, r.TradeDate);
		q.setParameter(5, r.SettleDate);
		q.setParameter(6, r.TradeOper);
		q.setParameter(7, r.TradePrice);
		q.setParameter(8, r.Quantity);
		q.setParameter(9, r.Currency);
		q.setParameter(10, r.TradeSystem);
		q.setParameter(11, r.Account);
		q.setParameter(12, r.Portfolio);
		q.setParameter(13, r.Initiator);
		return q.executeUpdate();
	}

}
