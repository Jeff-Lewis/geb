/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.BuildModelItem;
import ru.prbb.analytics.domain.PortfolioWatchListItem;
import ru.prbb.analytics.repo.UserHistory.AccessAction;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Расчёт модели по компании
 * 
 * @author RBr
 */
@Service
public class BuildModelDao
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManagerService ems;

	public BuildModelItem calculateModel(ArmUserInfo user, Long id) {
		try {
			String sql = "{call dbo.build_model_proc_p ?}";
			return ems.getResultItem(user, BuildModelItem.class, sql, id);
		} catch (Exception e) {
			log.error("build_model_proc_p {}", id, e);
			BuildModelItem item = new BuildModelItem();
			item.setSecurity_code(id.toString());
			item.setStatus(e.getMessage());
			return item;
		}
	}

	public List<PortfolioWatchListItem> getPortfolioWatchList(ArmUserInfo user) {
		String sql = "select short_name, security_code, period_id from anca_portfolio_watch_lst_v";
		return ems.getSelectList(user, PortfolioWatchListItem.class, sql);
	}

	public int calculateModelQ(ArmUserInfo user, String securityCode) {
		String sql = "{call dbo.build_model_proc_q ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql, securityCode);
	}

	public int calculateModelHY(ArmUserInfo user, String securityCode) {
		String sql = "{call dbo.build_model_proc_hy ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql, securityCode);
	}
}
