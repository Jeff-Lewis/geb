package ru.prbb.analytics.rest.bloomberg;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.EquitiesItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.BloombergServicesA;
import ru.prbb.analytics.repo.EquitiesDao;
import ru.prbb.analytics.repo.bloomberg.RequestBDPDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * BDP запрос
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/RequestBDP")
public class RequestBDPController
		extends BaseController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServicesA bs;
	@Autowired
	private RequestBDPDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postExecute(
			@RequestParam String[] security,
			@RequestParam(required = false) String[] params)
	{
		log.info("POST RequestBDP: security={}", (Object) security);
		log.info("POST RequestBDP: params={}", (Object) params);
		if (null == params) {
			params = new String[] {
					"ANNOUNCEMENT_DT", "EQY_DVD_YLD_IND", "EQY_WEIGHTED_AVG_PX",
					"HIGH_52WEEK", "LOW_52WEEK", "PX_LAST", "PX_VOLUME", "EQY_RAW_BETA",
					"OPER_ROE", "BS_TOT_LIAB2", "PE_RATIO", "EBITDA", "BEST_EPS_GAAP",
					"BEST_EPS_GAAP_1WK_CHG", "BEST_EPS_GAAP_3MO_CHG", "BEST_EPS_GAAP_4WK_CHG",
					"BOOK_VAL_PER_SH"
			};
		}
		Map<String, Map<String, String>> answer =
				bs.executeBdpRequest("BDP запрос", security, params);
		dao.execute(security, answer);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<EquitiesItem> getSecurities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equities,
			@RequestParam(required = false) Integer fundamentals)
	{
		log.info("POST RequestBDP/Securities: filter={}, equities={}, fundamentals={}",
				Utils.asArray(filter, equities, fundamentals));
		return daoEquities.findAllEquities(filter, equities, fundamentals);
	}

	@RequestMapping(value = "/Params", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboParams(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDP: Params='{}'", query);
		return dao.findParams(query);
	}

	@RequestMapping(value = "/EquitiesFilter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquitiesFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDP: EquitiesFilter='{}'", query);
		return daoEquities.comboFilter(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDP: Equities='{}'", query);
		return daoEquities.comboEquities(query);
	}
}
