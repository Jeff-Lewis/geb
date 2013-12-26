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
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.ViewDetailedFinrezItem;

/**
 * Текущий финрез
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class ViewDetailedFinrezDaoImpl implements ViewDetailedFinrezDao
{
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewDetailedFinrezItem> executeSelect(Long security, Date begin, Date end, Long client, Long fund) {
		String sql = "{call dbo.mo_WebGet_SelectDetailedFinRez_sp ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql, ViewDetailedFinrezItem.class)
				.setParameter(1, security)
				.setParameter(2, begin)
				.setParameter(3, end)
				.setParameter(4, client)
				.setParameter(5, fund);
		return q.getResultList();
	}

}
