/**
 * 
 */
package ru.prbb.analytics.repo.params;

import java.util.List;

import ru.prbb.analytics.domain.ViewParamItem;
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
	List<ViewParamsItem> findAll();

	/**
	 * @param blm_id
	 * @return
	 */
	ViewParamItem findById(String blm_id);

}
