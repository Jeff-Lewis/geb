/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import ru.prbb.analytics.domain.BuildEPSItem;

/**
 * Расчёт EPS по компании
 * 
 * @author RBr
 * 
 */
public interface BuildEPSDao {

	/**
	 * @param ids
	 * @return
	 */
	List<BuildEPSItem> calculate(Long[] ids);

	/**
	 * @return
	 */
	List<BuildEPSItem> calculate();

}
