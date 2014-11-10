package ru.prbb.jobber.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SubscriptionItem;

/**
 * 
 * @author RBr
 * 
 */
@Repository
public class SubscriptionDaoImpl implements SubscriptionDao
{
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SubscriptionItem> getSubscriptions() {
		String sql = "{call dbo.output_subscriptions_prc}";
		Query q = em.createNativeQuery(sql, SubscriptionItem.class);
		showSql(sql, q);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SecurityItem> subsGetSecs(Long id) {
		String sql = "{call dbo.secs_in_subscription_prc ?}";
		Query q = em.createNativeQuery(sql, SecurityItem.class)
				.setParameter(1, id);
		showSql(sql, q);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int subsUpdate(String security_code, String last_price, String last_chng) {
		String sql = "{call dbo.upd_sect_subs_proc ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, security_code)
				.setParameter(2, last_price)
				.setParameter(3, last_chng);
		showSql(sql, q);
		return 0;// TODO q.executeUpdate();
	}

	/**
	 * @param sql
	 * @param q
	 */
	private void showSql(String sql, Query q) {
		System.out.println(sql);
	}
}
