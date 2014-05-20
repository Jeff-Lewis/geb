package ru.prbb.middleoffice.repo.services;

import java.util.List;

import ru.prbb.middleoffice.domain.NotEnoughCouponsItem;

/**
 * Нет настроек для купонов
 * 
 * @author RBr
 */
public interface NotVisibleCouponsDao {

	List<NotEnoughCouponsItem> show();

}
