/**
 * 
 */
package ru.prbb.analytics.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.Utils;
import ru.prbb.analytics.domain.EquitiesItem;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class EquitiesDao
{

	@Autowired
	private EntityManagerService ems;

	public List<EquitiesItem> findAllEquities(ArmUserInfo user, String filter, Long security_id, Integer fund_flag) {
		if (Utils.isEmpty(filter) && Utils.isEmpty(security_id)) {
			String sql = "{call dbo.anca_WebGet_EquityFilter_sp}";
			return ems.getSelectList(user, EquitiesItem.class, sql);
		} else {
			String sql = "{call dbo.anca_WebGet_EquityFilter_sp ?, ?, ?}";
			return ems.getSelectList(user, EquitiesItem.class, sql, filter, fund_flag, security_id);
		}
	}

	public List<SimpleItem> comboFilter(String query) {
		String sql = "select name from dbo.anca_WebGet_ajaxEquityFilter_v";
		String where = " where lower(name) like ?";
		return ems.getComboListName(sql, where, query);
	}

	public List<SimpleItem> comboEquities(String query) {
		String sql = "select id, name from dbo.anca_WebGet_ajaxEquity_v";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}
}
