/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.PortfolioItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewFuturesItem;
import ru.prbb.middleoffice.domain.ViewOptionsItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class EquitiesDao
{

	@Autowired
	private EntityManagerService ems;

	public List<PortfolioItem> findAllPortfolio(ArmUserInfo user) {
		String sql = "select id_sec, ticker, deal_name, date_insert from dbo.portfolio_equity_v";
		return ems.getSelectList(user, PortfolioItem.class, sql);
	}

	public List<PortfolioItem> findAllSwaps(ArmUserInfo user) {
		String sql = "{call mo_WebGet_SecuritiesDealNameMapping_sp 4}";
		return ems.getSelectList(user, PortfolioItem.class, sql);
	}

	public List<PortfolioItem> findAllBonds(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_SecuritiesDealNameMapping_sp 5}";
		List<Object[]> list = ems.getSelectList(user, Object[].class, sql);

		List<PortfolioItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			PortfolioItem item = new PortfolioItem();
			item.setId_sec(Utils.toLong(arr[0]));
			item.setTicker(Utils.toString(arr[2]));
			item.setDeal_name(Utils.toString(arr[3]));
			item.setDate_insert(Utils.toTimestamp(arr[4]));
			res.add(item);
		}
		return res;
	}

	public List<ViewFuturesItem> findAllFutures(ArmUserInfo user) {
		String sql = "select * from dbo.portfolio_cmdt_v";
		return ems.getSelectList(user, ViewFuturesItem.class, sql);
	}

	public List<ViewOptionsItem> findAllOptions(ArmUserInfo user) {
		String sql = "select * from dbo.portfolio_options_v";
		return ems.getSelectList(user, ViewOptionsItem.class, sql);
	}

	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, security_code as name from dbo.mo_WebGet_ajaxEquity_v";
		String where = " where lower(security_code) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findComboInvestmentPortfolio(String query) {
		String sql = "select p_id as id, name from dbo.investment_portfolio";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}

	public List<SimpleItem> findComboPortfolio(String query) {
		String sql = "select id_sec as id, security_code as name from dbo.mo_WebGet_Portfolio_v";
		String where = " where lower(security_code) like ?";
		return ems.getComboList(sql, where, query);
	}
}
