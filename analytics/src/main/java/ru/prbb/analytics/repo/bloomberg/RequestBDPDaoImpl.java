/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDP запрос
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class RequestBDPDaoImpl implements RequestBDPDao
{
	@Autowired
	private EntityManager em;

	@Override
	public void execute(String[] security, String[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SimpleItem> findParams() {
		String sql = "select code as name from dbo.cur_request_params_v";
		return em.createQuery(sql, SimpleItem.class).getResultList();
	}

}
