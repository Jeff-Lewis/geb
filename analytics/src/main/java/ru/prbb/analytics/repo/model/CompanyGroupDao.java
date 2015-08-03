/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.CompanyStaffItem;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.UserHistory.AccessAction;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Компании и группы
 * 
 * @author RBr
 */
@Service
public class CompanyGroupDao
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManagerService ems;

	public List<SimpleItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.anca_WebGet_PivotGroups_sp}";
		return ems.getSelectList(user, SimpleItem.class, sql);
	}

	public SimpleItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.anca_WebGet_PivotGroups_sp ?}";
		return ems.getSelectItem(user, SimpleItem.class, sql, id);
	}

	public int put(ArmUserInfo user, String name) {
		String sql = "{call dbo.anca_WebSet_putPivotGroup_sp ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, name);
	}

	public int renameById(ArmUserInfo user, Long id, String name) {
		String sql = "{call dbo.anca_WebSet_udPivotGroup_sp 'u', ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, name);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.anca_WebSet_udPivotGroup_sp 'd', ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public List<CompanyStaffItem> findStaff(ArmUserInfo user) {
		String sql = "{call dbo.anca_WebGet_SelectEquitiesNotPivotGroup_sp}";
		return ems.getSelectList(user, CompanyStaffItem.class, sql);
	}

	public List<CompanyStaffItem> findStaff(ArmUserInfo user, Long id) {
		String sql = "{call dbo.anca_WebGet_SelectPivotGroupEquities_sp ?}";
		return ems.getSelectList(user, CompanyStaffItem.class, sql, id);
	}

	public int[] putStaff(ArmUserInfo user, Long group_id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_addEquityPivotGroup_sp ?, ?}";

		int[] res = new int[cids.length];
		for (int i = 0; i < cids.length; i++) {
			Long id_sec = cids[i];
			try {
				res[i] = ems.executeUpdate(AccessAction.UPDATE, user, sql, id_sec, group_id);
			} catch (DataAccessException e) {
				log.error("putStaff", e);
			}
		}

		return res;
	}

	public int[] deleteStaff(ArmUserInfo user, Long _id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_removeEquityPivotGroup_sp ?}";

		int[] res = new int[cids.length];
		for (int i = 0; i < cids.length; i++) {
			Long id_sec = cids[i];
			try {
				res[i] = ems.executeUpdate(AccessAction.DELETE, user, sql, id_sec);
			} catch (DataAccessException e) {
				log.error("putStaff", e);
			}
		}

		return res;
	}

}
