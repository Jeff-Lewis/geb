/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class DealsOneNewDao
{

	@Autowired
	private EntityManagerService ems;

	public int put(ArmUserInfo user, String batch, String tradeNum,
			String tradeTicker, Date tradeDate, Date settleDate, String tradeOper,
			Double tradePrice, Double quantity, String currency, String tradeSystem,
			String account, String investmentPortfolio, String securityCode,
			String futuresAlias, Integer kindTicker) {
		String sql = "{call dbo.mo_WebSet_putSingleDeal_sp"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql,
				batch, tradeNum, tradeTicker, tradeDate, settleDate, tradeOper, tradePrice, quantity,
				currency, tradeSystem, account, investmentPortfolio, securityCode, futuresAlias, kindTicker);
	}
}
