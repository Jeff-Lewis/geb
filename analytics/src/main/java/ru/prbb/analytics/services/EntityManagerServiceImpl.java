package ru.prbb.analytics.services;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.prbb.ArmUserInfo;
import ru.prbb.Utils;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.UserHistory;
import ru.prbb.analytics.repo.UserHistory.AccessAction;

@Repository
public class EntityManagerServiceImpl implements EntityManagerService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManager em;

	@Autowired
	private UserHistory uh;

	private Query setParameters(Query query, Object... params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}
		return query;
	}

	private List<?> getResultList(Query q, String sql) {
		long st = System.currentTimeMillis();
		try {
			List<?> resultList = q.getResultList();
			return resultList;
		} finally {
			long time = System.currentTimeMillis() - st;
			log.debug("==> exec time {} ms: {}", Long.valueOf(time), sql);
		}
	}

	private Object getSingleResult(Query q, String sql) {
		long st = System.currentTimeMillis();
		try {
			Object singleResult = q.getSingleResult();
			return singleResult;
		} finally {
			long time = System.currentTimeMillis() - st;
			log.debug("==> exec time {} ms: {}", Long.valueOf(time), sql);
		}
	}

	@Override
	public <T> T getSelectItem(ArmUserInfo user, Class<T> resultClass, String sql, Object... params) {
		return getResultItem(user, resultClass, sql, params);
	}

	@Override
	public <T> T getResultItem(ArmUserInfo user, Class<T> resultClass, String sql, Object... params) {
		uh.checkAccess(user.getName(), sql, AccessAction.SELECT);

		if (resultClass.getAnnotation(Entity.class) != null) {
			Query q = setParameters(em.createNativeQuery(sql, resultClass), params);
			Object object = getSingleResult(q, sql);

			T result = (T) object;
			return result;
		}

		Query q = setParameters(em.createNativeQuery(sql), params);
		Object object = getSingleResult(q, sql);

		T result = null;
		if (resultClass == Object[].class) {
			result = (T) object;
		} else {
			if (object.getClass() == Object[].class) {
				try {
					Constructor<T> constructor = resultClass.getConstructor(Object[].class);
					result = constructor.newInstance(object);
				} catch (Exception e) {
					log.error("create newInstance in getResultItem", e);
				}
			}  else {
				result = (T) object;
			}
		}
		return result;
	}

	@Override
	public <T> List<T> getSelectList(ArmUserInfo user, Class<T> resultClass, String sql, Object... params) {
		return getResultList(user, resultClass, sql, params);
	}

	@Override
	public <T> List<T> getResultList(ArmUserInfo user, Class<T> resultClass, String sql, Object... params) {
		uh.checkAccess(user.getName(), sql, AccessAction.SELECT);

		if (resultClass.getAnnotation(Entity.class) != null) {
			Query q = setParameters(em.createNativeQuery(sql, resultClass), params);
			List<?> object = getResultList(q, sql);

			List<T> result = (List<T>) object;
			return result;
		}

		Query q = setParameters(em.createNativeQuery(sql), params);
		List<?> objects = getResultList(q, sql);

		if (resultClass == Object[].class) {
			List<T> result = (List<T>) objects;
			return result;
		}

		List<T> result;
		if (resultClass.getSimpleName().contains("Item")) {
			result = new ArrayList<T>(objects.size());
			try {
				Constructor<T> constructor = resultClass.getConstructor(Object[].class);
				for (Object arr : objects) {
					T e = constructor.newInstance(arr);
					result.add(e);
				}
			} catch (Exception e) {
				log.error("create newInstance in getResultList", e);
			}
		} else {
			result = (List<T>) objects;
		}
		return result;
	}

	@Override
	public int executeUpdate(AccessAction action, ArmUserInfo user, String sql, Object... params) {
		uh.checkAccess(user.getName(), sql, action);

		Query q = setParameters(em.createNativeQuery(sql), params);

		storeSql(user, sql, q);

		long st = System.currentTimeMillis();
		try {
			return q.executeUpdate();
		} finally {
			long time = System.currentTimeMillis() - st;
			log.debug("==> exec time {} ms: {}", Long.valueOf(time), sql);
		}
	}

	private void storeSql(ArmUserInfo user, String sql, Query q) {
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

			if (!sql.contains("WebGet"))
				;//uh.putHist(user.getName(), user.getHost(), sql);
		} catch (Exception e) {
			log.error("storeSql", e);
		}
	}

	@Override
	public List<SimpleItem> getComboList(String sql, String where, String query) {
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += ' ' + where;
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		List<SimpleItem> list = q.getResultList();
		return list;
	}

	@Override
	public List<SimpleItem> getComboListName(String sql, String where, String query) {
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += ' ' + where;
			q = em.createNativeQuery(sql)
					.setParameter(1, query.toLowerCase() + '%');
		}
		List<Object> list = q.getResultList();

		List<SimpleItem> res = new ArrayList<SimpleItem>(list.size());
		long id = 1;
		for (Object obj : list) {
			SimpleItem e = new SimpleItem();
			e.setId(id++);
			e.setName(Utils.toString(obj));
			res.add(e);
		}
		return res;
	}
}
