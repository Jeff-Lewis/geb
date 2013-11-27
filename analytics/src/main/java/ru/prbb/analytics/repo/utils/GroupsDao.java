/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.List;

import ru.prbb.analytics.domain.GroupItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * Справочник контактов
 * 
 * @author RBr
 * 
 */
public interface GroupsDao {

	/**
	 * @return
	 */
	List<SimpleItem> findAll();

	/**
	 * @param id
	 */
	SimpleItem findById(Long id);

	/**
	 * @param name
	 * @return
	 */
	int put(String name);

	/**
	 * @param id
	 * @param name
	 * @return
	 */
	int updateById(Long id, String name);

	/**
	 * @param id
	 * @return
	 */
	int deleteById(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<GroupItem> findAllAddresses(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<GroupItem> getContacts(Long id);

	/**
	 * @param id
	 * @param cids
	 * @return
	 */
	int[] putStaff(Long id, Long[] cids);

	/**
	 * @param id
	 * @param cids
	 * @return
	 */
	int[] deleteStaff(Long id, Long[] cids);

}