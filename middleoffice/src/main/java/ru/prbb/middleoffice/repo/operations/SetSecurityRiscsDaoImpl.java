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
 * Задать параметры риска
 * 
 * @author RBr
 * 
 */
@Service
public class SetSecurityRiscsDaoImpl implements SetSecurityRiscsDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int execute(Long id, Double riskATH, Double riskAVG, Double stopLoss, String comment) {
		String sql = "{call dbo.mo_WebSet_putSecurityRiscs_sp ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, riskATH)
				.setParameter(3, riskAVG)
				.setParameter(4, stopLoss)
				.setParameter(5, comment);
		return q.executeUpdate();
	}

}
