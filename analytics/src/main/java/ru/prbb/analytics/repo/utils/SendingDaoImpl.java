/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

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
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < 3; ++i) {
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", "status" + i);
			map.put("mail", "mail" + i);
			list.add(map);
		}
		return list;
	}

	@Override
	public List<SimpleItem> findComboPhone(String query) {
		String sql = "select 0 as id, value as name from ncontacts_request_v where type != 'E-mail'";
		if (Utils.isEmpty(query)) {
			return em.createQuery(sql, SimpleItem.class).getResultList();
		} else {
			sql += " and lower(name) like :query";
			query = '%' + query.toLowerCase() + '%';
			return em.createQuery(sql, SimpleItem.class).setParameter(1, query).getResultList();
		}
	}

	@Override
	public List<SimpleItem> findComboMail(String query) {
		String sql = "select 0 as id, value as name from ncontacts_request_v where type = 'E-mail'";
		if (Utils.isEmpty(query)) {
			return em.createQuery(sql, SimpleItem.class).getResultList();
		} else {
			sql += " and lower(name) like :query";
			query = '%' + query.toLowerCase() + '%';
			return em.createQuery(sql, SimpleItem.class).setParameter(1, query).getResultList();
		}
	}

	@Override
	public String getAnalitic() {
		// {call dbo.sms_template_proc}
		return "{call dbo.sms_template_proc}";
	}

	@Override
	public String getTrader() {
		// {call dbo.sms_template_trader}
		return "{call dbo.sms_template_trader}";
	}

}
