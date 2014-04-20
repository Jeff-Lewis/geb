package ru.prbb.analytics.repo.users;

import java.util.List;

import ru.prbb.analytics.domain.DictGroupItem;
import ru.prbb.analytics.domain.DictGroupsObjectItem;
import ru.prbb.analytics.domain.DictGroupsPermisionItem;
import ru.prbb.analytics.domain.DictGroupsUserItem;

/**
 * Справочник пользователей - Пользователи
 * 
 * @author BrihlyaevRA
 */
public interface DictGroupsDao {

	/**
	 * @return
	 */
	List<DictGroupItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	DictGroupItem findById(Long id);

	/**
	 * @param name
	 * @param comment
	 * @return
	 */
	public int put(String name, String comment);

	/**
	 * @param id
	 * @param name
	 * @param comment
	 * @return
	 */
	public int updateById(Long id, String name, String comment);

	/**
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);

	/**
	 * @param idGroup
	 * @return
	 */
	List<DictGroupsUserItem> findAllUsers(Long idGroup);

	/**
	 * @param idGroup
	 * @return
	 */
	List<DictGroupsUserItem> findAllStaff(Long idGroup);

	/**
	 * @param idGroup
	 * @param ids
	 */
	void addStuff(Long idGroup, Long[] ids);

	/**
	 * @param idGroup
	 * @param ids
	 */
	void delStuff(Long idGroup, Long[] ids);

	/**
	 * 
	 * @param idGroup
	 * @return
	 */
	List<DictGroupsObjectItem> findAllObjects(Long idGroup);

	/**
	 * @param idGroup
	 * @return
	 */
	List<DictGroupsPermisionItem> findAllPermission(Long idGroup);

	/**
	 * @param idGroup
	 * @param objects
	 * @param ids
	 */
	void addPermission(Long idGroup, Long[] objects, Long[] ids);

	/**
	 * @param idGroup
	 * @param ids
	 */
	void delPermission(Long idGroup, Long[] ids);
}
