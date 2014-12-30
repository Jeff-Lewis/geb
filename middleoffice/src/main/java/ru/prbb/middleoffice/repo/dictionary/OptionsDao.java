package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.OptionsCoefficientItem;
import ru.prbb.middleoffice.domain.OptionsItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * @author RBr
 */
public interface OptionsDao {

	/**
	 * @return
	 */
	public List<OptionsItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public OptionsItem findById(Long id);

	/**
	 * @param id
	 * @return
	 */
	public OptionsCoefficientItem findCoefficientById(Long id);

	/**
	 * @param name
	 * @param coef
	 * @param comment
	 * @param sys_id
	 * @return
	 */
	public int put(String name, Number coef, String comment, Long sys_id);

	/**
	 * @param futures_alias_id
	 * @param coef
	 * @param comment
	 * @param sys_id
	 * @return
	 */
	public int putCoefficient(Long futures_alias_id, Number coef, String comment, Long sys_id);

	/**
	 * @param futures_alias_id
	 * @param name
	 * @return
	 */
	public int updateById(Long futures_alias_id, String name);

	/**
	 * @param futures_coef_id
	 * @param coef
	 * @param comment
	 * @param sys_id
	 * @param futures_alias_id
	 * @return
	 */
	public int updateCoefficientById(Long futures_coef_id, Number coef, String comment, Long sys_id, Long futures_alias_id);

	/**
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);

	/**
	 * @param id
	 * @return
	 */
	public int deleteCoefficientById(Long id);

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findCombo(String query);

}