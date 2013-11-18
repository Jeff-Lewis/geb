/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.SecIncItem;

/**
 * Регистрация инструментов
 * 
 * @author RBr
 * 
 */
public interface SecurityIncorporationsDao {

	/**
	 * @return
	 */
	public List<SecIncItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public SecIncItem findById(Long id);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public Long put(SecIncItem value);

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, SecIncItem value);

	/**
	 * @param id
	 */
	public void deleteById(Long id);

}