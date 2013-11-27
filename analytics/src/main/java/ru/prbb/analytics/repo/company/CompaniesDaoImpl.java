/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.CompanyItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * Список компаний
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class CompaniesDaoImpl implements CompaniesDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<CompanyItem> findAll() {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 0}";
		return em.createQuery(sql, CompanyItem.class).getResultList();
	}

	@Override
	public CompanyItem findById(Long id) {
		final CompanyItem item = new CompanyItem();
		item.setId_sec(id);
		item.setId_isin("id_isin" + id);
		item.setSecurity_code("SECURITY_CODE_" + id);
		item.setSecurity_name("SECURITY_NAME_" + id);
		item.setCurrency("currency" + id);
		item.setIndstry_grp("indstry_grp" + (id % 3));
		return item;
	}

	@Override
	public List<SimpleItem> findComboCurrencies(String query) {
		String sql = "select iso as name from dbo.currency_iso_v";
		if (Utils.isEmpty(query)) {
			return em.createQuery(sql, SimpleItem.class).getResultList();
		} else {
			sql += " where lower(iso) like :q";
			query = query.toLowerCase() + '%';
			return em.createQuery(sql, SimpleItem.class).setParameter(1, query).getResultList();
		}
	}

	@Override
	public List<SimpleItem> findComboGroupSvod(String query) {
		String sql = "select name from dbo.PivotGroupsV";
		if (Utils.isEmpty(query)) {
			return em.createQuery(sql, SimpleItem.class).getResultList();
		} else {
			sql += " where lower(name) like :q";
			query = query.toLowerCase() + '%';
			return em.createQuery(sql, SimpleItem.class).setParameter(1, query).getResultList();
		}
	}

	@Override
	public List<SimpleItem> findComboPeriod(String query) {
		String sql = "select period_id as id, name from dbo.period_type";
		if (Utils.isEmpty(query)) {
			return em.createQuery(sql, SimpleItem.class).getResultList();
		} else {
			sql += " where lower(name) like :q";
			query = query.toLowerCase() + '%';
			return em.createQuery(sql, SimpleItem.class).setParameter(1, query).getResultList();
		}
	}

	@Override
	public List<SimpleItem> findComboEps(String query) {
		String sql = "select id, name from anca_WebGet_ajaxEPSparams_v";
		if (Utils.isEmpty(query)) {
			return em.createQuery(sql, SimpleItem.class).getResultList();
		} else {
			sql += " where lower(name) like :q";
			query = query.toLowerCase() + '%';
			return em.createQuery(sql, SimpleItem.class).setParameter(1, query).getResultList();
		}
	}

	@Override
	public List<SimpleItem> findComboVariables(String query) {
		String sql = "select var_user_name as name from dbo.model_variables";
		if (Utils.isEmpty(query)) {
			return em.createQuery(sql, SimpleItem.class).getResultList();
		} else {
			sql += " where lower(var_user_name) like :q";
			query = query.toLowerCase() + '%';
			return em.createQuery(sql, SimpleItem.class).setParameter(1, query).getResultList();
		}
	}

}
