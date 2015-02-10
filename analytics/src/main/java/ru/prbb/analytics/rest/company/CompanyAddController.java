package ru.prbb.analytics.rest.company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.BloombergServicesA;
import ru.prbb.analytics.repo.bloomberg.RequestBDHDao;
import ru.prbb.analytics.repo.bloomberg.RequestBDHepsDao;
import ru.prbb.analytics.repo.bloomberg.RequestBDPDao;
import ru.prbb.analytics.repo.bloomberg.RequestBDPovrDao;
import ru.prbb.analytics.repo.bloomberg.RequestBDSDao;
import ru.prbb.analytics.repo.company.CompanyAddDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Добавление компаний
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/CompanyAdd")
public class CompanyAddController
		extends BaseController
{

	@Autowired
	private BloombergServicesA bs;
	@Autowired
	private CompanyAddDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData add(
			@RequestParam String[] codes)
	{
		log.info("POST CompanyAdd: codes={}", (Object) codes);
		String[] fields = new String[] {
				"ID_BB_GLOBAL", "ID_BB", "ID_BB_UNIQUE", "ID_BB_SEC_NUM_DES", "ID_BB_SEC_NUM_SRC",
				"ID_ISIN", "ID_CUSIP", "PARSEKYABLE_DES", "PARSEKYABLE_DES_SOURCE", "SECURITY_TYP",
				"MARKET_SECTOR_DES", "FEED_SOURCE", "TICKER", "SECURITY_NAME", "NAME", "SHORT_NAME",
				"EQY_PRIM_EXCH", "EXCH_CODE", "EQY_FUND_IND", "INDUSTRY_GROUP", "INDUSTRY_SUBGROUP",
				"ADR_SH_PER_ADR", "CRNCY", "EQY_FUND_CRNCY", "EQY_PRIM_SECURITY_CRNCY", "ADR_CRNCY",
				"BEST_CRNCY_ISO", "DVD_CRNCY", "EARN_EST_CRNCY", "EQY_FUND_TICKER", "EQY_FISCAL_YR_END",
				"PRIMARY_PERIODICITY"};
		Map<String, Map<String, String>> answer = bs.executeReferenceDataRequest("CompanyAdd", codes, fields);
		return new ResultData(dao.execute(codes, answer));
	}

	@Autowired
	private RequestBDHDao reqBDHdao;
	@Autowired
	private RequestBDHepsDao reqBDHEPSdao;
	@Autowired
	private RequestBDPDao reqBDPdao;
	@Autowired
	private RequestBDPovrDao reqBDPOVRdao;
	@Autowired
	private RequestBDSDao reqBDSdao;

	@RequestMapping(value = "/Bloom", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData addBloom(
			@RequestParam String[] codes)
	{
		log.info("POST CompanyAdd/Bloom: codes={}", (Object) codes);
		final Map<String, StringBuilder> info = new HashMap<>();
		for (String code : codes) {
			info.put(code, new StringBuilder("Загрузка: "));
		}

		Map<String, String> periodicity = dao.createPeriodicity(info, codes,
				bs.executeReferenceDataRequest("CompanyAdd", codes, new String[] { "PRIMARY_PERIODICITY" }));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Utils.LOCALE);
/*
		Calendar cYear10 = Calendar.getInstance(Utils.LOCALE);
		cYear10.add(Calendar.YEAR, -10);
		cYear10.set(Calendar.MONTH, Calendar.JANUARY);
		cYear10.set(Calendar.DAY_OF_MONTH, 1);
		String dateStartYear = sdf.format(cYear10.getTime());
*/
		Calendar cYear2k = Calendar.getInstance(Utils.LOCALE);
		cYear2k.clear();
		cYear2k.set(Calendar.YEAR, 2000);
		cYear2k.set(Calendar.MONTH, Calendar.JANUARY);
		cYear2k.set(Calendar.DAY_OF_MONTH, 1);
		String dateStartYear = sdf.format(cYear2k.getTime());

		Calendar cToday = Calendar.getInstance(Utils.LOCALE);
		String dateEnd = sdf.format(cToday.getTime());

		Calendar cYear1 = Calendar.getInstance(Utils.LOCALE);
		cYear1.add(Calendar.YEAR, -1);
		String dateStartDay = sdf.format(cYear1.getTime());

		Set<String> aCurrencies = new HashSet<>();
		List<String> aCode_crncy = new ArrayList<>(codes.length);

		Set<String> qaCurrencies = new HashSet<>();
		List<String> qaCode_crncy = new ArrayList<>(codes.length);

		Set<String> saCurrencies = new HashSet<>();
		List<String> saCode_crncy = new ArrayList<>(codes.length);

		List<CompanyAddDao.InfoItem> infoAdd = dao.getCompanyInfo(codes);
		for (CompanyAddDao.InfoItem item : infoAdd) {
			String text = item + "; ";
			if (periodicity.containsKey(item.code)) {
				/*
				PRIMARY_PERIODICITY

				Returns the company's reporting frequency, which can be used to determine what periodicity 
				was used in calculating trailing and daily ratio fields.

				"Quarterly and Annual" -- Quarterly data will be used in trailing and daily fields.
				"Semi-Annual and Annual" -- Semi-Annual data will be used in trailing and daily fields.
				"Annual" - Annual data will be used in trailing and daily fields.

				по нему можно понять как компания отчитывается.
				соотвественно это нужно учитывать в запросах bdh с eps
				 */
				String period = periodicity.get(item.code);

				if ("Quarterly and Annual".equals(period)) {
					qaCurrencies.add(item.crncy);
					qaCode_crncy.add(item.name + '|' + item.crncy);
					text += " Квартальный";
				}

				if ("Semi-Annual and Annual".equals(period)) {
					saCurrencies.add(item.crncy);
					saCode_crncy.add(item.name + '|' + item.crncy);
					text += " Полугодовой";
				}

				aCurrencies.add(item.crncy);
				aCode_crncy.add(item.name + '|' + item.crncy);
				text += " Годовой";
			} else {
				text += " нет";
			}
			info.get(item.code).append(text);
		}

		// Запросы BDH
		if (!aCode_crncy.isEmpty()) {
			try {
				String[] securities = toArray(aCode_crncy);
				reqBDHdao.execute(securities,
						bs.executeBdhRequest("Добавление компаний BDH YEARLY",
								dateStartYear, dateEnd, "YEARLY", "CALENDAR",
								toArray(aCurrencies), securities, toArray(
										"EQY_WEIGHTED_AVG_PX",
										"BOOK_VAL_PER_SH",
										"IS_AVG_NUM_SH_FOR_EPS",
										"IS_COMP_EPS_ADJUSTED",
										"OPER_ROE",
										"RETENTION_RATIO")));
			} catch (Exception e) {
				info.put("BDH YEARLY", new StringBuilder("Запрос BDH YEARLY:").append(e.getMessage()));
			}
		}
		if (!aCode_crncy.isEmpty()) {
			try {
				String[] securities = toArray(aCode_crncy);
				reqBDHdao.execute(securities,
						bs.executeBdhRequest("Добавление компаний ",
								dateStartDay, dateEnd, "DAILY", "CALENDAR",
								toArray(aCurrencies), securities, toArray(
										"EQY_WEIGHTED_AVG_PX",
										"TOT_BUY_REC",
										"TOT_HOLD_REC",
										"TOT_SELL_REC",
										"PX_LAST",
										"PX_VOLUME")));
			} catch (Exception e) {
				info.put("BDH DAILY", new StringBuilder("Запрос BDH DAILY:").append(e.getMessage()));
			}
		}

		// Запрос BDH EPS
		try {
			String[] securities = toArray(aCode_crncy);
			reqBDHEPSdao.execute(securities,
					bs.executeBdhEpsRequest("Добавление компаний BDH EPS YEARLY",
							dateStartYear, dateEnd, "YEARLY", "CALENDAR",
							toArray(aCurrencies), securities, toArray(
									"BEST_EBITDA",
									"EBITDA",
									"NET_REV",
									"IS_EPS",
									"EQY_DPS",
									"IS_COMP_EPS_ADJUSTED",
									"IS_BASIC_EPS_CONT_OPS",
									"IS_DIL_EPS_CONT_OPS",
									"SALES_REV_TURN",
									"IS_AVG_NUM_SH_FOR_EPS",
									"OPER_ROE",
									"PROF_MARGIN",
									"OPER_MARGIN",
									"EQY_DVD_YLD_IND")));
		} catch (Exception e) {
			info.put("BDH EPS YEARLY", new StringBuilder("Запрос BDH EPS YEARLY:").append(e.getMessage()));
		}
		if (!qaCode_crncy.isEmpty()) {
			try {
				String[] securities = toArray(qaCode_crncy);
				reqBDHEPSdao.execute(securities,
						bs.executeBdhEpsRequest("Добавление компаний BDH EPS QUARTERLY",
								dateStartYear, dateEnd, "QUARTERLY", "CALENDAR",
								toArray(qaCurrencies), securities, toArray(
										"BEST_EBITDA",
										"EBITDA",
										"NET_REV",
										"IS_EPS",
										"EQY_DPS",
										"IS_COMP_EPS_ADJUSTED",
										"IS_BASIC_EPS_CONT_OPS",
										"IS_DIL_EPS_CONT_OPS",
										"SALES_REV_TURN",
										"IS_AVG_NUM_SH_FOR_EPS",
										"OPER_ROE",
										"PROF_MARGIN",
										"OPER_MARGIN",
										"EQY_DVD_YLD_IND")));
			} catch (Exception e) {
				info.put("BDH EPS QUARTERLY",
						new StringBuilder("Запрос BDH EPS QUARTERLY:").append(e.getMessage()));
			}
		}
		if (!saCode_crncy.isEmpty()) {
			try {
				String[] securities = toArray(saCode_crncy);
				reqBDHEPSdao.execute(securities,
						bs.executeBdhEpsRequest("Добавление компаний BDH EPS SEMI_ANNUALLY",
								dateStartYear, dateEnd, "SEMI_ANNUALLY", "CALENDAR",
								toArray(saCurrencies), securities, toArray(
										"BEST_EBITDA",
										"EBITDA",
										"NET_REV",
										"IS_EPS",
										"EQY_DPS",
										"IS_COMP_EPS_ADJUSTED",
										"IS_BASIC_EPS_CONT_OPS",
										"IS_DIL_EPS_CONT_OPS",
										"SALES_REV_TURN",
										"IS_AVG_NUM_SH_FOR_EPS",
										"OPER_ROE",
										"PROF_MARGIN",
										"OPER_MARGIN",
										"EQY_DVD_YLD_IND")));
			} catch (Exception e) {
				info.put("BDH EPS SEMI_ANNUALLY",
						new StringBuilder("Запрос BDH EPS SEMI_ANNUALLY:").append(e.getMessage()));
			}
		}

		// Запрос BDP
		try {
			reqBDPdao.execute(codes,
					bs.executeBdpRequest("Добавление компаний BDP", codes, toArray(
							"ANNOUNCEMENT_DT",
							"BEST_EPS_GAAP",
							"BEST_EPS_GAAP_1WK_CHG",
							"BEST_EPS_GAAP_3MO_CHG",
							"BEST_EPS_GAAP_4WK_CHG",
							"BS_TOT_LIAB2",
							"EBITDA",
							"EQY_DVD_YLD_IND",
							"EQY_RAW_BETA",
							"EQY_WEIGHTED_AVG_PX",
							"HIGH_52WEEK",
							"LOW_52WEEK",
							"OPER_ROE",
							"PE_RATIO",
							"PX_LAST",
							"PX_VOLUME")));
		} catch (Exception e) {
			info.put("BDP", new StringBuilder("Запрос BDP:").append(e.getMessage()));
		}

		// Запрос BDP override
		try {
			String[] securities = toArray(aCode_crncy);
			String over = "BST";
			reqBDPOVRdao.execute(securities, over,
					bs.executeBdpRequestOverrideQuarter("Добавление компаний BDP override",
							securities, toArray("BEST_EPS_GAAP", "BEST_BPS"), toArray(aCurrencies), over));
		} catch (Exception e) {
			info.put("", new StringBuilder("Запрос BDP over:").append(e.getMessage()));
		}

		// Запрос BDS
		try {
			reqBDSdao.execute(codes,
					bs.executeBdsRequest("Добавление компаний BDS", codes,
							toArray("BLOOMBERG_PEERS", "BEST_ANALYST_RECS_BULK")));
		} catch (Exception e) {
			info.put("BDS", new StringBuilder("Запрос BDS:").append(e.getMessage()));
		}

		final List<String[]> infoArr = new ArrayList<>(codes.length);
		for (Entry<String, StringBuilder> entry : info.entrySet()) {
			StringBuilder text = entry.getValue();
			infoArr.add(new String[] { entry.getKey(), text.toString() });
		}
		return new ResultData(infoArr);
	}

	private String[] toArray(Collection<String> c) {
		return c.toArray(new String[c.size()]);
	}

	private String[] toArray(String... s) {
		return s;
	}
}
