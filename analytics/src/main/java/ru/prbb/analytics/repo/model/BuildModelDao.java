/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import ru.prbb.analytics.domain.BuildModelItem;

/**
 * Расчёт модели по компании
 * 
 * @author RBr
 * 
 */
public interface BuildModelDao {

	/**
	 * @param ids
	 * @return
	 */
	List<BuildModelItem> calculateModel(Long[] ids);

	/**
	 * @return
	 */
	List<BuildModelItem> calculateSvod();

}
