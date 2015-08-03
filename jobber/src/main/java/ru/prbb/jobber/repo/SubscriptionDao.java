package ru.prbb.jobber.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SubscriptionItem;
import ru.prbb.jobber.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class SubscriptionDao
{

	@Autowired
	private EntityManagerService ems;

	public List<SubscriptionItem> getSubscriptions() {
		String sql = "{call dbo.output_subscriptions_prc}";
		return ems.getSelectList(SubscriptionItem.class, sql);
	}

	public List<SecurityItem> getSubscriptionSecurities(Long id) {
		String sql = "{call dbo.secs_in_subscription_prc ?}";
		return ems.getSelectList(SecurityItem.class, sql, id);
	}

	public int subsUpdate(String security_code, String last_price, String last_chng) {
		String sql = "{call dbo.upd_sect_subs_proc ?, ?, ?}";
		return ems.executeUpdate(sql, security_code, last_price, last_chng);
	}

}
