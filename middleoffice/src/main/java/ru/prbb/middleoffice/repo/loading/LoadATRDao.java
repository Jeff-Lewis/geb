/**
 * 
 */
package ru.prbb.middleoffice.repo.loading;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.AtrLoadDataItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Загрузка ATR
 * 
 * @author RBr
 */
@Service
public class LoadATRDao
{

	@Autowired
	private EntityManagerService ems;

	public List<AtrLoadDataItem> execute(ArmUserInfo user, List<AtrLoadDataItem> answer,
			String maType, Integer taPeriod, String period, String calendar) {
		String sql = "{call dbo.mo_WebSet_putATR_sp ?, ?, ?, " + taPeriod + ", '" + maType + "', 'PX_HIGH', 'PX_LOW', 'PX_LAST', '"
				+ period + "', '" + calendar + "'}";
		for (AtrLoadDataItem item : answer) {
			ems.executeUpdate(AccessAction.OTHER, user, sql,
					item.getSecurity(), Utils.parseDate(item.getDate()), new BigDecimal(item.getValue()));
		}
		return answer;
	}

	public List<SimpleItem> getTypeMA(String query) {
		String sql = "select id, algorithm_name as name from dbo.mo_WebGet_ajaxAlgorithm_v";
		String where = " where lower(algorithm_name) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> getPeriod(String query) {
		String sql = "select period_id as id, name from dbo.period_type";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> getCalendar(String query) {
		String sql = "select calendar_id as id, name from dbo.calendar_type";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}

}
