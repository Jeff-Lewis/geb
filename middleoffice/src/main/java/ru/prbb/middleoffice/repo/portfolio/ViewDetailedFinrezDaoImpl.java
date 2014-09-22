/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.ViewDetailedFinrezItem;

/**
 * Текущий финрез
 * 
 * @author RBr
 * 
 */
@Repository
public class ViewDetailedFinrezDaoImpl implements ViewDetailedFinrezDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewDetailedFinrezItem> executeSelect(Long security, Date begin, Date end, Long client, Long fund, Long initiator) {
		String sql = "{call dbo.mo_WebGet_SelectDetailedFinRez_sp ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql, ViewDetailedFinrezItem.class)
				.setParameter(1, security)
				.setParameter(2, begin)
				.setParameter(3, end)
				.setParameter(4, client)
				.setParameter(5, fund)
				.setParameter(6, initiator);
		return q.getResultList();
	}

}
