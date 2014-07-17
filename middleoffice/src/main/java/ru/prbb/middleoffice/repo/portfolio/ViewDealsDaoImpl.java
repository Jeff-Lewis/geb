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

import ru.prbb.middleoffice.domain.ViewDealsItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Список сделок
 * 
 * @author RBr
 * 
 */
@Repository
public class ViewDealsDaoImpl extends BaseDaoImpl implements ViewDealsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewDealsItem> findAll(Date begin, Date end, Long security, Long client, Long funds, Long initiator) {
		String sql = "{call dbo.mo_WebGet_SelectDeals_sp ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql, ViewDealsItem.class)
				.setParameter(1, begin)
				.setParameter(2, end)
				.setParameter(3, security)
				.setParameter(4, client)
				.setParameter(5, funds)
				.setParameter(6, initiator);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteById(Long[] deals) {
		String sql = "{call dbo.mo_WebSet_dDeals_sp ?}";
		for (Long deal : deals) {
			Query q = em.createNativeQuery(sql)
					.setParameter(1, deal);
			storeSql(sql, q);
			q.executeUpdate();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateById(Long[] deals, String field, String value) {
		String sql = "{call dbo.mo_WebSet_setDealsAttr_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(2, field)
				.setParameter(3, value);
		for (Long deal : deals) {
			q.setParameter(1, deal);
			storeSql(sql, q);
			q.executeUpdate();
		}
	}

}
