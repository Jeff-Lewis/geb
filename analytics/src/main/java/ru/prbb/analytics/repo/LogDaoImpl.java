/**
 * 
 */
package ru.prbb.analytics.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.LogContactItem;
import ru.prbb.analytics.domain.LogMessagesItem;

/**
 * Журнал отправки сообщений<br>
 * Журнал изменений справочника контактов<br>
 * Журнал подписки
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class LogDaoImpl implements LogDao
{
	@Autowired
	private EntityManager em;

	/**
	 * dbo.check_ncontacts_change_log
	 * 
	 * @param start
	 *            varchar (20)
	 * @param stop
	 *            varchar (20)
	 * 
	 */
	@Override
	public List<LogContactItem> getLogContacts(String start, String stop) {
		String sql = "{call dbo.check_ncontacts_change_log :start, :stop}";
		TypedQuery<LogContactItem> q = em.createQuery(sql, LogContactItem.class);
		return q.setParameter(1, start).setParameter(2, stop).getResultList();
	}

	/**
	 * dbo.check_sms_and_email_reciver
	 * 
	 * @param type
	 *            varchar(10),
	 * @param start
	 *            varchar (20),
	 * @param stop
	 *            varchar (20)
	 * 
	 */
	@Override
	public List<LogMessagesItem> getLogMessages(String type, String start, String stop) {
		String sql = "{call dbo.check_sms_and_email_reciver :type, :start, :stop}";
		TypedQuery<LogMessagesItem> q = em.createQuery(sql, LogMessagesItem.class);
		return q.setParameter(1, type).setParameter(2, start).setParameter(3, stop).getResultList();
	}

	@Override
	public List<Map<String, Object>> getLogSubscription() {
		// {call subscription_data_v_proc}
		final ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			list.add(map);
			map.put("security_type", "SECURITY_TYPE_" + (i + 1));
		}
		return list;
	}

}
