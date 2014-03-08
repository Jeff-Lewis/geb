/**
 * 
 */
package ru.prbb.middleoffice.repo.services.contacts;

import java.util.List;

import ru.prbb.middleoffice.domain.GroupAddressItem;
import ru.prbb.middleoffice.domain.GroupContactsItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Справочник контактов
 * 
 * @author RBr
 */
public interface GroupsDao {

	/**
	 * @return
	 */
	List<SimpleItem> findAll();

	/**
	 * @param id
	 * @return
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
	List<GroupAddressItem> findAllAddresses(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<GroupContactsItem> findAllContacts(Long id);

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
