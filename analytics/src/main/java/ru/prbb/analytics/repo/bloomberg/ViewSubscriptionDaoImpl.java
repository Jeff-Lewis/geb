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
			final ViewSubscriptionItem map = new ViewSubscriptionItem();
			map.setId_subscr(i);
			map.setSubscription_name("SUBSCRIPTION_NAME_" + i);
			map.setSubscription_comment("SUBSCRIPTION_COMMENT_" + i);
			map.setSubscription_status("SUBSCRIPTION_STATUS_" + ((i % 2) == 1 ? "Running" : "Stopped"));
			list.add(map);
		}
		return list;
	}

	@Override
	public void put(String name, String comment) {
		// TODO Auto-generated method stub

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
