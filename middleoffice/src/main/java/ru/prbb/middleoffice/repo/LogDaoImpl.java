/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.LogContactItem;
import ru.prbb.middleoffice.domain.LogMessagesItem;
import ru.prbb.middleoffice.domain.SubscriptionItem;

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
	public List<SubscriptionItem> getLogSubscription() {
		String sql = "execute dbo.subscription_data_v_proc";
		List list = em.createNativeQuery(sql).getResultList();
		List<SubscriptionItem> res = new ArrayList<SubscriptionItem>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			SubscriptionItem item = new SubscriptionItem();
			item.setSecurity_type(Utils.toString(arr[0]));
			item.setTicker(Utils.toString(arr[1]));
			item.setName(Utils.toString(arr[2]));
			item.setLast(Utils.toDouble(arr[3]));
			item.setLastchange(Utils.toDouble(arr[4]));
			item.setLastchangetime(Utils.toString(arr[5])); // Timestamp
			item.setAttention(Utils.toString(arr[6]));
			res.add(item);
		}
		return res;
	}

}
