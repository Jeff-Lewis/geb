package ru.prbb.middleoffice.repo.services;

import java.util.List;

import ru.prbb.middleoffice.domain.NotEnoughDividendsItem;

/**
 * Нет настроек для дивидендов
 * 
 * @author RBr
 */
public interface NotVisibleDividendsDao {

	List<NotEnoughDividendsItem> show();

}
