/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.CompaniesExceptionItem;
import ru.prbb.analytics.domain.CompaniesFileItem;
import ru.prbb.analytics.domain.CompaniesItem;
import ru.prbb.analytics.domain.CompaniesListItem;
import ru.prbb.analytics.domain.CompaniesQuarterItem;
import ru.prbb.analytics.domain.CompaniesYearItem;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.UserHistory.AccessAction;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Список компаний
 * 
 * @author RBr
 */
@Service
public class CompaniesDao
{

	@Autowired
	private EntityManagerService ems;

	public List<CompaniesListItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 0}";
		return ems.getSelectList(user, CompaniesListItem.class, sql);
	}

	public CompaniesItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 1, ?}";
		return ems.getSelectItem(user, CompaniesItem.class, sql, id);
	}

	public void updateById(ArmUserInfo user, Long id, Map<String, String> params) {
		String sql = "{call dbo.anca_WebSet_EquityAttributes_sp ?, ?, ?}";
		for (Entry<String, String> param : params.entrySet()) {
			ems.executeUpdate(AccessAction.UPDATE, user, sql, id, param.getKey(), param.getValue());
		}
	}

	public List<CompaniesQuarterItem> findQuarters(ArmUserInfo user, Long id, Number idCalendar) {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 21, ?, ?}";
		return ems.getSelectList(user, CompaniesQuarterItem.class, sql, id, idCalendar);
	}

	public int delQuarters(ArmUserInfo user, Long id_sec, String security_code, String period, Date date, String iso) {
		String sql = "{call dbo.anca_WebSet_dEquityPeriodData_sp 'Q', ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id_sec, security_code, period, date, iso);
	}

	public List<CompaniesYearItem> findYears(ArmUserInfo user, Long id, Number idCalendar) {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 22, ?, ?}";
		return ems.getSelectList(user, CompaniesYearItem.class, sql, id, idCalendar);
	}

	public int delYears(ArmUserInfo user, Long id_sec, String security_code, String period, Date date, String iso) {
		String sql = "{call dbo.anca_WebSet_dEquityPeriodData_sp 'Y', ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id_sec, security_code, period, date, iso);
	}

	public List<CompaniesFileItem> findFiles(ArmUserInfo user, Long id) {
		String sql = "select id_doc, file_type, file_name, insert_date from dbo.sec_docs where id_sec=?";
		return ems.getSelectList(user, CompaniesFileItem.class, sql, id);
	}

	public List<CompaniesExceptionItem> findVarException(ArmUserInfo user, Long id) {
		String sql = "{call dbo.anca_WebGet_EquityVarException_sp ?}";
		return ems.getSelectList(user, CompaniesExceptionItem.class, sql, id);
	}

	public List<SimpleItem> findComboCurrencies(ArmUserInfo user, String query) {
		String sql = "select iso as name from dbo.currency_iso_v";
		String where = " where lower(iso) like ?";
		return ems.getComboListName(sql, where, query);
	}

	public List<SimpleItem> findComboGroupSvod(ArmUserInfo user, String query) {
		String sql = "select name from dbo.PivotGroupsV";
		String where = " where lower(name) like ?";
		return ems.getComboListName(sql, where, query);
	}

	public List<SimpleItem> findComboPeriod(ArmUserInfo user, String query) {
		String sql = "select period_id as id, name from dbo.period_type";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findComboEps(ArmUserInfo user, String query) {
		String sql = "select id, name from dbo.anca_WebGet_ajaxEPSparams_v";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findComboCalendar(ArmUserInfo user, String query) {
		String sql = "select calendar_id as id, name from calendar_type";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findComboVariables(ArmUserInfo user, String query) {
		String sql = "select var_user_name as name from dbo.model_variables";
		String where = " where lower(var_user_name) like ?";
		return ems.getComboListName(sql, where, query);
	}

	public int fileUpload(ArmUserInfo user, Long id, String name, String type, byte[] content) {
		String sql = "insert into dbo.sec_docs " +
				"(id_sec, file, file_type, file_name) " +
				"values (?, ?, ?, ?)";
		return ems.executeUpdate(AccessAction.NONE, user, sql, id, content, type, name);
	}

	public CompaniesFileItem fileGetById(ArmUserInfo user, Long id, Long id_doc) {
		String sql = "select id_doc, file_type, file_name, insert_date from dbo.sec_docs"
				+ " where id_doc=?";
		return ems.getSelectItem(user, CompaniesFileItem.class, sql, id_doc);
	}

	public byte[] fileGetContentById(ArmUserInfo user, Long id, Long id_doc) {
		String sql = "select id_doc, file from dbo.sec_docs where id_doc=?";
		return ems.getSelectItem(user, byte[].class, sql, id_doc);
	}

	public int fileDeleteById(ArmUserInfo user, Long id, Long id_doc) {
		String sql = "delete from dbo.sec_docs where id_doc=?";
		return ems.executeUpdate(AccessAction.NONE, user, sql, id_doc);
	}

	public List<SimpleItem> getEquityVars(ArmUserInfo user, Long id) {
		String sql = "{call dbo.equity_vars_notused ?}";
		return ems.getSelectList(user, SimpleItem.class, sql, id);
	}

	public int addEps(ArmUserInfo user, Long id, String type, Integer baseYear, Integer calcYear) {
		String sql = "{call dbo.add_notst_eps_growth_rate ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, id, type, baseYear, calcYear);
	}

	public int delEps(ArmUserInfo user, Long id, String type) {
		String sql = "{call dbo.del_notst_eps_growth_rate ?, ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id, type);
	}

	public int addBookVal(ArmUserInfo user, Long id, String type, Integer baseYear, Integer calcYear) {
		String sql = "{call dbo.add_bv_growth_notstandard ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, id, type, baseYear, calcYear);
	}

	public int delBookVal(ArmUserInfo user, Long id, String type) {
		String sql = "{call dbo.del_bv_growth_notstandard ?, ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id, type);
	}

	public int addFormula(ArmUserInfo user, Long id, String variable, String expression, String comment) {
		String sql = "{call dbo.anca_WebSet_putEquityVarException_sp ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, id, variable, expression, comment);
	}

	public int delFormula(ArmUserInfo user, Long id, String variable) {
		String sql = "{call dbo.anca_WebSet_dEquityVarException_sp ?, ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id, variable);
	}

	public int delHistData(ArmUserInfo user, Long id_sec) {
		String sql = "{call dbo.anca_WebSet_dhist_params_values_sp ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id_sec);
	}

}
