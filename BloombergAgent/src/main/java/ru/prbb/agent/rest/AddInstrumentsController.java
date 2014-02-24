package ru.prbb.agent.rest;

import java.util.ArrayList;
import java.util.HashMap;
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

import ru.prbb.agent.service.BloombergServices;

/**
 * Ввод новой акции<br>
 * Ввод нового индекса<br>
 * Ввод новой облигации<br>
 * Ввод нового фьючерса
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/AddInstruments")
public class AddInstrumentsController {

	private final Log log = LogFactory.getLog(getClass());

	private static final String АКЦИЯ = "Акция";
	private static final String ИНДЕКС = "Индекс";
	private static final String ОБЛИГАЦИЯ = "Облигация";
	private static final String ФЬЮЧЕРС = "Фьючерс";

	private final BloombergServices bs;

	@Autowired
	public AddInstrumentsController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public @ResponseBody
	String get() {
		log.trace("GET");

		return "Добавление нового инструмента.\n"
				+ "Ввод новой акции, нового индекса, новой облигации и нового фьючерса\n"
				+ "\n"
				+ "Параметр\n"
				+ "instruments = [ type:code ]\n"
				+ "type = (Акция, Индекс, Облигация, Фьючерс)\n"
				+ "\n"
				+ "Результат\n"
				+ "type -> [ code -> [ { field, value } ] ]\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Map<String, Map<String, String>>> post(@RequestParam String[] instruments) {
		log.trace("POST");

		final List<String> shares = new ArrayList<>();
		final List<String> indexes = new ArrayList<>();
		final List<String> bonds = new ArrayList<>();
		final List<String> futures = new ArrayList<>();

		for (String item : instruments) {
			final int p = item.indexOf(':');
			final String type = item.substring(0, p);
			final String code = item.substring(p + 1);

			if (АКЦИЯ.equals(type)) {
				shares.add(code);
				continue;
			}
			if (ИНДЕКС.equals(type)) {
				indexes.add(code);
				continue;
			}
			if (ОБЛИГАЦИЯ.equals(type)) {
				bonds.add(code);
				continue;
			}
			if (ФЬЮЧЕРС.equals(type)) {
				futures.add(code);
				continue;
			}
		}

		Map<String, Map<String, Map<String, String>>> res = new HashMap<>();

		if (!shares.isEmpty()) {
			Map<String, Map<String, String>> d = processShares(shares);
			res.put(АКЦИЯ, d);
		}

		if (!indexes.isEmpty()) {
			Map<String, Map<String, String>> d = processIndexes(indexes);
			res.put(ИНДЕКС, d);
		}

		if (!bonds.isEmpty()) {
			Map<String, Map<String, String>> d = processBonds(bonds);
			res.put(ОБЛИГАЦИЯ, d);
		}

		if (!futures.isEmpty()) {
			Map<String, Map<String, String>> d = processFutures(futures);
			res.put(ФЬЮЧЕРС, d);
		}

		return res;
	}

	private Map<String, Map<String, String>> processShares(List<String> codes) {
		log.trace("processShares");

		final String[] fields = { "ID_BB_GLOBAL", "ID_BB", "ID_BB_UNIQUE", "ID_BB_SEC_NUM_DES", "ID_BB_SEC_NUM_SRC", "ID_ISIN", "ID_CUSIP",
				"PARSEKYABLE_DES", "PARSEKYABLE_DES_SOURCE", "SECURITY_TYP", "MARKET_SECTOR_DES", "FEED_SOURCE", "TICKER", "SECURITY_NAME",
				"NAME", "SHORT_NAME", "EQY_PRIM_EXCH", "EXCH_CODE", "EQY_FUND_IND", "INDUSTRY_GROUP", "INDUSTRY_SUBGROUP",
				"ADR_SH_PER_ADR", "CRNCY", "EQY_FUND_CRNCY", "EQY_PRIM_SECURITY_CRNCY", "ADR_CRNCY", "BEST_CRNCY_ISO", "DVD_CRNCY",
				"EARN_EST_CRNCY", "EQY_FUND_TICKER", "EQY_FISCAL_YR_END" };

		return bs.executeReferenceDataRequest("Ввод новой акции", toArray(codes), fields);
	}

	private Map<String, Map<String, String>> processIndexes(List<String> codes) {
		log.trace("processIndexes");

		final String[] fields = { "ID_BB_GLOBAL", "ID_BB_SEC_NUM_SRC", "ID_BB_SEC_NUM_DES", "PARSEKYABLE_DES", "PARSEKYABLE_DES_SOURCE",
				"TICKER", "SECURITY_NAME", "NAME", "SHORT_NAME", "SECURITY_TYP", "MARKET_SECTOR_DES", "FEED_SOURCE", "CRNCY",
				"EQY_FUND_CRNCY" };

		return bs.executeReferenceDataRequest("Ввод нового индекса", toArray(codes), fields);
	}

	private Map<String, Map<String, String>> processBonds(List<String> codes) {
		log.trace("processBonds");

		final String[] fields = { "ID_BB_UNIQUE", "ID_BB_COMPANY", "ID_CUSIP", "ID_ISIN", "PARSEKYABLE_DES", "SECURITY_NAME",
				"SECURITY_SHORT_DES", "SHORT_NAME", "TICKER", "NAME", "CRNCY", "MATURITY", "MTY_TYP", "COLLAT_TYP", "SECURITY_DES",
				"INDUSTRY_SECTOR", "SECURITY_TYP", "COUNTRY_ISO", "CNTRY_OF_DOMICILE", "PX_METHOD", "PAYMENT_RANK", "SINKABLE",
				"DAY_CNT_DES", "ANNOUNCE_DT", "INT_ACC_DT", "FIRST_SETTLE_DT", "FIRST_CPN_DT", "CPN_CRNCY", "FIXED", "CPN", "PAR_AMT" };

		return bs.executeReferenceDataRequest("Ввод новой облигации", toArray(codes), fields);
	}

	private Map<String, Map<String, String>> processFutures(List<String> codes) {
		log.trace("processFutures");

		final String[] fields = { "ID_BB_GLOBAL", "ID_BB", "ID_BB_UNIQUE", "ID_BB_SEC_NUM_DES", "ID_BB_SEC_NUM_SRC", "PARSEKYABLE_DES",
				"PARSEKYABLE_DES_SOURCE", "SECURITY_TYP", "MARKET_SECTOR_DES", "FEED_SOURCE", "FUTURES_CATEGORY", "FUT_TRADING_UNITS",
				"TICKER", "SECURITY_NAME", "NAME", "SHORT_NAME", "EXCH_CODE", "CRNCY", "QUOTED_CRNCY", "FUT_TICK_SIZE", "FUT_TICK_VAL",
				"FUT_CONT_SIZE", "FUT_VAL_PT", "FUT_FIRST_TRADE_DT", "LAST_TRADEABLE_DT", "FUT_GEN_MONTH" };

		return bs.executeReferenceDataRequest("Ввод нового фьючерса", toArray(codes), fields);
	}

	private String[] toArray(List<String> codes) {
		return codes.toArray(new String[codes.size()]);
	}
}
