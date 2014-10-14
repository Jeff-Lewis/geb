package ru.prbb.middleoffice.repo.loading;

import java.util.List;
import java.util.Map;

import ru.prbb.middleoffice.domain.LoadCurrenciesItem;

/**
 * Загрузка курсов валют
 * 
 * @author RBr
 */
public interface LoadCurrenciesDao {

	List<LoadCurrenciesItem> findAll();

	/**
	 * @param securities
	 * @param answer
	 *            security -> {date -> { field, value } }
	 * @return
	 */
	List<Map<String, Object>> execute(String[] securities, Map<String, Map<String, Map<String, String>>> answer);

}
