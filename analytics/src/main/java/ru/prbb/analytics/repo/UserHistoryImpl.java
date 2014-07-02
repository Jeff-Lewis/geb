package ru.prbb.analytics.repo;

import java.sql.Timestamp;
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

import ru.prbb.analytics.domain.LogUserActionItem;

/**
 * @author RBr
 */
@Repository
public class UserHistoryImpl implements UserHistory {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<LogUserActionItem> getHistory(Timestamp date_b, Timestamp date_e) {
		String sql = "{call dbo.WebGet_SelectHist_sp ?, ?}";
		Query q = em.createNativeQuery(sql, LogUserActionItem.class)
				.setParameter(1, date_b)
				.setParameter(2, date_e);
		storeSql(sql, q, false);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public int putHist(String command) {
		String sql = "{call dbo.WebSet_putHist_sp ?, ?, ?}";
		String user_login = "admin1";
		String user_ip = "localhost";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, user_login)
				.setParameter(2, user_ip)
				.setParameter(3, command);
		//storeSql(sql, q, false);
		return q.executeUpdate();
	}

	protected void storeSql(String sql, Query q, boolean isPutHist) {
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

}