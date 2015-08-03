/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ru.prbb.ArmUserInfo;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.NoCoefficientsItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Не хватает коэффициентов
 * 
 * @author RBr
 */
@Service
public class NoCoefficientsDao
{

	@Autowired
	private EntityManagerService ems;

	@Deprecated
	public List<NoCoefficientsItem> show(ArmUserInfo user) {
		String sql = "select id_sec, sys_id, security_code, TradeSystem from dbo.mo_WebGet_noCoef_v";
		return ems.getSelectList(user, NoCoefficientsItem.class, sql);
	}

	public List<NoCoefficientsItem> showFutures(ArmUserInfo user) {
		String sql = "select id_sec, sys_id, security_code, TradeSystem from dbo.mo_WebGet_noCoefFutures_v";
		List<NoCoefficientsItem> list = ems.getSelectList(user, NoCoefficientsItem.class, sql);
		for (NoCoefficientsItem item : list) {
			item.setType("futures");
		}
		return list;
	}

	public List<NoCoefficientsItem> showOptions(ArmUserInfo user) {
		String sql = "select id_sec, sys_id, security_code, TradeSystem from dbo.mo_WebGet_noCoefOptions_v";
		List<NoCoefficientsItem> list = ems.getSelectList(user, NoCoefficientsItem.class, sql);
		for (NoCoefficientsItem item : list) {
			item.setType("options");
		}
		return list;
	}

}
