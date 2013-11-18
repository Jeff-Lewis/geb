/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDP с override
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class RequestBDPovrDaoImpl implements RequestBDPovrDao
{
	@Autowired
	private EntityManager em;

	@Override
	public void execute(String[] security, String[] params, String over, String period) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(String[] security, String[] params, String over, Set<String> _currency) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SimpleItem> findParams() {
		String sql = "select code as name from dbo.fundamentals_params_v";
		return em.createQuery(sql, SimpleItem.class).getResultList();
	}

}
