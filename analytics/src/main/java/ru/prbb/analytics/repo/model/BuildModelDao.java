/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import ru.prbb.analytics.domain.BuildModelItem;
import ru.prbb.analytics.domain.PortfolioWatchListItem;

/**
 * Расчёт модели по компании
 * 
 * @author RBr
 * 
 */
public interface BuildModelDao {

	/**
	 * dbo.build_model_proc_p
	 */
	BuildModelItem calculateModel(Long id);

	/**
	 * dbo.build_model_proc
	 */
	//List<BuildModelItem> calculateSvod();

	List<PortfolioWatchListItem> getPortfolioWatchList();

	int calculateModelQ(String securityCode);

	int calculateModelHY(String securityCode);

}
