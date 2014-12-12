package ru.prbb.middleoffice.rest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.repo.BloombergServicesM;
import ru.prbb.middleoffice.repo.services.NewInstrumentDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Ввод нового инструмента
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/NewInstrument")
public class NewInstrumentController
		extends BaseController
{

	private static final String АКЦИЯ = "Акция";
	private static final String ИНДЕКС = "Индекс";
	private static final String ОБЛИГАЦИЯ = "Облигация";
	private static final String ФЬЮЧЕРС = "Фьючерс";
	private static final String ОПЦИОН = "Опцион";

	@Autowired
	private BloombergServicesM bs;

	@Autowired
	private NewInstrumentDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData postAdd(
			@RequestParam String[] instruments)
	{
		final List<String> shares = new ArrayList<>();
		final List<String> indexes = new ArrayList<>();
		final List<String> bonds = new ArrayList<>();
		final List<String> futures = new ArrayList<>();
		final List<String> options = new ArrayList<>();

		final List<String[]> info = new ArrayList<>();
		for (String item : instruments) {
			log.info("POST NewInstrument: instrument={}", item);
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
			if (ОПЦИОН.equals(type)) {
				options.add(code);
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

		if (!options.isEmpty()) {
			processOptions(info, options);
		}

		return new ResultData(info);
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
				"EQY_FISCAL_YR_END",
				"PRIMARY_PERIODICITY"
		};

		final Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Ввод новой акции",
						codes.toArray(new String[codes.size()]), fields);

		for (String security : codes) {
			final Map<String, String> values = answer.get(security);
			log.debug(security + ": " + values);

			if (null == values) {
				updateInfo(info, security, АКЦИЯ, "Нет данных для " + security);
				continue;
			}

			if (values.containsKey("securityError")) {
				updateInfo(info, security, АКЦИЯ, values.get("securityError"));
				continue;
			}

			try {
				dao.putSecurityData(values);
				updateInfo(info, security, АКЦИЯ, "Загружено.");
			} catch (Exception e) {
				log.error("Ошибка при сохранении АКЦИЯ: " + security, e);
				updateInfo(info, security, АКЦИЯ, "Ошибка при сохранении: " + e);
			}
		}
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
			log.debug(security + ": " + values);

			if (null == values) {
				updateInfo(info, security, ИНДЕКС, "Нет данных для " + security);
				continue;
			}

			if (values.containsKey("securityError")) {
				updateInfo(info, security, ИНДЕКС, values.get("securityError"));
				continue;
			}

			try {
				dao.putIndexData(values);
				updateInfo(info, security, ИНДЕКС, "Загружено");
			} catch (Exception e) {
				log.error("Ошибка при сохранении ИНДЕКС: " + security, e);
				updateInfo(info, security, ИНДЕКС, "Ошибка при сохранении: " + e);
			}
		}
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
			log.debug(security + ": " + values);

			if (null == values) {
				updateInfo(info, security, ОБЛИГАЦИЯ, "Нет данных для " + security);
				continue;
			}

			if (values.containsKey("securityError")) {
				updateInfo(info, security, ОБЛИГАЦИЯ, values.get("securityError"));
				continue;
			}

			try {
				dao.putBondsData(values);
				updateInfo(info, security, ОБЛИГАЦИЯ, "Загружено");
			} catch (Exception e) {
				log.error("Ошибка при сохранении ОБЛИГАЦИЯ: " + security, e);
				updateInfo(info, security, ОБЛИГАЦИЯ, "Ошибка при сохранении: " + e);
			}
		}
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
				"QUOTE_UNITS",
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
			log.debug(security + ": " + values);

			if (null == values) {
				updateInfo(info, security, ФЬЮЧЕРС, "Нет данных для " + security);
				continue;
			}

			if (values.containsKey("securityError")) {
				updateInfo(info, security, ФЬЮЧЕРС, values.get("securityError"));
				continue;
			}

			try {
				dao.putFuturesData(values);
				updateInfo(info, security, ФЬЮЧЕРС, "Загружено");
			} catch (Exception e) {
				log.error("Ошибка при сохранении ФЬЮЧЕРС: " + security, e);
				updateInfo(info, security, ФЬЮЧЕРС, "Ошибка при сохранении: " + e);
			}
		}
	}

	private void processOptions(List<String[]> info, List<String> codes) {
		final String[] fields = {
				"ID_BB_GLOBAL",
				"ID_BB",
				"ID_BB_UNIQUE",
				"ID_BB_SEC_NUM_DES",
				"ID_BB_SEC_NUM_SRC",
				"UNDERLYING_SECURITY_DES",
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
				"TICK_SIZE_REALTIME",
				"OPT_TICK_VAL",
				"OPT_CONT_SIZE",
				"OPT_FIRST_TRADE_DT",
				"LAST_TRADEABLE_DT",
				"QUOTE_UNITS",
				"OPT_STRIKE_PX"
		};

		final Map<String, Map<String, String>> answer =
				bs.executeReferenceDataRequest("Ввод нового опциона",
						codes.toArray(new String[codes.size()]), fields);

		for (String security : codes) {
			final Map<String, String> values = answer.get(security);
			log.debug(security + ": " + values);

			if (null == values) {
				updateInfo(info, security, ОПЦИОН, "Нет данных для " + security);
				continue;
			}

			if (values.containsKey("securityError")) {
				updateInfo(info, security, ОПЦИОН, values.get("securityError"));
				continue;
			}

			try {
				dao.putOptionsData(values);
				updateInfo(info, security, ОПЦИОН, "Загружено");
			} catch (Exception e) {
				log.error("Ошибка при сохранении ОПЦИОН: " + security, e);
				updateInfo(info, security, ОПЦИОН, "Ошибка при сохранении: " + e);
			}
		}
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
