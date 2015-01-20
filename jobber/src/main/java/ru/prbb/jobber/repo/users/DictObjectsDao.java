package ru.prbb.jobber.repo.users;

import java.util.List;

import ru.prbb.jobber.domain.DictObjectItem;

/**
 * Справочник пользователей - Объекты
 * 
 * @author BrihlyaevRA
 */
public interface DictObjectsDao {

	/**
	 * @return
	 */
	List<DictObjectItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	DictObjectItem findById(Long id);

	/**
	 * @param name
	 * @param comment
	 * @return
	 */
	public int put(String name, String comment);

	/**
	 * @param id
	 * @param name
	 * @param comment
	 * @return
	 */
	public int updateById(Long id, String name, String comment);

	/**
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
}
