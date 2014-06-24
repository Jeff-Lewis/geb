/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Ввод нового инструмента
 * 
 * @author RBr
 */
@Repository
public class NewInstrumentDaoImpl extends BaseDaoImpl implements NewInstrumentDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int putSecurityData(Map<String, String> values) {
		String sql = "{call dbo.put_equity_proc "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		int i = 1;
		q.setParameter(i++, values.get("ID_BB_GLOBAL"));
		q.setParameter(i++, values.get("ID_BB"));
		q.setParameter(i++, values.get("ID_BB_UNIQUE"));
		q.setParameter(i++, values.get("ID_BB_SEC_NUM_DES"));
		q.setParameter(i++, values.get("ID_BB_SEC_NUM_SRC"));
		q.setParameter(i++, values.get("ID_ISIN"));
		q.setParameter(i++, values.get("ID_CUSIP"));
		q.setParameter(i++, values.get("PARSEKYABLE_DES"));
		q.setParameter(i++, values.get("PARSEKYABLE_DES_SOURCE"));
		q.setParameter(i++, values.get("SECURITY_TYP"));
		q.setParameter(i++, values.get("MARKET_SECTOR_DES"));
		q.setParameter(i++, values.get("FEED_SOURCE"));
		q.setParameter(i++, values.get("TICKER"));
		q.setParameter(i++, values.get("SECURITY_NAME"));
		q.setParameter(i++, values.get("NAME"));
		q.setParameter(i++, values.get("SHORT_NAME"));
		q.setParameter(i++, values.get("EQY_PRIM_EXCH"));
		q.setParameter(i++, values.get("EXCH_CODE"));
		q.setParameter(i++, values.get("EQY_FUND_IND"));
		q.setParameter(i++, values.get("INDUSTRY_GROUP"));
		q.setParameter(i++, values.get("INDUSTRY_SUBGROUP"));
		q.setParameter(i++, values.get("ADR_SH_PER_ADR"));
		q.setParameter(i++, values.get("CRNCY"));
		q.setParameter(i++, values.get("EQY_FUND_CRNCY"));
		q.setParameter(i++, values.get("EQY_PRIM_SECURITY_CRNCY"));
		q.setParameter(i++, values.get("ADR_CRNCY"));
		q.setParameter(i++, values.get("BEST_CRNCY_ISO"));
		q.setParameter(i++, values.get("DVD_CRNCY"));
		q.setParameter(i++, values.get("EARN_EST_CRNCY"));
		q.setParameter(i++, values.get("EQY_FUND_TICKER"));
		q.setParameter(i++, values.get("EQY_FISCAL_YR_END"));
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int putIndexData(Map<String, String> values) {
		String sql = "{call dbo.put_indices_proc "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		int i = 1;
		q.setParameter(i++, values.get("ID_BB_GLOBAL"));
		q.setParameter(i++, values.get("ID_BB_SEC_NUM_SRC"));
		q.setParameter(i++, values.get("ID_BB_SEC_NUM_DES"));
		q.setParameter(i++, values.get("PARSEKYABLE_DES"));
		q.setParameter(i++, values.get("PARSEKYABLE_DES_SOURCE"));
		q.setParameter(i++, values.get("TICKER"));
		q.setParameter(i++, values.get("SECURITY_NAME"));
		q.setParameter(i++, values.get("NAME"));
		q.setParameter(i++, values.get("SHORT_NAME"));
		q.setParameter(i++, values.get("SECURITY_TYP"));
		q.setParameter(i++, values.get("MARKET_SECTOR_DES"));
		q.setParameter(i++, values.get("FEED_SOURCE"));
		q.setParameter(i++, values.get("CRNCY"));
		q.setParameter(i++, values.get("EQY_FUND_CRNCY"));
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int putBondsData(Map<String, String> values) {
		String sql = "{call dbo.put_bond_proc " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?}";
		Query q = em.createNativeQuery(sql);
		int i = 1;
		q.setParameter(i++, values.get("ID_BB_UNIQUE"));
		q.setParameter(i++, values.get("ID_BB_COMPANY"));
		q.setParameter(i++, values.get("ID_CUSIP"));
		q.setParameter(i++, values.get("ID_ISIN"));
		q.setParameter(i++, values.get("PARSEKYABLE_DES"));
		q.setParameter(i++, values.get("SECURITY_NAME"));
		q.setParameter(i++, values.get("SECURITY_SHORT_DES"));
		q.setParameter(i++, values.get("SHORT_NAME"));
		q.setParameter(i++, values.get("TICKER"));
		q.setParameter(i++, values.get("NAME"));
		q.setParameter(i++, values.get("CRNCY"));
		q.setParameter(i++, Utils.parseDate(values.get("MATURITY")));
		q.setParameter(i++, values.get("MTY_TYP"));
		q.setParameter(i++, values.get("COLLAT_TYP"));
		q.setParameter(i++, values.get("SECURITY_DES"));
		q.setParameter(i++, values.get("INDUSTRY_SECTOR"));
		q.setParameter(i++, values.get("SECURITY_TYP"));
		q.setParameter(i++, values.get("COUNTRY_ISO"));
		q.setParameter(i++, values.get("CNTRY_OF_DOMICILE"));
		q.setParameter(i++, values.get("PX_METHOD"));
		q.setParameter(i++, values.get("PAYMENT_RANK"));
		q.setParameter(i++, values.get("SINKABLE"));
		q.setParameter(i++, values.get("DAY_CNT_DES"));
		q.setParameter(i++, Utils.parseDate(values.get("ANNOUNCE_DT")));
		q.setParameter(i++, Utils.parseDate(values.get("INT_ACC_DT")));
		q.setParameter(i++, Utils.parseDate(values.get("FIRST_SETTLE_DT")));
		q.setParameter(i++, Utils.parseDate(values.get("FIRST_CPN_DT")));
		q.setParameter(i++, values.get("CPN_CRNCY"));
		q.setParameter(i++, values.get("FIXED"));
		q.setParameter(i++, new Double(values.get("CPN")));
		q.setParameter(i++, new Double(values.get("PAR_AMT")));
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int putNewFuturesData(Map<String, String> values) {
		String sql = "{call dbo.put_futures_proc "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		int i = 1;
		q.setParameter(i++, values.get("ID_BB_GLOBAL"));
		q.setParameter(i++, values.get("ID_BB"));
		q.setParameter(i++, values.get("ID_BB_UNIQUE"));
		q.setParameter(i++, values.get("ID_BB_SEC_NUM_DES"));
		q.setParameter(i++, values.get("ID_BB_SEC_NUM_SRC"));
		q.setParameter(i++, values.get("PARSEKYABLE_DES"));
		q.setParameter(i++, values.get("PARSEKYABLE_DES_SOURCE"));
		q.setParameter(i++, values.get("SECURITY_TYP"));
		q.setParameter(i++, values.get("MARKET_SECTOR_DES"));
		q.setParameter(i++, values.get("FEED_SOURCE"));
		q.setParameter(i++, values.get("FUTURES_CATEGORY"));
		q.setParameter(i++, values.get("FUT_TRADING_UNITS"));
		q.setParameter(i++, values.get("TICKER"));
		q.setParameter(i++, values.get("SECURITY_NAME"));
		q.setParameter(i++, values.get("NAME"));
		q.setParameter(i++, values.get("SHORT_NAME"));
		q.setParameter(i++, values.get("EXCH_CODE"));
		q.setParameter(i++, values.get("CRNCY"));
		q.setParameter(i++, values.get("QUOTED_CRNCY"));
		q.setParameter(i++, values.get("FUT_TICK_SIZE"));
		q.setParameter(i++, values.get("FUT_TICK_VAL"));
		q.setParameter(i++, values.get("FUT_CONT_SIZE"));
		q.setParameter(i++, values.get("FUT_VAL_PT"));
		q.setParameter(i++, values.get("FUT_FIRST_TRADE_DT"));
		q.setParameter(i++, values.get("LAST_TRADEABLE_DT"));
		q.setParameter(i++, values.get("FUT_GEN_MONTH"));
		storeSql(sql, q);
		return q.executeUpdate();
	}

}
