/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.SecuritySubscrItem;
import ru.prbb.jobber.domain.ViewSubscriptionItem;
import ru.prbb.jobber.services.EntityManagerService;

/**
 * Subscription
 * 
 * @author RBr
 */
@Service
public class ViewSubscriptionDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ViewSubscriptionItem> findAll() {
		String sql = "{call dbo.output_subscriptions_prc}";
		return ems.getSelectList(ViewSubscriptionItem.class, sql);
	}

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

	public int put(String name, String comment) {
		String sql = "{call dbo.create_subscription_proc ?, ?}";
		return ems.executeUpdate(sql, name, comment);
	}

	public int deleteById(Long id) {
		String sql = "{call dbo.remove_subscription_proc ?}";
		return ems.executeUpdate(sql, id);
	}

	public List<SecuritySubscrItem> findAllSecurities() {
		String sql = "{call dbo.output_securities_subscr_prc}";
		return ems.getSelectList(SecuritySubscrItem.class, sql);
	}

	public List<SecuritySubscrItem> findAllSecurities(Long id) {
		String sql = "{call dbo.secs_in_subscription_prc ?}";
		return ems.getSelectList(SecuritySubscrItem.class, sql, id);
	}

	public int[] staffAdd(Long id_subscr, Long[] ids) {
		String sql = "{call dbo.subscribe_security_proc ?, ?}";
		int[] res = new int[ids.length];
		for (int i = 0; i < ids.length; i++) {
			res[i] = ems.executeUpdate(sql, ids[i], id_subscr);
		}
		return res;
	}

	public int[] staffDel(Long id_subscr, Long[] ids) {
		String sql = "{call dbo.unsubscribe_security_proc ?, ?}";
		int[] res = new int[ids.length];
		for (int i = 0; i < ids.length; i++) {
			res[i] = ems.executeUpdate(sql, ids[i], id_subscr);
		}
		return res;
	}

	public int start(Long id) {
		String sql = "{call dbo.run_subscription_proc ?}";
		return ems.executeUpdate(sql, id);
	}

	public int stop(Long id) {
		String sql = "{call dbo.stop_subscription_proc ?}";
		return ems.executeUpdate(sql, id);
	}

}
