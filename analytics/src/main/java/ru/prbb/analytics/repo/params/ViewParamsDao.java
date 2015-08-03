/**
 * 
 */
package ru.prbb.analytics.repo.params;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.ViewParamItem;
import ru.prbb.analytics.domain.ViewParamsItem;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Справочник параметров
 * 
 * @author RBr
 */
@Service
public class ViewParamsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ViewParamsItem> findAll(ArmUserInfo user) {
		String sql = "select param_id, blm_id, code, name from dbo.params";
		return ems.getSelectList(user, ViewParamsItem.class, sql);
	}

	public ViewParamItem findById(ArmUserInfo user, String blm_id) {
		String sql = "select field_id, field_mnemonic, description, data_license_category, category," +
				" definition, comdty, equity, muni, pfd, m_mkt, govt, corp, indx, curncy, mtge, standard_width," +
				" standard_decimal_places, field_type, back_office, extended_back_office, production_date," +
				" current_maximum_width, bval, bval_blocked, getfundamentals, gethistory, getcompany," +
				" old_mnemonic, data_license_category_2, psboopt" +
				" from dbo.bloomberg_dl_fields" +
				" where field_id=?";
		return ems.getSelectItem(user, ViewParamItem.class, sql, blm_id);
	}

}
