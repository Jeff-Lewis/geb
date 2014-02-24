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
import ru.prbb.bloomberg.BloombergServices;

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

	@Autowired
	private BloombergServices bs;

	@Override
	public void execute(String dateStart, String dateEnd, String period, String calendar,
			String[] security, String[] params, Set<String> _currency) {
		bs.executeBdhRequest("BDH запрос", dateStart, dateEnd, period, calendar,
				_currency, security, params);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findParams() {
		String sql = "select code from multy_request_params_v";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}

}
