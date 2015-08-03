/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ru.prbb.ArmUserInfo;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.SecurityItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class SecuritiesDao
{

	@Autowired
	private EntityManagerService ems;

	public List<SecurityItem> findAll(ArmUserInfo user, String filter, Long security) {
		String sql = "{call dbo.mo_WebGet_FilterSecurities_sp ?, ?}";
		return ems.getSelectList(user, SecurityItem.class, sql, filter, security);
	}

	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxSecurities_v";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findComboType(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxSecurityType_v";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findComboFilter(String query) {
		String sql = "select name from dbo.mo_WebGet_ajaxFilterRequest_v";
		String where = " where lower(name) like ?";
		return ems.getComboListName(sql, where, query);
	}

	public List<SimpleItem> findComboShares(String query) {
		String sql = "select id, security_code as name from dbo.mo_WebGet_ajaxShares_v";
		String where = " where lower(security_code) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findComboSwaps(String query) {
		String sql = "select id_sec as id, security_code as name from dbo.mo_WebGet_ajaxSwaps_v";
		String where = " where lower(security_code) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findComboBonds(String query) {
		String sql = "select id, security_code as name from dbo.mo_WebGet_ajaxBonds_v";
		String where = " where lower(security_code) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findComboFutures(String query) {
		String sql = "select id, security_code as name from dbo.mo_WebGet_ajaxFutures_v";
		String where = " where lower(security_code) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findComboOptions(String query) {
		String sql = "select id, security_code as name from dbo.mo_WebGet_ajaxOptions_v";
		String where = " where lower(security_code) like ?";
		return ems.getComboList(sql, where, query);
	}

}
