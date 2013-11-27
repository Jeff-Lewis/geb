/**
 * 
 */
package ru.prbb.analytics.repo.params;

import ru.prbb.analytics.domain.NewParamItem;

/**
 * Ввод нового параметра<br>
 * Ввод нового параметра blm_datasource_ovr
 * 
 * @author RBr
 * 
 */
public interface NewParamDao {

	/**
	 * @param code
	 * @return
	 */
	NewParamItem setup(String code);

	/**
	 * @param blm_id
	 * @param code
	 * @param name
	 * @return
	 */
	int save(String blm_id, String code, String name);

	/**
	 * @param code
	 * @param broker
	 * @return
	 */
	int saveOvr(String code, String broker);

}
