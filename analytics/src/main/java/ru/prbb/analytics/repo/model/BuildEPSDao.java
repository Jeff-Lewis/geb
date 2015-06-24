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
	 * @param id
	 * @return
	 */
	BuildEPSItem calculate(Long id);

	/**
	 * @return
	 */
	List<BuildEPSItem> calculate();

}
