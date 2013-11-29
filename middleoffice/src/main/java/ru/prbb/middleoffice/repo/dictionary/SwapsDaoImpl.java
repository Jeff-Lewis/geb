/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.SwapItem;

/**
 * Свопы
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class SwapsDaoImpl implements SwapsDao
{
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<SwapItem> findAll() {
		String sql = "execute dbo.mo_WebGet_TrsContracts_sp";
		Query q = em.createNativeQuery(sql, SwapItem.class);
		return q.getResultList();
	}

	@Override
	public SwapItem findById(Long id) {
		// "{call dbo.mo_WebGet_TrsContracts_sp ?}"
		throw new IllegalAccessError("Method not implemented.");
	}

	@Override
	public int put(String swap, Long security) {
		String sql = "execute dbo.mo_WebSet_putTrsContract_sp ?, ?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, swap)
				.setParameter(2, security);
		return q.executeUpdate();
	}

	@Override
	public int updateById(Long id, String swap) {
		String sql = "execute dbo.mo_WebSet_udTrsContracts_sp 'u', ?, ?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, swap);
		return q.executeUpdate();
	}

	@Override
	public int deleteById(Long id) {
		String sql = "execute dbo.mo_WebSet_udTrsContracts_sp 'd', ?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

}
