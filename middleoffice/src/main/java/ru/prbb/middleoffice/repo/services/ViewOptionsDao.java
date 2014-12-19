package ru.prbb.middleoffice.repo.services;

/**
 * Редактирование опционов
 * 
 * @author RBr
 */
public interface ViewOptionsDao {

	void put(Long code, String deal, Long futures);

	void del(Long code, String deal);

}
