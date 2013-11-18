/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import ru.prbb.analytics.domain.ViewCompaniesEpsItem;

/**
 * EPS по компаниям
 * 
 * @author RBr
 * 
 */
public interface ViewCompaniesEpsDao {

	/**
	 * @return
	 */
	List<ViewCompaniesEpsItem> execute();

}
