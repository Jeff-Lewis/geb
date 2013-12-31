/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDP с override
 * 
 * @author RBr
 * 
 */
@Service
public class RequestBDPovrDaoImpl implements RequestBDPovrDao
{
	@Autowired
	private EntityManager em;

	@Override
	public void execute(String[] security, String[] params, String over, String period) {
		// TODO RequestBDPovrDaoImpl
	}

	@Override
	public void execute(String[] security, String[] params, String over, Set<String> _currency) {
		// TODO RequestBDPovrDaoImpl
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findParams() {
		String sql = "select code from dbo.fundamentals_params_v";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> comboFilterOverride(String query) {
		String sql = "select code from dbo.blm_datasource_ovr";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}
}
