/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;

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
 * BDS запрос
 * 
 * @author RBr
 * 
 */
@Service
public class RequestBDSDaoImpl implements RequestBDSDao
{
	@Autowired
	private EntityManager em;
	@Autowired
	private BloombergServices bs;

	@Override
	public void execute(String[] security, String[] params) {
		bs.executeBdsRequest("BDS запрос", security, params);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findParams() {
		String sql = "select code from bulk_request_params_v";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}

}
