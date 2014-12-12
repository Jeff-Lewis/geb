package ru.prbb.middleoffice.repo.services;

import java.util.List;

import ru.prbb.middleoffice.domain.NoCoefficientsItem;

/**
 * Не хватает коэффициентов
 * 
 * @author RBr
 */
public interface NoCoefficientsDao {

	List<NoCoefficientsItem> show();

}
