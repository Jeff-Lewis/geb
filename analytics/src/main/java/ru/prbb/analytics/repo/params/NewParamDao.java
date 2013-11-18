/**
 * 
 */
package ru.prbb.analytics.repo.params;

import java.util.Map;

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
	Map<String, String> setup(String code);

	/**
	 * @param blm_id
	 * @param code
	 * @param name
	 */
	void save(String blm_id, String code, String name);

	/**
	 * @param code
	 * @param broker
	 */
	void saveOvr(String code, String broker);

}
