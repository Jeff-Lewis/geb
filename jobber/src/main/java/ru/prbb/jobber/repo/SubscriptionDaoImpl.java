package ru.prbb.jobber.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
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

	private void showSql(String sql, Query q) {
		try {
			StringBuilder res = new StringBuilder(sql);
			if (!q.getParameters().isEmpty()) {
				List<Parameter<?>> ps = new ArrayList<>(q.getParameters());
				Collections.sort(ps, new Comparator<Parameter<?>>() {

					@Override
					public int compare(Parameter<?> o1, Parameter<?> o2) {
						return o1.getPosition().compareTo(o2.getPosition());
					}
				});
				res.append('(');
				for (Parameter<?> p : ps) {
					try {
						Object pv = q.getParameterValue(p);
						res.append(pv);
						res.append(',');
					} catch (IllegalStateException e) {
						res.append("NULL,");
					}
				}
				res.setCharAt(res.length() - 1, ')');
			}

			String msg = res.toString();
			log.info(msg);
		} catch (Exception e) {
			log.error("storeSql", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SubscriptionItem> getSubscriptions() {
		String sql = "{call dbo.output_subscriptions_prc}";
		Query q = em.createNativeQuery(sql, SubscriptionItem.class);
		//showSql(sql, q);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SecurityItem> subsGetSecs(Long id) {
		String sql = "{call dbo.secs_in_subscription_prc ?}";
		Query q = em.createNativeQuery(sql, SecurityItem.class)
				.setParameter(1, id);
		//showSql(sql, q);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int[] subsUpdate(List<String[]> data) {
		String sql = "{call dbo.upd_sect_subs_proc ?, ?, ?}";
		Query q = em.createNativeQuery(sql);

		int ri = 0;
		int[] res = new int[data.size()];
		for (String[] arr : data) {
			try {
				String security_code = arr[0];
				String last_price = arr[1];
				String last_chng = arr[2];
				q.setParameter(1, security_code);
				q.setParameter(2, last_price);
				q.setParameter(3, last_chng);
				//showSql(sql, q);
				res[ri++] = q.executeUpdate();
			} catch (Exception e) {
				log.error("Store subscription data", e);
			}
		}

		return res;
	}

}
