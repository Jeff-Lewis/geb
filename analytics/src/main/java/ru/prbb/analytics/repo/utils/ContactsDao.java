/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.List;

import ru.prbb.analytics.domain.ContactStaffItem;
import ru.prbb.analytics.domain.SimpleItem;

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
	List<ContactStaffItem> findAllStaff(Long id);

	/**
	 * @param id
	 * @param name
	 * @param type
	 * @return
	 */
	int putStaff(Long id, String name, Integer type);

	/**
	 * @param id
	 * @param cid
	 * @param name
	 * @return
	 */
	int updateByIdStaff(Long id, Long cid, String name);

	/**
	 * @param id
	 * @param cid
	 * @return
	 */
	int deleteByIdStaff(Long id, Long cid);

}
