/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.SecurityCashFlowItem;

/**
 * Загрузка дат погашений
 * 
 * @author RBr
 * 
 */
@Service
public class LoadCashFlowDaoImpl implements LoadCashFlowDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<Map<String, Object>> execute(String[] securities) {
		// TODO LoadCashFlowDaoImpl.execute
		return new ArrayList<Map<String, Object>>();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityCashFlowItem> findAllSecurities() {
		String sql = "select * from dbo.mo_WebGet_bonds_v";
		Query q = em.createNativeQuery(sql, SecurityCashFlowItem.class);
		return q.getResultList();
	}

}
