package ru.prbb.analytics.rest.company;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.company.CompaniesDao;

/**
 * Добавление компаний
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/CompanyAdd")
public class CompanyAddController
{
	@Autowired
	private CompaniesDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData add(
			@RequestParam String[] codes)
	{
		final List<String[]> info = new ArrayList<String[]>();

		for (String code : codes) {
			info.add(new String[] { code, "Загружено" });
		}
		//		final Map<String, Map<String, String>> a = bs.executeReferenceDataRequest("company-add", codes,
		//				new String[] {
		//						"ID_BB_GLOBAL", "ID_BB", "ID_BB_UNIQUE", "ID_BB_SEC_NUM_DES", "ID_BB_SEC_NUM_SRC",
		//						"ID_ISIN", "ID_CUSIP", "PARSEKYABLE_DES", "PARSEKYABLE_DES_SOURCE", "SECURITY_TYP",
		//						"MARKET_SECTOR_DES", "FEED_SOURCE", "TICKER", "SECURITY_NAME", "NAME", "SHORT_NAME",
		//						"EQY_PRIM_EXCH", "EXCH_CODE", "EQY_FUND_IND", "INDUSTRY_GROUP", "INDUSTRY_SUBGROUP",
		//						"ADR_SH_PER_ADR", "CRNCY", "EQY_FUND_CRNCY", "EQY_PRIM_SECURITY_CRNCY", "ADR_CRNCY",
		//						"BEST_CRNCY_ISO", "DVD_CRNCY", "EARN_EST_CRNCY", "EQY_FUND_TICKER", "EQY_FISCAL_YR_END"
		//				});
		//
		//		for (String code : codes) {
		//			final Map<String, String> answer = a.get(code);
		//			if (answer.containsKey("securityError")) {
		//				info.add(new String[] { code, answer.get("securityError") });
		//			} else {
		//				try {
		//					dbm.putSecurityData(
		//							answer.get("ID_BB_GLOBAL"),
		//							answer.get("ID_BB"),
		//							answer.get("ID_BB_UNIQUE"),
		//							answer.get("ID_BB_SEC_NUM_DES"),
		//							answer.get("ID_BB_SEC_NUM_SRC"),
		//							answer.get("ID_ISIN"),
		//							answer.get("ID_CUSIP"),
		//							answer.get("PARSEKYABLE_DES"),
		//							answer.get("PARSEKYABLE_DES_SOURCE"),
		//							answer.get("SECURITY_TYP"),
		//							answer.get("MARKET_SECTOR_DES"),
		//							answer.get("FEED_SOURCE"),
		//							answer.get("TICKER"),
		//							answer.get("SECURITY_NAME"),
		//							answer.get("NAME"),
		//							answer.get("SHORT_NAME"),
		//							answer.get("EQY_PRIM_EXCH"),
		//							answer.get("EXCH_CODE"),
		//							answer.get("EQY_FUND_IND"),
		//							answer.get("INDUSTRY_GROUP"),
		//							answer.get("INDUSTRY_SUBGROUP"),
		//							answer.get("ADR_SH_PER_ADR"),
		//							answer.get("CRNCY"),
		//							answer.get("EQY_FUND_CRNCY"),
		//							answer.get("EQY_PRIM_SECURITY_CRNCY"),
		//							answer.get("ADR_CRNCY"),
		//							answer.get("BEST_CRNCY_ISO"),
		//							answer.get("DVD_CRNCY"),
		//							answer.get("EARN_EST_CRNCY"),
		//							answer.get("EQY_FUND_TICKER"),
		//							answer.get("EQY_FISCAL_YR_END"));
		//					info.add(new String[] { code, "Загружено" });
		//				} catch (DataAccessException e) {
		//					info.add(new String[] { code, e.toString() });
		//				}
		//			}
		//		}

		return new ResultData(info);
	}
}
