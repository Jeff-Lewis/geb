/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import ru.prbb.analytics.domain.ViewModelInfoItem;
import ru.prbb.analytics.domain.ViewModelItem;
import ru.prbb.analytics.domain.ViewModelPriceItem;

/**
 * Просмотр текущей модели
 * 
 * @author RBr
 * 
 */
public interface ViewModelDao {

	/**
	 * @return
	 */
	List<ViewModelItem> findAll();

	/**
	 * @param id_sec
	 * @return
	 */
	ViewModelInfoItem getInfoById(Long id_sec);

	/**
	 * @param id_sec
	 * @return
	 */
	List<ViewModelPriceItem> findPriceById(Long id_sec);

}
