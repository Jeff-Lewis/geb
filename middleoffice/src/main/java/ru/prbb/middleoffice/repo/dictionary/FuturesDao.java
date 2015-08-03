/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.FuturesCoefficientItem;
import ru.prbb.middleoffice.domain.FuturesItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Фьючерсы
 * 
 * @author RBr
 */
@Service
public class FuturesDao
{

	@Autowired
	private EntityManagerService ems;

	public List<FuturesItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_SelectFuturesAlias_sp 'LS'}";
		return ems.getSelectList(user, FuturesItem.class, sql);
//		List<FuturesItem> res = new ArrayList<>(list.size());
//		for (Object[] arr : list) {arr
//			FuturesItem item = new FuturesItem(arr);
//			res.add(item);
//		}
//		return res;
	}

	public FuturesItem findById(ArmUserInfo user, Long futures_id) {
		String sql = "{call dbo.mo_WebGet_SelectFuturesAlias_sp 'FA', ?, null}";
		Object[] arr = ems.getSelectItem(user, Object[].class, sql, futures_id);
		FuturesItem item = new FuturesItem();
		item.setFuturesId(Utils.toLong(arr[0]));
		item.setFutures(Utils.toString(arr[1]));
		return item;
	}

	public FuturesCoefficientItem findCoefficientById(ArmUserInfo user, Long coef_id) {
		String sql = "{call dbo.mo_WebGet_SelectFuturesAlias_sp 'CF', null, ?}";
		return ems.getSelectItem(user, FuturesCoefficientItem.class, sql, coef_id);
	}

	public int put(ArmUserInfo user, String name, Number coef, String comment, Long sys_id) {
		@SuppressWarnings("unused")
		Number futures_alias_id, futures_coef_id;
		{
			String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'i', 'fa', ?, null, null, null, null, null}";
			futures_alias_id = ems.getResultItem(user, Number.class, sql, name);
		}
		{
			String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'i', 'fc', null, null, ?, ?, ?, ?}";
			futures_coef_id = ems.getResultItem(user, Number.class, sql, coef, comment, sys_id, futures_alias_id);
		}
		return 2;
	}

	public int putCoefficient(ArmUserInfo user, Long futures_alias_id, Number coef, String comment, Long sys_id) {
		String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'i', 'fc', null, null, ?, ?, ?, ?}";
		@SuppressWarnings("unused")
		Number futures_coef_id = ems.getResultItem(user, Number.class, sql, coef, comment, sys_id, futures_alias_id);
		return 1;
	}

	public int updateById(ArmUserInfo user, Long futures_alias_id, String name) {
		String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'u', 'fa', ?, null, null, null, null, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, name, futures_alias_id);
	}

	public int updateCoefficientById(ArmUserInfo user, Long futures_coef_id, Number coef, String comment, Long sys_id, Long futures_alias_id) {
		String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'u', 'fc', null, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, futures_coef_id, coef, comment, sys_id, futures_alias_id);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'd', 'fa', null, null, null, null, null, ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public int deleteCoefficientById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_iudFuturesAlias_sp 'd', 'fc', null, ?, null, null, null, null}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxFuturesAlias_v";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}
}
