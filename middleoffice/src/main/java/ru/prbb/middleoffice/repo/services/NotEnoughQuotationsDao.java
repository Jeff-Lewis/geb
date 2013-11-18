/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import ru.prbb.middleoffice.domain.NotEnoughQuotationsItem;

/**
 * Не хватает котировок
 * 
 * @author RBr
 * 
 */
public interface NotEnoughQuotationsDao {

	/**
	 * @return
	 */
	List<NotEnoughQuotationsItem> show();

}
