package ru.prbb.jobber.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.repo.BloombergDao;
import ru.prbb.jobber.repo.BloombergServices;

/**
 * Ввод новой акции<br>
 * Ввод нового индекса<br>
 * Ввод новой облигации<br>
 * Ввод нового фьючерса
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/InstrumentAdd")
public class AddInstrumentsController
{
	private static final String АКЦИЯ = "Акция";
	private static final String ИНДЕКС = "Индекс";
	private static final String ОБЛИГАЦИЯ = "Облигация";
	private static final String ФЬЮЧЕРС = "Фьючерс";

	private static final Log log = LogFactory.getLog(AddInstrumentsController.class);

	@Autowired
	private BloombergServices bs;
	@Autowired
	private BloombergDao dao;

	private class ResultArrayList extends ArrayList<String[]> {
		private static final long serialVersionUID = 1L;

		boolean add(String code, String type, String status) {
			return add(new String[] { code, type, status });
		}

		void update(String code, String type, String state) {
			for (String[] item : this) {
				if (code.equals(item[0]) && type.equals(item[1])) {
					item[2] = state;
					break;
				}
			}
		}
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<String[]> execute(
			@RequestParam String[] instruments) {
		log.info("InstrumentAdd " + instruments);

		final List<String> shares = new ArrayList<>();
		final List<String> indexes = new ArrayList<>();
		final List<String> bonds = new ArrayList<>();
		final List<String> futures = new ArrayList<>();

		ResultArrayList res = new ResultArrayList();
		for (String item : instruments) {
			final int p = item.indexOf('|');
			final String type = item.substring(0, p);
			final String code = item.substring(p + 1);

			if (АКЦИЯ.equals(type)) {
				shares.add(code);
				res.add(code, type, "Добавлено в список.");
				continue;
			}
			if (ИНДЕКС.equals(type)) {
				indexes.add(code);
				res.add(code, type, "Добавлено в список.");
				continue;
			}
			if (ОБЛИГАЦИЯ.equals(type)) {
				bonds.add(code);
				res.add(code, type, "Добавлено в список.");
				continue;
			}
			if (ФЬЮЧЕРС.equals(type)) {
				futures.add(code);
				res.add(code, type, "Добавлено в список.");
				continue;
			}

			res.add(code, type, "Неизвестный тип инструмента.");
		}

		if (!shares.isEmpty()) {
			processShares(shares, res);
		}

		if (!indexes.isEmpty()) {
			processIndexes(indexes, res);
		}

		if (!bonds.isEmpty()) {
			processBonds(bonds, res);
		}

		if (!futures.isEmpty()) {
			processFutures(futures, res);
		}

		return res;
	}

	private void processShares(List<String> codes, ResultArrayList info) {
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
			// security -> {field, value}
			final Map<String, String> values = answer.get(security);

			if (values.containsKey("securityError")) {
				info.update(security, АКЦИЯ, values.get("securityError"));
			} else {
				try {
					dao.putSharesData(values);
					info.update(security, АКЦИЯ, "Загружено.");
				} catch (Exception e) {
					log.error(e);
					info.update(security, АКЦИЯ, "Ошибка при сохранении: " + e.getMessage());
				}
			}
		}
	}

	private void processIndexes(List<String> codes, ResultArrayList info) {
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
			// security -> {field, value}
			final Map<String, String> values = answer.get(security);

			if (values.containsKey("securityError")) {
				info.update(security, ИНДЕКС, values.get("securityError"));
			} else {
				try {
					dao.putIndexData(values);
					info.update(security, ИНДЕКС, "Загружено");
				} catch (Exception e) {
					log.error(e);
					info.update(security, ИНДЕКС, "Ошибка при сохранении: " + e.getMessage());
				}
			}
		}
	}

	private void processBonds(List<String> codes, ResultArrayList info) {
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
			// security -> {field, value}
			final Map<String, String> values = answer.get(security);

			if (values.containsKey("securityError")) {
				info.update(security, ОБЛИГАЦИЯ, values.get("securityError"));
			} else {
				try {
					dao.putBondsData(values);
					info.update(security, ОБЛИГАЦИЯ, "Загружено");
				} catch (Exception e) {
					log.error(e);
					info.update(security, ОБЛИГАЦИЯ, "Ошибка при сохранении: " + e.getMessage());
				}
			}
		}
	}

	private void processFutures(List<String> codes, ResultArrayList info) {
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
			// security -> {field, value}
			final Map<String, String> values = answer.get(security);

			if (values.containsKey("securityError")) {
				info.update(security, ФЬЮЧЕРС, values.get("securityError"));
			} else {
				try {
					dao.putFuturesData(values);
					info.update(security, ФЬЮЧЕРС, "Загружено");
				} catch (Exception e) {
					log.error(e);
					info.update(security, ФЬЮЧЕРС, "Ошибка при сохранении: " + e.getMessage());
				}
			}
		}
	}
}
