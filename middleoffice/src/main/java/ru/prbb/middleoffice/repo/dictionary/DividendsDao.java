/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.middleoffice.domain.DividendItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Дивиденды
 * 
 * @author RBr
 */
@Service
public class DividendsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<DividendItem> findAll(ArmUserInfo user, Long security, Long client, Long broker, Long account, Date begin, Date end) {
		String sql = "{call dbo.mo_WebGet_Dividends_sp null, ?, ?, ?, ?, ?, ?}";
		return ems.getSelectList(user, DividendItem.class, sql, security, client, broker, account, begin, end);
//		List<Object[]> list = ems.getSelectList(user, sql, security, client, broker, account, begin, end);
//		List<DividendItem> res = new ArrayList<>(list.size());
//		for (Object[] arr : list) {
//			DividendItem item = new DividendItem(arr);
//			res.add(item);
//		}
//		return res;
	}

	public DividendItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebGet_Dividends_sp ?}";
		return ems.getSelectItem(user, DividendItem.class, sql, id);
	}

	public int put(ArmUserInfo user, Long security, Long account, Long fund, Long currency, Date record, Date receive,
			Integer quantity, Double dividend_per_share, Double extra_costs_per_share) {
		String sql = "{call dbo.mo_WebSet_putDividends_sp ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, security, account, fund, currency, record, receive, quantity, dividend_per_share,
				extra_costs_per_share);
	}

	public int updateById(ArmUserInfo user, Long id, Date receive) {
		String sql = "{call dbo.mo_WebSet_uActualDividends_sp ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, receive);
	}

	public int updateAttrById(ArmUserInfo user, Long id, String type, String value) {
		String sql = "{call dbo.mo_WebSet_setDividendAttributes_sp ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, type, value);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_dDividends_sp ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

}
