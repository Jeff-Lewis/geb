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

import ru.prbb.middleoffice.domain.TransferOperationsItem;

/**
 * Список перекидок
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class TransferOperationsDaoImpl implements TransferOperationsDao
{
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferOperationsItem> findAll(Date begin, Date end, Long security) {
		String sql = "{call dbo.mo_WebGet_SelectTransferOperations_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql, TransferOperationsItem.class)
				.setParameter(1, begin)
				.setParameter(2, end)
				.setParameter(3, security);
		return q.getResultList();
	}

	@Override
	public void deleteById(Long[] ids) {
		String sql = "{call dbo.mo_WebSet_dTransferDeals_sp ?}";
		for (Long id : ids) {
			Query q = em.createNativeQuery(sql)
					.setParameter(1, id);
			// q.executeUpdate();
		}
	}

	@Override
	public void updateById(Long[] ids, String field, String value) {
		String sql = "{call dbo.mo_WebSet_setTransferAttributes_sp ?, ?, ?}";
		for (Long id : ids) {
			Query q = em.createNativeQuery(sql)
					.setParameter(1, id)
					.setParameter(2, field)
					.setParameter(3, value);
			// q.executeUpdate();
		}
	}

}
