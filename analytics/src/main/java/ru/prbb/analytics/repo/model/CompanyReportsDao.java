/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.CompanyAllItem;
import ru.prbb.analytics.domain.CompanyStaffItem;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.UserHistory.AccessAction;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Компании и отчёты
 * 
 * @author RBr
 */
@Service
public class CompanyReportsDao
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManagerService ems;

	public List<SimpleItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.anca_WebGet_Reports_sp}";
		return ems.getSelectList(user, SimpleItem.class, sql);
	}

	public SimpleItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.anca_WebGet_Reports_sp ?}";
		return ems.getSelectItem(user, SimpleItem.class, sql, id);
	}

	public int put(ArmUserInfo user, String name) {
		String sql = "{call dbo.anca_WebSet_putReports_sp ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, name);
	}

	public int renameById(ArmUserInfo user, Long id, String name) {
		String sql = "{call dbo.anca_WebSet_udReports_sp 'u', ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, name);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.anca_WebSet_udReports_sp 'd', ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public List<CompanyAllItem> findStaff(ArmUserInfo user, Long id) {
		String sql = "{call dbo.anca_WebGet_EquitiesNotInReport_sp ?}";
		return ems.getSelectList(user, CompanyAllItem.class, sql, id);
	}

	public List<CompanyStaffItem> findStaffReport(ArmUserInfo user, Long id) {
		String sql = "{call dbo.anca_WebGet_Security_report_maps_sp ?}";

		List<Object[]> list = ems.getSelectList(user, Object[].class, sql, id);

		List<CompanyStaffItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			CompanyStaffItem item = new CompanyStaffItem();
			item.setId(Utils.toLong(arr[0]));
			item.setSecurity_code(Utils.toString(arr[2]));
			item.setShort_name(Utils.toString(arr[3]));
			res.add(item);
		}
		return res;
	}

	public int[] putStaff(ArmUserInfo user, Long report_id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_putSecurity_report_maps_sp ?, ?}";

		int[] res = new int[cids.length];
		for (int i = 0; i < cids.length; i++) {
			try {
				res[i] = ems.executeUpdate(AccessAction.UPDATE, user, sql, report_id, cids[i]);
			} catch (Exception e) {
				log.error("putStaff", e);
			}
		}
		return res;
	}

	public int[] deleteStaff(ArmUserInfo user, Long report_id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_udSecurity_report_maps_sp 'd', ?}";

		int[] res = new int[cids.length];
		for (int i = 0; i < cids.length; i++) {
			try {
				res[i] = ems.executeUpdate(AccessAction.DELETE, user, sql, cids[i]);
			} catch (Exception e) {
				log.error("deleteStaff", e);
			}
		}
		return res;
	}

}
