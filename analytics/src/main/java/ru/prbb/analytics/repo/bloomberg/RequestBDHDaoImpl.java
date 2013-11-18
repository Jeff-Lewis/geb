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
 * BDH запрос
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class RequestBDHDaoImpl implements RequestBDHDao
{
	@Autowired
	private EntityManager em;

	@Override
	public void execute(String dateStart, String dateEnd, String period, String calendar, String[] security,
			String[] params, Set<String> _currency) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SimpleItem> findParams() {
		// select code from multy_request_params_v
		String sql = "select 0 as id, code as name from multy_request_params_v";
		return em.createQuery(sql, SimpleItem.class).getResultList();
	}

}
