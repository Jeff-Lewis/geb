/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.ViewCompaniesEpsItem;

/**
 * EPS по компаниям
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class ViewCompaniesEpsDaoImpl implements ViewCompaniesEpsDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<ViewCompaniesEpsItem> execute() {
		final ArrayList<ViewCompaniesEpsItem> list = new ArrayList<ViewCompaniesEpsItem>();
		for (long i = 0; i < 10; i++) {
			final ViewCompaniesEpsItem item = new ViewCompaniesEpsItem();
			item.setId_sec(i);
			item.setSecurity_code("SECURITY_CODE_" + (i + 1));
			item.setSector("sector_" + (i + 1));
			item.setEPS("EPS_" + (i + 1));
			item.setRelated_security("RELATED_SECURITY_" + (i + 1));
			list.add(item);
		}
		return list;
	}

}
