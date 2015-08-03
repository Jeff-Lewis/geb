/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.middleoffice.domain.CouponItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Купоны (погашение)
 * 
 * @author RBr
 */
@Service
public class CouponsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<CouponItem> findAll(ArmUserInfo user, Long security, Long client, Long broker, Long operation, Date begin, Date end) {
		String sql = "{call dbo.mo_WebGet_Coupons_sp null, ?, ?, ?, null, ?, ?, ?}";
		return ems.getSelectList(user, CouponItem.class, sql, security, client, broker, operation, begin, end);
	}

	public CouponItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebGet_Coupons_sp ?}";
		return ems.getResultItem(user, CouponItem.class, sql, id);
	}

	public int put(ArmUserInfo user, Long security, Long account, Long fund, Long currency, Date record, Date receive,
			Integer quantity, Double coupon_per_share, Double extra_costs_per_share, Long coupon_oper_id) {
		String sql = "{call dbo.mo_WebSet_putCoupons_sp ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, security, account, fund, currency, record, receive, quantity, coupon_per_share,
				extra_costs_per_share, coupon_oper_id);
	}

	public int updateById(ArmUserInfo user, Long id, Date receive) {
		String sql = "{call dbo.mo_WebSet_uActualCoupons_sp ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, receive);
	}

	public int updateAttrById(ArmUserInfo user, Long id, String type, String value) {
		String sql = "{call dbo.mo_WebSet_setCouponAttributes_sp ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, type, value);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_dCoupons_sp ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public List<SimpleItem> findComboOperations(String query) {
		String sql = "select id, oper_name as name from dbo.mo_WebGet_ajaxCouponOperations_v";
		String where = " where lower(oper_name) like ?";
		return ems.getComboList(sql, where, query);
	}
}
