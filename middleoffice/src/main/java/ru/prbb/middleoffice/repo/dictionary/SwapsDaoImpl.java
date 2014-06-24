/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.SwapItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Свопы
 * 
 * @author RBr
 * 
 */
@Repository
public class SwapsDaoImpl extends BaseDaoImpl implements SwapsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SwapItem> findAll() {
		String sql = "{call dbo.mo_WebGet_TrsContracts_sp}";
		Query q = em.createNativeQuery(sql, SwapItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public SwapItem findById(Long id) {
		// "{call dbo.mo_WebGet_TrsContracts_sp ?}"
		throw new IllegalAccessError("Method not implemented.");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String swap, Long security) {
		String sql = "{call dbo.mo_WebSet_putTrsContract_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, swap)
				.setParameter(2, security);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, String swap) {
		String sql = "{call dbo.mo_WebSet_udTrsContracts_sp 'u', ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, swap);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_udTrsContracts_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return q.executeUpdate();
	}

}
