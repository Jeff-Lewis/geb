/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ru.prbb.ArmUserInfo;
import org.springframework.stereotype.Service;

import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Параметры запросов
 * 
 * @author RBr
 */
@Service
public class BloombergParamsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<SimpleItem> findPeriod(ArmUserInfo user, String query) {
		String sql = "select period_id as id, name from dbo.period_type";
		String where = "";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findCalendar(ArmUserInfo user, String query) {
		String sql = "select calendar_id as id, name from dbo.calendar_type";
		String where = "";
		return ems.getComboList(sql, where, query);
	}

}
