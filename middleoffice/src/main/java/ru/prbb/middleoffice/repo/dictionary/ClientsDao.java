/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.ClientsItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Клиенты
 * 
 * @author RBr
 * 
 */
public interface ClientsDao {

	/**
	 * @return
	 */
	public List<ClientsItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public ClientsItem findById(Long id);

	/**
	 * 
	 * @param name
	 * @param comment
	 * @param countryId
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public int put(String name, String comment, Long countryId, Date dateBegin, Date dateEnd);

	/**
	 * 
	 * @param id
	 * @param name
	 * @param comment
	 * @param countryId
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public int updateById(Long id, String name, String comment, Long countryId, Date dateBegin, Date dateEnd);

	/**
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findCombo(String query);

}