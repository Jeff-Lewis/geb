/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Перекидка ЦБ между фондами
 * 
 * @author RBr
 */
@Service
public class DealsTransferDaoImpl implements DealsTransferDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public int execute(Long portfolioId, Integer quantity, Double price,
			Long fundId, Integer batch, String comment) {
		String sql = "{call dbo.mo_WebSet_putTransferDeals_sp ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, portfolioId)
				.setParameter(2, quantity)
				.setParameter(3, price)
				.setParameter(4, fundId)
				.setParameter(5, batch)
				.setParameter(6, comment);
		return q.executeUpdate();
	}

}
