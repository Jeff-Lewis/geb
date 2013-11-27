/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.SecuritySubscrItem;
import ru.prbb.analytics.domain.ViewSubscriptionItem;

/**
 * Subscription
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class ViewSubscriptionDaoImpl implements ViewSubscriptionDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<ViewSubscriptionItem> findAll() {
		// {call dbo.output_subscriptions_prc}
		final List<ViewSubscriptionItem> list = new ArrayList<ViewSubscriptionItem>();
		for (long i = 0; i < 10; i++) {
			final ViewSubscriptionItem item = new ViewSubscriptionItem();
			item.setId_subscr(i);
			item.setSubscription_name("SUBSCRIPTION_NAME_" + i);
			item.setSubscription_comment("SUBSCRIPTION_COMMENT_" + i);
			item.setSubscription_status((i % 2) == 1 ? "Running" : "Stopped");
			list.add(item);
		}
		return list;
	}

	@Override
	public List<SecuritySubscrItem> findAllSecurities(Long id) {
		if (null == id) {
			String sql = "{call dbo.output_securities_subscr_prc}";
			return em.createQuery(sql, SecuritySubscrItem.class).getResultList();
		} else {
			String sql = "{call dbo.secs_in_subscription_prc :id}";
			return em.createQuery(sql, SecuritySubscrItem.class).setParameter(1, id).getResultList();
		}
	}

	@Override
	public void put(String name, String comment) {
		// TODO Auto-generated method stub

	}

	@Override
	public ViewSubscriptionItem get(Long id) {
		final ViewSubscriptionItem item = new ViewSubscriptionItem();
		item.setId_subscr(id);
		item.setSubscription_name("SUBSCRIPTION_NAME_" + id);
		item.setSubscription_comment("SUBSCRIPTION_COMMENT_" + id);
		item.setSubscription_status((id % 2) == 1 ? "Running" : "Stopped");
		return item;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop(Long id) {
		// TODO Auto-generated method stub

	}

}
