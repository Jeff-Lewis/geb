/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * @author RBr
 * 
 */
@Service
@Transactional
public class SendingDaoImpl implements SendingDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<Map<String, Object>> execute(String text, String recp, String recm) {
		final List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 1; i < 3; ++i) {
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("mail", "mail" + i);
			map.put("status", i % 2);
			list.add(map);
		}
		return list;
	}

	@Override
	public List<SimpleItem> findComboPhone(String query) {
		String sql = "select value from ncontacts_request_v where type != 'E-mail'";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += " and lower(name) like ?";
			q = em.createNativeQuery(sql)
					.setParameter(1, '%' + query.toLowerCase() + '%');
		}
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<SimpleItem> res = new ArrayList<>(list.size());
		long id = 0;
		for (Object object : list) {
			SimpleItem item = new SimpleItem();
			item.setId(++id);
			item.setName(Utils.toString(object));
			res.add(item);
		}
		return res;
	}

	@Override
	public List<SimpleItem> findComboMail(String query) {
		String sql = "select value from ncontacts_request_v where type = 'E-mail'";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += " and lower(name) like ?";
			q = em.createNativeQuery(sql)
					.setParameter(1, '%' + query.toLowerCase() + '%');
		}
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<SimpleItem> res = new ArrayList<>(list.size());
		long id = 0;
		for (Object object : list) {
			SimpleItem item = new SimpleItem();
			item.setId(++id);
			item.setName(Utils.toString(object));
			res.add(item);
		}
		return res;
	}

	@Override
	public String getAnalitic() {
		String sql = "{call dbo.sms_template_proc}";
		Query q = em.createNativeQuery(sql);
		return Utils.toString(q.getSingleResult());
	}

	@Override
	public String getTrader() {
		String sql = "{call dbo.sms_template_trader}";
		Query q = em.createNativeQuery(sql);
		return Utils.toString(q.getSingleResult());
	}

}
