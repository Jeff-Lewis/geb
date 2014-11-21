/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
public class LogDaoImpl extends BaseDaoImpl implements LogDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<LogContactItem> getLogContacts(String start, String stop) {
		String sql = "{call dbo.check_ncontacts_change_log ?, ?}";
		Query q = em.createNativeQuery(sql, LogContactItem.class)
				.setParameter(1, start)
				.setParameter(2, stop);
		storeSql(sql, q);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<LogMessagesItem> getLogMessages(String type, String start, String stop) {
		String sql = "{call dbo.check_sms_and_email_reciver ?, ?, ?}";
		Query q = em.createNativeQuery(sql, LogMessagesItem.class)
				.setParameter(1, type)
				.setParameter(2, start)
				.setParameter(3, stop);
		storeSql(sql, q);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SubscriptionItem> getLogSubscription() {
		String sql = "{call dbo.subscription_data_v_proc}";
		Query q = em.createNativeQuery(sql);
		storeSql(sql, q);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<SubscriptionItem> res = new ArrayList<>(list.size());
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
