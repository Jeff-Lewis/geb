/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.OptionsCoefficientItem;
import ru.prbb.middleoffice.domain.OptionsItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Фьючерсы
 * 
 * @author RBr
 */
@Service
public class OptionsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<OptionsItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_SelectOptionsAlias_sp 'LS'}";
		return ems.getSelectList(user, OptionsItem.class, sql);
	}

	public OptionsItem findById(ArmUserInfo user, Long futures_id) {
		String sql = "{call dbo.mo_WebGet_SelectOptionsAlias_sp 'OA', ?, null}";
		Object[] arr = ems.getSelectItem(user, Object[].class, sql, futures_id);
		OptionsItem item = new OptionsItem();
		item.setOptionsId(Utils.toLong(arr[0]));
		item.setOptions(Utils.toString(arr[1]));
		return item;
	}

	public OptionsCoefficientItem findCoefficientById(ArmUserInfo user, Long coef_id) {
		String sql = "{call dbo.mo_WebGet_SelectOptionsAlias_sp 'CO', null, ?}";
		return ems.getSelectItem(user, OptionsCoefficientItem.class, sql, coef_id);
	}

	public int put(ArmUserInfo user, String name, Number coef, String comment, Long sys_id) {
		@SuppressWarnings("unused")
		Number futures_alias_id, futures_coef_id;
		{
			String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'i', 'oa', ?, null, null, null, null, null}";
			futures_alias_id = ems.getResultItem(user, Number.class, sql, name);
		}
		{
			String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'i', 'oc', null, null, ?, ?, ?, ?}";
			futures_alias_id = ems.getResultItem(user, Number.class, sql, coef, comment, sys_id, futures_alias_id);
		}
		return 2;
	}

	public int putCoefficient(ArmUserInfo user, Long futures_alias_id, Number coef, String comment, Long sys_id) {
		String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'i', 'oc', null, null, ?, ?, ?, ?}";
		@SuppressWarnings("unused")
		Number futures_coef_id = ems.getResultItem(user, Number.class, sql, coef, comment, sys_id, futures_alias_id);
		return 1;
	}

	public int updateById(ArmUserInfo user, Long futures_alias_id, String name) {
		String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'u', 'oa', ?, null, null, null, null, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, name, futures_alias_id);
	}

	public int updateCoefficientById(ArmUserInfo user, Long futures_coef_id, Number coef, String comment, Long sys_id, Long futures_alias_id) {
		String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'u', 'oc', null, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, futures_coef_id, coef, comment, sys_id, futures_alias_id);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'd', 'oa', null, null, null, null, null, ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public int deleteCoefficientById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_iudOptionsAlias_sp 'd', 'oc', null, ?, null, null, null, null}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxOptionsAlias_v";
		String where = " where lower(security_code) like ?";
		return ems.getComboList(sql, where, query);
	}
}
