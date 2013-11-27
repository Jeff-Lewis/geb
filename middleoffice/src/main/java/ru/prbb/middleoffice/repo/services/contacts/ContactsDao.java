/**
 * 
 */
package ru.prbb.middleoffice.repo.services.contacts;

import java.util.List;

import ru.prbb.middleoffice.domain.ContactStaffItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Справочник контактов
 * 
 * @author RBr
 * 
 */
public interface ContactsDao {

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
	List<ContactStaffItem> findAllStaff(Long id);

	/**
	 * @param id
	 * @param name
	 * @param type
	 */
	void putStaff(Long id, String name, Integer type);

	/**
	 * @param id
	 * @param cid
	 * @param name
	 */
	void updateByIdStaff(Long id, Long cid, String name);

	/**
	 * @param id
	 * @param cid
	 */
	void deleteByIdStaff(Long id, Long cid);

}
