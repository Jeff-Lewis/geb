/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import ru.prbb.analytics.domain.CompanyStaffItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * Компании и отчёты
 * 
 * @author RBr
 * 
 */
public interface CompanyReportsDao {

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
	 */
	void put(String name);

	/**
	 * @param id
	 * @param name
	 */
	void renameById(Long id, String name);

	/**
	 * @param id
	 */
	void deleteById(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<CompanyStaffItem> findStaff(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<CompanyStaffItem> findStaffReport(Long id);

	/**
	 * @param id
	 * @param ids
	 */
	void putStaff(Long id, Long[] ids);

	/**
	 * @param id
	 * @param ids
	 */
	void deleteStaff(Long id, Long[] ids);

}
