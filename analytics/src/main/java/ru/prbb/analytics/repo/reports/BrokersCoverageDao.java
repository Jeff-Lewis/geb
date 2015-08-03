/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.BrokersCoverageItem;
import ru.prbb.analytics.repo.UserHistory.AccessAction;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Покрытие брокеров
 * 
 * @author RBr
 */
@Service
public class BrokersCoverageDao
{

	@Autowired
	private EntityManagerService ems;

	public List<BrokersCoverageItem> execute(ArmUserInfo user) {
		String sql = "{call dbo.anca_WebGet_EquityBrokerCoverage_sp}";
		return ems.getSelectList(user, BrokersCoverageItem.class, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int change(ArmUserInfo user, Long id, String broker, Integer value) {
		String sql = "{call dbo.anca_WebSet_setEquityBrokerCoverage_sp ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, broker, value);
	}
}
