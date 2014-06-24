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

import ru.prbb.analytics.domain.SecuritySubscrItem;
import ru.prbb.analytics.domain.ViewSubscriptionItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * Subscription
 * 
 * @author RBr
 * 
 */
@Service
public class ViewSubscriptionDaoImpl extends BaseDaoImpl implements ViewSubscriptionDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewSubscriptionItem> findAll() {
		String sql = "{call dbo.output_subscriptions_prc}";
		Query q = em.createNativeQuery(sql, ViewSubscriptionItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public ViewSubscriptionItem findById(Long id) {
		List<ViewSubscriptionItem> list = findAll();
		for (ViewSubscriptionItem item : list) {
			if (id.equals(item.getId())) {
				return item;
			}
		}

		ViewSubscriptionItem item = new ViewSubscriptionItem();
		item.setId(id);
		item.setName(id.toString());
		item.setComment("findById:" + id);
		return item;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String name, String comment) {
		String sql = "{call dbo.create_subscription_proc ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name)
				.setParameter(2, comment);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.remove_subscription_proc ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SecuritySubscrItem> findAllSecurities() {
		String sql = "{call dbo.output_securities_subscr_prc}";
		Query q = em.createNativeQuery(sql, SecuritySubscrItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SecuritySubscrItem> findAllSecurities(Long id) {
		String sql = "{call dbo.secs_in_subscription_prc ?}";
		Query q = em.createNativeQuery(sql, SecuritySubscrItem.class)
				.setParameter(1, id);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int[] staffAdd(Long id_subscr, Long[] ids) {
		String sql = "{call dbo.subscribe_security_proc ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(2, id_subscr);
		int i = 0;
		int[] res = new int[ids.length];
		for (Long id_sec : ids) {
			q.setParameter(1, id_sec);
			storeSql(sql, q);
			res[i++] = q.executeUpdate();
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int[] staffDel(Long id_subscr, Long[] ids) {
		String sql = "{call dbo.unsubscribe_security_proc ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(2, id_subscr);
		int i = 0;
		int[] res = new int[ids.length];
		for (Long id_sec : ids) {
			q.setParameter(1, id_sec);
			storeSql(sql, q);
			res[i++] = q.executeUpdate();
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int start(Long id) {
		String sql = "{call dbo.run_subscription_proc ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int stop(Long id) {
		String sql = "{call dbo.stop_subscription_proc ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return q.executeUpdate();
	}

}
