/**
 * 
 */
package ru.prbb.analytics.repo.portfolio;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.ViewPortfolioItem;
import ru.prbb.analytics.repo.UserHistory.AccessAction;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Добавление организаций в Portfolio
 * 
 * @author RBr
 */
@Service
public class ViewPortfolioDao
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManagerService ems;

	public List<ViewPortfolioItem> findAll(ArmUserInfo user) {
		String sql = "select id_sec, security_code from dbo.equity_request_v";
		return ems.getSelectList(user, ViewPortfolioItem.class, sql);
	}

	public List<ViewPortfolioItem> findAllPortfolio(ArmUserInfo user) {
		String sql = "select id_sec, security_code from dbo.securities where portfolio = 'portfolio'";
		return ems.getSelectList(user, ViewPortfolioItem.class, sql);
	}

	public int[] put(ArmUserInfo user, Long[] ids) {
		String sql = "update dbo.securities set portfolio='portfolio' where id_sec=?";
		int[] res = new int[ids.length];
		for (int i = 0; i < res.length; i++) {
			try {
				res[i] = ems.executeUpdate(AccessAction.NONE, user, sql, ids[i]);
			} catch (Exception e) {
				log.error("put", e);
			}
		}
		return res;
	}

	public int[] del(ArmUserInfo user, Long[] ids) {
		String sql = "update dbo.securities set portfolio='' where id_sec=?";
		int[] res = new int[ids.length];
		for (int i = 0; i < res.length; i++) {
			try {
				res[i] = ems.executeUpdate(AccessAction.NONE, user, sql, ids[i]);
			} catch (Exception e) {
				log.error("del", e);
			}
		}
		return res;
	}

}
