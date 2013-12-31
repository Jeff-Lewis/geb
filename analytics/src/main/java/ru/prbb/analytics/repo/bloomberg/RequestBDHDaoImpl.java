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
 * BDH запрос
 * 
 * @author RBr
 * 
 */
@Service
public class RequestBDHDaoImpl implements RequestBDHDao
{
	@Autowired
	private EntityManager em;

	@Override
	public void execute(String dateStart, String dateEnd, String period, String calendar, String[] security,
			String[] params, Set<String> _currency) {
		// TODO RequestBDHDaoImpl
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findParams() {
		String sql = "select code from multy_request_params_v";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}

}
