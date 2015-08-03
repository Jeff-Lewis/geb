package ru.prbb.analytics.services;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.UserHistory.AccessAction;

/**
 * Сервис работы с БД
 * 
 * @author ruslan
 */
public interface EntityManagerService {

	/**
	 * Execute a SELECT query that returns a single untyped result.
	 *
	 * @return the result
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	<T> T getSelectItem(ArmUserInfo user, Class<T> resultClass, String sql, Object... params);

	/**
	 * Execute a query that returns a single untyped result.
	 *
	 * @return the result
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	<T> T getResultItem(ArmUserInfo user, Class<T> resultClass, String sql, Object... params);

	/**
	 * Execute a SELECT query and return the query results
	 * as an List.
	 *
	 * @return a list of the results
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	<T> List<T> getSelectList(ArmUserInfo user, Class<T> resultClass, String sql, Object... params);

	/**
	 * Execute a query and return the query results
	 * as an untyped List.
	 *
	 * @return a list of the results
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	<T> List<T> getResultList(ArmUserInfo user, Class<T> resultClass, String sql, Object... params);

	/**
	 * Execute an update or delete statement.
	 *
	 * @return the number of entities updated or deleted
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	int executeUpdate(AccessAction action, ArmUserInfo user, String sql, Object... params);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	List<SimpleItem> getComboList(String sql, String where, String query);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	List<SimpleItem> getComboListName(String sql, String where, String query);

}
