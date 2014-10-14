package ru.prbb.middleoffice.repo.services;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.ClientSortItem;

/**
 * Сортировка клиентов
 * 
 * @author RBr
 */
public interface ClientSortDao {

	List<ClientSortItem> showSelected();

	List<ClientSortItem> showUnselected();

	int action(Long id, Integer flag);

	int setDate(Long id, Date date_b);
}
