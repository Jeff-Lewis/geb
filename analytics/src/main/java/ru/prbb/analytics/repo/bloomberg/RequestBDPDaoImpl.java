/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
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
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<SimpleItem> res = new ArrayList<>();
		long id = 0;
		for (Object object : list) {
			SimpleItem item = new SimpleItem();
			item.setId(++id);
			item.setName(Utils.toString(object));
			res.add(item);
		}
		return res;
	}

}
