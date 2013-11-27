/**
 * 
 */
package ru.prbb.middleoffice.repo.services.contacts;

import java.util.List;

import ru.prbb.middleoffice.domain.GroupItem;
import ru.prbb.middleoffice.domain.SimpleItem;

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
	 */
	void put(String name);

	/**
	 * @param id
	 * @param name
	 */
	void updateById(Long id, String name);

	/**
	 * @param id
	 */
	void deleteById(Long id);

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
	 */
	void putStaff(Long id, Long[] cids);

	/**
	 * @param id
	 * @param cids
	 */
	void deleteStaff(Long id, Long[] cids);

}
