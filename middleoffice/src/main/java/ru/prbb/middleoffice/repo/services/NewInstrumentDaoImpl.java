/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.prbb.Utils;
import ru.prbb.middleoffice.repo.BloombergServicesM;

/**
 * Ввод нового инструмента
 * 
 * @author RBr
 */
@Repository
public class NewInstrumentDaoImpl implements NewInstrumentDao
{

	@Autowired
	private EntityManager em;

	private static final String АКЦИЯ = "Акция";
	private static final String ИНДЕКС = "Индекс";
	private static final String ОБЛИГАЦИЯ = "Облигация";
	private static final String ФЬЮЧЕРС = "Фьючерс";
	@Autowired
	private BloombergServicesM bs;

	@Override
	public List<String[]> execute(String[] instruments) {
		final List<String> shares = new ArrayList<>();
		final List<String> indexes = new ArrayList<>();
		final List<String> bonds = new ArrayList<>();
		final List<String> futures = new ArrayList<>();

		final List<String[]> info = new ArrayList<>();
		for (String item : instruments) {
			final int p = item.indexOf(':');
			final String type = item.substring(0, p);
			final String code = item.substring(p + 1);

			if (АКЦИЯ.equals(type)) {
				shares.add(code);
				info.add(new String[] { code, type, "Добавлено в список." });
				continue;
			}
			if (ИНДЕКС.equals(type)) {
				indexes.add(code);
				info.add(new String[] { code, type, "Добавлено в список." });
				continue;
			}
			if (ОБЛИГАЦИЯ.equals(type)) {
				bonds.add(code);
				info.add(new String[] { code, type, "Добавлено в список." });
				continue;
			}
			if (ФЬЮЧЕРС.equals(type)) {
				futures.add(code);
				info.add(new String[] { code, type, "Добавлено в список." });
				continue;
			}

			info.add(new String[] { code, type, "Неизвестный тип инструмента." });
		}

		if (!shares.isEmpty()) {
			processShares(info, shares);
		}

		if (!indexes.isEmpty()) {
			processIndexes(info, indexes);
		}

		if (!bonds.isEmpty()) {
			processBonds(info, bonds);
		}

		if (!futures.isEmpty()) {
			processFutures(info, futures);
		}

		return info;
	}

	private void processShares(List<String[]> info, List<String> codes) {
		final String[] fields = {
				"ID_BB_GLOBAL",
				"ID_BB",
				"ID_BB_UNIQUE",
				"ID_BB_SEC_NUM_DES",
				"ID_BB_SEC_NUM_SRC",
				"ID_ISIN",
				"ID_CUSIP",
				"PARSEKYABLE_DES",
				"PARSEKYABLE_DES_SOURCE",
				"SECURITY_TYP",
				"MARKET_SECTOR_DES",
				"FEED_SOURCE",
				"TICKER",
				"SECURITY_NAME",
				"NAME",
				"SHORT_NAME",
				"EQY_PRIM_EXCH",
				"EXCH_CODE",
				"EQY_FUND_IND",
				"INDUSTRY_GROUP",
				"INDUSTRY_SUBGROUP",
				"ADR_SH_PER_ADR",
				"CRNCY",
				"EQY_FUND_CRNCY",
				"EQY_PRIM_SECURITY_CRNCY",
				"ADR_CRNCY",
				"BEST_CRNCY_ISO",
				"DVD_CRNCY",
				"EARN_EST_CRNCY",
				"EQY_FUND_TICKER",
				"EQY_FISCAL_YR_END"
		};

		final Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Ввод новой акции",
						codes.toArray(new String[codes.size()]), fields);

