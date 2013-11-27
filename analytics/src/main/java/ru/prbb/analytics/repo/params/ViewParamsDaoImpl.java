/**
 * 
 */
package ru.prbb.analytics.repo.params;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.ViewParamItem;
import ru.prbb.analytics.domain.ViewParamsItem;

/**
 * Справочник параметров
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class ViewParamsDaoImpl implements ViewParamsDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<ViewParamsItem> findAll() {
		String sql = "select param_id, blm_id, code, name from dbo.params";
		return em.createQuery(sql, ViewParamsItem.class).getResultList();
	}

	@Override
	public ViewParamItem findById(String blm_id) {
		String sql = "select field_id, field_mnemonic, description, data_license_category, category," +
				" definition, comdty, equity, muni, pfd, m_mkt, govt, corp, indx, curncy, mtge, standard_width," +
				" standard_decimal_places, field_type, back_office, extended_back_office, production_date," +
				" current_maximum_width, bval, bval_blocked, getfundamentals, gethistory, getcompany," +
				" old_mnemonic, data_license_category_2, psboopt" +
				" from dbo.bloomberg_dl_fields" +
				" where field_id=:blm_id";
		return em.createQuery(sql, ViewParamItem.class).setParameter(1, blm_id).getSingleResult();
	}

}