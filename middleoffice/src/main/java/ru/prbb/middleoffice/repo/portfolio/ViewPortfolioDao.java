/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.ViewPortfolioItem;
import ru.prbb.middleoffice.domain.ViewPortfolioTransferItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Текущий портфель
 * 
 * @author RBr
 */
@Service
public class ViewPortfolioDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ViewPortfolioItem> executeSelect(ArmUserInfo user, Date rep_date, Long id_sec, Long funds_id) {
		String sql = "{call dbo.mo_WebGet_SelectPlReport_sp ?, ?, ?}";
		return ems.getSelectList(user, ViewPortfolioItem.class, sql, rep_date, id_sec, funds_id);
	}

	public List<ViewPortfolioTransferItem> executeSelect(ArmUserInfo user, Date rep_date, Long funds_id) {
		String sql = "{call dbo.mo_WebGet_SelectPlReport_sp ?, null, ?, 1}";
		return ems.getSelectList(user, ViewPortfolioTransferItem.class, sql, rep_date, funds_id);
	}

	public int executeCalc(ArmUserInfo user, Date report_date, Long id_sec, Long funds_id) {
//		String sql = "{call dbo.mo_WebSet_PlReport_sp ?, null, ?}";
		String sql = "{call dbo.PlPortfolioOnDate ?, ?, ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql, report_date, id_sec, funds_id);
	}

	public int executeDelete(ArmUserInfo user, Date begin_date, Long id_sec) {
		String sql = "{call dbo.mo_dFinResAndRests_sp ?, ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, begin_date, id_sec);
	}

}
