/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.Utils;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Ввод нового инструмента
 * 
 * @author RBr
 */
@Service
public class NewInstrumentDao
{

	@Autowired
	private EntityManagerService ems;

	public int putSecurityData(ArmUserInfo user, Map<String, String> values) {
		String sql = "{call dbo.put_equity_proc "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?}";

		return ems.executeUpdate(AccessAction.OTHER, user, sql,
				values.get("ID_BB_GLOBAL"),
				values.get("ID_BB"),
				values.get("ID_BB_UNIQUE"),
				values.get("ID_BB_SEC_NUM_DES"),
				values.get("ID_BB_SEC_NUM_SRC"),
				values.get("ID_ISIN"),
				values.get("ID_CUSIP"),
				values.get("PARSEKYABLE_DES"),
				values.get("PARSEKYABLE_DES_SOURCE"),
				values.get("SECURITY_TYP"),
				values.get("MARKET_SECTOR_DES"),
				values.get("FEED_SOURCE"),
				values.get("TICKER"),
				values.get("SECURITY_NAME"),
				values.get("NAME"),
				values.get("SHORT_NAME"),
				values.get("EQY_PRIM_EXCH"),
				values.get("EXCH_CODE"),
				values.get("EQY_FUND_IND"),
				values.get("INDUSTRY_GROUP"),
				values.get("INDUSTRY_SUBGROUP"),
				values.get("ADR_SH_PER_ADR"),
				values.get("CRNCY"),
				values.get("EQY_FUND_CRNCY"),
				values.get("EQY_PRIM_SECURITY_CRNCY"),
				values.get("ADR_CRNCY"),
				values.get("BEST_CRNCY_ISO"),
				values.get("DVD_CRNCY"),
				values.get("EARN_EST_CRNCY"),
				values.get("EQY_FUND_TICKER"),
				values.get("EQY_FISCAL_YR_END"),
				values.get("PRIMARY_PERIODICITY"));
	}

	public int putIndexData(ArmUserInfo user, Map<String, String> values) {
		String sql = "{call dbo.put_indices_proc "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?}";

		return ems.executeUpdate(AccessAction.OTHER, user, sql,
				values.get("ID_BB_GLOBAL"),
				values.get("ID_BB_SEC_NUM_SRC"),
				values.get("ID_BB_SEC_NUM_DES"),
				values.get("PARSEKYABLE_DES"),
				values.get("PARSEKYABLE_DES_SOURCE"),
				values.get("TICKER"),
				values.get("SECURITY_NAME"),
				values.get("NAME"),
				values.get("SHORT_NAME"),
				values.get("SECURITY_TYP"),
				values.get("MARKET_SECTOR_DES"),
				values.get("FEED_SOURCE"),
				values.get("CRNCY"),
				values.get("EQY_FUND_CRNCY"));
	}

	public int putBondsData(ArmUserInfo user, Map<String, String> values) {
		String sql = "{call dbo.put_bond_proc " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?}";

		return ems.executeUpdate(AccessAction.OTHER, user, sql,
				values.get("ID_BB_UNIQUE"),
				values.get("ID_BB_COMPANY"),
				values.get("ID_CUSIP"),
				values.get("ID_ISIN"),
				values.get("PARSEKYABLE_DES"),
				values.get("SECURITY_NAME"),
				values.get("SECURITY_SHORT_DES"),
				values.get("SHORT_NAME"),
				values.get("TICKER"),
				values.get("NAME"),
				values.get("CRNCY"),
				Utils.parseDate(values.get("MATURITY")),
				values.get("MTY_TYP"),
				values.get("COLLAT_TYP"),
				values.get("SECURITY_DES"),
				values.get("INDUSTRY_SECTOR"),
				values.get("SECURITY_TYP"),
				values.get("COUNTRY_ISO"),
				values.get("CNTRY_OF_DOMICILE"),
				values.get("PX_METHOD"),
				values.get("PAYMENT_RANK"),
				values.get("SINKABLE"),
				values.get("DAY_CNT_DES"),
				Utils.parseDate(values.get("ANNOUNCE_DT")),
				Utils.parseDate(values.get("INT_ACC_DT")),
				Utils.parseDate(values.get("FIRST_SETTLE_DT")),
				Utils.parseDate(values.get("FIRST_CPN_DT")),
				values.get("CPN_CRNCY"),
				values.get("FIXED"),
				new Double(values.get("CPN")),
				new Double(values.get("PAR_AMT")));
	}

	public int putFuturesData(ArmUserInfo user, Map<String, String> values) {
		String sql = "{call dbo.put_futures_proc "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?}";

		return ems.executeUpdate(AccessAction.OTHER, user, sql,
				values.get("ID_BB_GLOBAL"),
				values.get("ID_BB"),
				values.get("ID_BB_UNIQUE"),
				values.get("ID_BB_SEC_NUM_DES"),
				values.get("ID_BB_SEC_NUM_SRC"),
				values.get("PARSEKYABLE_DES"),
				values.get("PARSEKYABLE_DES_SOURCE"),
				values.get("SECURITY_TYP"),
				values.get("MARKET_SECTOR_DES"),
				values.get("FEED_SOURCE"),
				values.get("FUTURES_CATEGORY"),
				values.get("FUT_TRADING_UNITS"),
				values.get("TICKER"),
				values.get("SECURITY_NAME"),
				values.get("NAME"),
				values.get("SHORT_NAME"),
				values.get("EXCH_CODE"),
				values.get("CRNCY"),
				values.get("QUOTED_CRNCY"),
				values.get("FUT_TICK_SIZE"),
				values.get("FUT_TICK_VAL"),
				values.get("FUT_CONT_SIZE"),
				values.get("FUT_VAL_PT"),
				values.get("FUT_FIRST_TRADE_DT"),
				values.get("LAST_TRADEABLE_DT"),
				values.get("FUT_GEN_MONTH"),
				values.get("QUOTE_UNITS"));
	}

	public int putOptionsData(ArmUserInfo user, Map<String, String> values) {
		String sql = "{call dbo.put_options_proc "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?}";

		return ems.executeUpdate(AccessAction.OTHER, user, sql,
				values.get("ID_BB_GLOBAL"),
				values.get("ID_BB"),
				values.get("ID_BB_UNIQUE"),
				values.get("ID_BB_SEC_NUM_DES"),
				values.get("ID_BB_SEC_NUM_SRC"),
				values.get("PARSEKYABLE_DES"),
				values.get("PARSEKYABLE_DES_SOURCE"),
				values.get("SECURITY_TYP"),
				values.get("MARKET_SECTOR_DES"),
				values.get("FEED_SOURCE"),
				values.get("FUTURES_CATEGORY"),
				values.get("FUT_TRADING_UNITS"),
				values.get("TICKER"),
				values.get("SECURITY_NAME"),
				values.get("NAME"),
				values.get("SHORT_NAME"),
				values.get("EXCH_CODE"),
				values.get("CRNCY"),
				values.get("QUOTED_CRNCY"),
				values.get("TICK_SIZE_REALTIME"),
				values.get("OPT_TICK_VAL"),
				values.get("OPT_CONT_SIZE"),
				values.get("OPT_FIRST_TRADE_DT"),
				values.get("LAST_TRADEABLE_DT"),
				values.get("QUOTE_UNITS"),
				values.get("OPT_STRIKE_PX"));
	}
}
