/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.SecurityIncorporationItem;
import ru.prbb.middleoffice.domain.SecurityIncorporationListItem;

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
	public List<SecurityIncorporationListItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public SecurityIncorporationItem findById(Long id);

	/**
	 * 
	 * @param security
	 * @param country
	 * @param dateBegin
	 * @return
	 */
	public int put(Long security, Long country, Date dateBegin);

	/**
	 * 
	 * @param id
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public int updateById(Long id, Date dateBegin, Date dateEnd);

	/**
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);

}