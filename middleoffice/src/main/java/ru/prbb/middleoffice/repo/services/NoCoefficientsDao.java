package ru.prbb.middleoffice.repo.services;

import java.util.List;

import ru.prbb.middleoffice.domain.NoCoefficientsItem;

/**
 * Не хватает коэффициентов
 * 
 * @author RBr
 */
public interface NoCoefficientsDao {

	@Deprecated
	List<NoCoefficientsItem> show();

	List<NoCoefficientsItem> showFutures();

	List<NoCoefficientsItem> showOptions();

}
