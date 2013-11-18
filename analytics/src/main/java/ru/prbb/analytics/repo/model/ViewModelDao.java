/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import ru.prbb.analytics.domain.ViewModelItem;

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
	List<ViewModelItem> current();

	/**
	 * @param id_sec
	 * @return
	 */
	ViewModelItem getById(Long id_sec);

}
