/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.BrokersEstimateChangeItem;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Изменение оценок брокеров
 * 
 * @author RBr
 */
@Service
public class BrokersEstimateChangeDao
{

	@Autowired
	private EntityManagerService ems;

	public List<BrokersEstimateChangeItem> execute(ArmUserInfo user) {
		String sql = "{call dbo.anca_WebGet_BrokerEstimatesChange_sp}";
		return ems.getSelectList(user, BrokersEstimateChangeItem.class, sql);
	}

}
