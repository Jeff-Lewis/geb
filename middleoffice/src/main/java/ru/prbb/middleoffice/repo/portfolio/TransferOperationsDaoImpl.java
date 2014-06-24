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

import ru.prbb.middleoffice.domain.TransferOperationsItem;
import ru.prbb.middleoffice.domain.TransferOperationsListItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Список перекидок
 * 
 * @author RBr
 * 
 */
@Repository
public class TransferOperationsDaoImpl extends BaseDaoImpl implements TransferOperationsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<TransferOperationsListItem> findAll(Date begin, Date end, Long security) {
		String sql = "{call dbo.mo_WebGet_SelectTransferOperations_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql, TransferOperationsListItem.class)
				.setParameter(1, begin)
				.setParameter(2, end)
				.setParameter(3, security);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<TransferOperationsItem> findById(Long id) {
		String sql = "{call dbo.mo_WebGet_SelectTransferDeals_sp ?}";
		Query q = em.createNativeQuery(sql, TransferOperationsItem.class)
				.setParameter(1, id);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteById(Long[] ids) {
		String sql = "{call dbo.mo_WebSet_dTransferDeals_sp ?}";
		for (Long id : ids) {
			Query q = em.createNativeQuery(sql)
					.setParameter(1, id);
			storeSql(sql, q);
			q.executeUpdate();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateById(Long[] ids, String field, String value) {
		String sql = "{call dbo.mo_WebSet_setTransferAttributes_sp ?, ?, ?}";
		for (Long id : ids) {
			Query q = em.createNativeQuery(sql)
					.setParameter(1, id)
					.setParameter(2, field)
					.setParameter(3, value);
			storeSql(sql, q);
			q.executeUpdate();
		}
	}

}
