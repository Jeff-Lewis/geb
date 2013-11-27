/**
 * 
 */
package ru.prbb.analytics.repo.reports;

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
		String sql = "{call dbo.anca_WebGet_EquityEPSinfo_sp}";
		return em.createQuery(sql, ViewCompaniesEpsItem.class).getResultList();
	}

}
