package ru.prbb.analytics.repo.users;

import java.util.List;

import ru.prbb.analytics.domain.DictUserItem;
import ru.prbb.analytics.domain.DictUsersInfoItem;

/**
 * Справочник пользователей - Пользователи
 * 
 * @author BrihlyaevRA
 */
public interface DictUsersDao {

	/**
	 * @return
	 */
	List<DictUserItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	DictUserItem findById(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<DictUsersInfoItem> findInfoById(Long id);

	/**
	 * @param login
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	public int put(String login, String password, String name, String email);

	/**
	 * @param id
	 * @param login
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	public int updateById(Long id, String login, String password, String name, String email);

	/**
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);

}
