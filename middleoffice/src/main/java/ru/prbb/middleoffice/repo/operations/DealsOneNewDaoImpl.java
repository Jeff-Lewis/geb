/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * @author RBr
 */
@Service
public class DealsOneNewDaoImpl extends BaseDaoImpl implements DealsOneNewDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String batch, String tradeNum, String tradeTicker,
			Date tradeDate, Date settleDate, String tradeOper, Double tradePrice,
			Double quantity, String currency, String tradeSystem, String account,
			String investmentPortfolio, String securityCode, String futuresAlias,
			Integer kindTicker) {
		String sql = "{call dbo.mo_WebSet_putSingleDeal_sp"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?}";
		int i = 0;
		Query q = em.createNativeQuery(sql);
		q.setParameter(++i, batch);
		q.setParameter(++i, tradeNum);
		q.setParameter(++i, tradeTicker);
		q.setParameter(++i, tradeDate);
		q.setParameter(++i, settleDate);
		q.setParameter(++i, tradeOper);
		q.setParameter(++i, tradePrice);
		q.setParameter(++i, quantity);
		q.setParameter(++i, currency);
		q.setParameter(++i, tradeSystem);
		q.setParameter(++i, account);
		q.setParameter(++i, investmentPortfolio);
		q.setParameter(++i, securityCode);
		q.setParameter(++i, futuresAlias);
		q.setParameter(++i, kindTicker);
		storeSql(sql, q);
		return q.executeUpdate();
	}
}
