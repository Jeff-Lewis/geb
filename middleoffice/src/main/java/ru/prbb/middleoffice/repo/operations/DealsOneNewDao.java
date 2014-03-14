/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import java.sql.Date;

/**
 * Загрузка единичной сделки
 * 
 * @author RBr
 * 
 */
public interface DealsOneNewDao {

	/**
	 * @param batch
	 * @param tradeNum
	 * @param tradeTicker
	 * @param tradeDate
	 * @param settleDate
	 * @param tradeOper
	 * @param tradePrice
	 * @param quantity
	 * @param currency
	 * @param tradeSystem
	 * @param account
	 * @param investmentPortfolio
	 * @param securityCode
	 * @param futuresAlias
	 * @param kindTicker
	 * @return
	 */
	int put(String batch, String tradeNum, String tradeTicker,
			Date tradeDate, Date settleDate, String tradeOper, Double tradePrice,
			Double quantity, String currency, String tradeSystem, String account,
			String investmentPortfolio, String securityCode, String futuresAlias,
			Integer kindTicker);

}
