/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import ru.prbb.analytics.domain.CompanyAllItem;
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
	 * @return
	 */
	int put(String name);

	/**
	 * @param id
	 * @param name
	 * @return
	 */
	int renameById(Long id, String name);

	/**
	 * @param id
	 * @return
	 */
	int deleteById(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<CompanyAllItem> findStaff(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<CompanyStaffItem> findStaffReport(Long id);

	/**
	 * @param report_id
	 * @param ids
	 * @return
	 */
	int[] putStaff(Long report_id, Long[] ids);

	/**
	 * @param report_id
	 * @param ids
	 * @return
	 */
	int[] deleteStaff(Long report_id, Long[] ids);

}