		for (String security : codes) {
			final Map<String, String> values = answer.get(security);
			// TODO log.trace(security + ": " + values);

			if (null == values) {
				updateInfo(info, security, АКЦИЯ, "Нет данных для " + security);
				continue;
			}

			if (values.containsKey("securityError")) {
				updateInfo(info, security, АКЦИЯ, values.get("securityError"));
				continue;
			}

			try {
				putSecurityData(values);
				updateInfo(info, security, АКЦИЯ, "Загружено.");
			} catch (Exception e) {
				// TODO log.error(e);
				updateInfo(info, security, АКЦИЯ, "Ошибка при сохранении: " + e);
			}
		}
	}

	private int putSecurityData(Map<String, String> values) {
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
		return q.executeUpdate();
	}

	private void processIndexes(List<String[]> info, List<String> codes) {
		final String[] fields = {
				"ID_BB_GLOBAL",
				"ID_BB_SEC_NUM_SRC",
				"ID_BB_SEC_NUM_DES",
				"PARSEKYABLE_DES",
				"PARSEKYABLE_DES_SOURCE",
				"TICKER",
				"SECURITY_NAME",
				"NAME",
				"SHORT_NAME",
				"SECURITY_TYP",
				"MARKET_SECTOR_DES",
				"FEED_SOURCE",
				"CRNCY",
				"EQY_FUND_CRNCY"
		};

		final Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Ввод нового индекса",
						codes.toArray(new String[codes.size()]), fields);

		for (String security : codes) {
			final Map<String, String> values = answer.get(security);
			// TODO log.debug(security + ": " + values);

			if (null == values) {
				updateInfo(info, security, ИНДЕКС, "Нет данных для " + security);
				continue;
			}

			if (values.containsKey("securityError")) {
				updateInfo(info, security, ИНДЕКС, values.get("securityError"));
				continue;
			}

			try {
				putIndexData(values);
				updateInfo(info, security, ИНДЕКС, "Загружено");
			} catch (Exception e) {
				// TODO log.error(e);
				updateInfo(info, security, ИНДЕКС, "Ошибка при сохранении: " + e);
			}
		}
	}

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
		return q.executeUpdate();
	}

	private void processBonds(List<String[]> info, List<String> codes) {
		final String[] fields = {
				"ID_BB_UNIQUE",
				"ID_BB_COMPANY",
				"ID_CUSIP",
				"ID_ISIN",
				"PARSEKYABLE_DES",
				"SECURITY_NAME",
				"SECURITY_SHORT_DES",
				"SHORT_NAME",
				"TICKER",
				"NAME",
				"CRNCY",
				"MATURITY",
				"MTY_TYP",
				"COLLAT_TYP",
				"SECURITY_DES",
				"INDUSTRY_SECTOR",
				"SECURITY_TYP",
				"COUNTRY_ISO",
				"CNTRY_OF_DOMICILE",
				"PX_METHOD",
				"PAYMENT_RANK",
				"SINKABLE",
				"DAY_CNT_DES",
				"ANNOUNCE_DT",
				"INT_ACC_DT",
				"FIRST_SETTLE_DT",
				"FIRST_CPN_DT",
				"CPN_CRNCY",
				"FIXED",
				"CPN",
				"PAR_AMT"
		};

		final Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Ввод новой облигации",
						codes.toArray(new String[codes.size()]), fields);

		for (String security : codes) {
			final Map<String, String> values = answer.get(security);
			// TODO log.debug(security + ": " + values);

			if (null == values) {
				updateInfo(info, security, ОБЛИГАЦИЯ, "Нет данных для " + security);
				continue;
			}

			if (values.containsKey("securityError")) {
				updateInfo(info, security, ОБЛИГАЦИЯ, values.get("securityError"));
				continue;
			}

			try {
				putBondsData(values);
				updateInfo(info, security, ОБЛИГАЦИЯ, "Загружено");
			} catch (Exception e) {
				// TODO log.error(e);
				updateInfo(info, security, ОБЛИГАЦИЯ, "Ошибка при сохранении: " + e);
			}
		}
	}

	private int putBondsData(Map<String, String> values) {
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
		return q.executeUpdate();
	}

	private void processFutures(List<String[]> info, List<String> codes) {
		final String[] fields = {
				"ID_BB_GLOBAL",
				"ID_BB",
				"ID_BB_UNIQUE",
				"ID_BB_SEC_NUM_DES",
				"ID_BB_SEC_NUM_SRC",
				"PARSEKYABLE_DES",
				"PARSEKYABLE_DES_SOURCE",
				"SECURITY_TYP",
				"MARKET_SECTOR_DES",
				"FEED_SOURCE",
				"FUTURES_CATEGORY",
				"FUT_TRADING_UNITS",
				"TICKER",
				"SECURITY_NAME",
				"NAME",
				"SHORT_NAME",
				"EXCH_CODE",
				"CRNCY",
				"QUOTED_CRNCY",
				"FUT_TICK_SIZE",
				"FUT_TICK_VAL",
				"FUT_CONT_SIZE",
				"FUT_VAL_PT",
				"FUT_FIRST_TRADE_DT",
				"LAST_TRADEABLE_DT",
				"FUT_GEN_MONTH"
		};

		final Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Ввод нового фьючерса",
						codes.toArray(new String[codes.size()]), fields);

		for (String security : codes) {
			final Map<String, String> values = answer.get(security);
			// TODO log.debug(security + ": " + values);

			if (null == values) {
				updateInfo(info, security, ФЬЮЧЕРС, "Нет данных для " + security);
				continue;
			}

			if (values.containsKey("securityError")) {
				updateInfo(info, security, ФЬЮЧЕРС, values.get("securityError"));
				continue;
			}

			try {
				putNewFuturesData(values);
				updateInfo(info, security, ФЬЮЧЕРС, "Загружено");
			} catch (Exception e) {
				// TODO log.error(e);
				updateInfo(info, security, ФЬЮЧЕРС, "Ошибка при сохранении: " + e);
			}
		}
	}

	private int putNewFuturesData(Map<String, String> values) {
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
		return q.executeUpdate();
	}

	private void updateInfo(List<String[]> info, String code, String type, String state) {
		for (String[] item : info) {
			if (code.equals(item[0]) && type.equals(item[1])) {
				item[2] = state;
				break;
			}
		}
	}

}
