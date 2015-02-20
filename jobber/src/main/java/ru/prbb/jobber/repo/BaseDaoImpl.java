/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Parameter;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ruslan
 */
public abstract class BaseDaoImpl {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserHistory uh;

	protected String showSql(String sql, Query q) {
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
			return msg;
		} catch (Exception e) {
			log.error("storeSql", e);
		}
		return sql;
	}

	protected void storeSql(String sql, Query q) {
		String msg = showSql(sql, q);

		if (!sql.contains("WebGet"))
			uh.putHist(msg);
	}

	@SuppressWarnings("rawtypes")
	protected List getResultList(Query q, String sql) {
		long st = System.currentTimeMillis();
		try {
			return q.getResultList();
		} finally {
			long time = System.currentTimeMillis() - st;
			log.debug("==> exec time {} ms: {}", Long.valueOf(time), sql);
		}
	}

	protected Object getSingleResult(Query q, String sql) {
		long st = System.currentTimeMillis();
		try {
			return q.getSingleResult();
		} finally {
			long time = System.currentTimeMillis() - st;
			log.debug("==> exec time {} ms: {}", Long.valueOf(time), sql);
		}
	}

	protected int executeUpdate(Query q, String sql) {
		long st = System.currentTimeMillis();
		try {
			return q.executeUpdate();
		} finally {
			long time = System.currentTimeMillis() - st;
			log.debug("==> exec time {} ms: {}", Long.valueOf(time), sql);
		}
	}

}
