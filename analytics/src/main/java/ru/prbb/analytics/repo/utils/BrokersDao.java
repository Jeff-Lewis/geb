/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.BrokerItem;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.UserHistory.AccessAction;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Справочник брокеров
 * 
 * @author RBr
 */
@Service
public class BrokersDao
{

	@Autowired
	private EntityManagerService ems;

	public List<BrokerItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.anca_WebGet_SelectBrokers_sp}";
		return ems.getSelectList(user, BrokerItem.class, sql);
	}

	public BrokerItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.anca_WebGet_SelectBrokers_sp ?}";
		return ems.getSelectItem(user, BrokerItem.class, sql, id);
	}

	public int put(ArmUserInfo user, String full_name, Integer rating, String bloomberg_code,
			Integer cover_russian, String short_name) {
		String sql = "{call dbo.anca_WebSet_putBrokers_sp ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql,
				full_name, rating, bloomberg_code, cover_russian, short_name);
	}

	public int updateById(ArmUserInfo user, Long id, String full_name, Integer rating, String bloomberg_code,
			Integer cover_russian, String short_name) {
		String sql = "{call dbo.anca_WebSet_udBrokers_sp 'u', ?, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql,
				id, full_name, rating, bloomberg_code, cover_russian, short_name);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.anca_WebSet_udBrokers_sp 'd', ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.anca_WebGet_ajaxBrokers_v";
		String where = "where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}
}
