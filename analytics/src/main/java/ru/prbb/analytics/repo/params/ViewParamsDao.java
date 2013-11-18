/**
 * 
 */
package ru.prbb.analytics.repo.params;

import java.util.List;

import ru.prbb.analytics.domain.ViewParamsItem;

/**
 * Справочник параметров
 * 
 * @author RBr
 * 
 */
public interface ViewParamsDao {

	/**
	 * @return
	 */
	List<ViewParamsItem> show();

	/**
	 * @param blm_id
	 * @return
	 */
	ViewParamsItem getById(String blm_id);

}
