/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewCompaniesEpsItem> execute() {
		String sql = "{call dbo.anca_WebGet_EquityEPSinfo_sp}";
		Query q = em.createNativeQuery(sql, ViewCompaniesEpsItem.class);
		return q.getResultList();
	}

}
