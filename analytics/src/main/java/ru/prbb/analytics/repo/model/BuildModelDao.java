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
	 * dbo.build_model_proc_p
	 * 
	 * @param ids
	 * @return
	 */
	List<BuildModelItem> calculateModel(Long... ids);

	/**
	 * dbo.build_model_proc
	 * 
	 * @return
	 */
	List<BuildModelItem> calculateSvod();

}
