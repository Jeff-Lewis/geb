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

import ru.prbb.analytics.domain.EquitiesItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.BloombergServicesA;
import ru.prbb.analytics.repo.EquitiesDao;
import ru.prbb.analytics.repo.bloomberg.RequestBDPDao;

/**
 * BDP запрос
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/RequestBDP")
public class RequestBDPController
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServicesA bs;
	@Autowired
	private RequestBDPDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result execute(
			@RequestParam String[] security,
			@RequestParam(required = false) String[] params)
	{
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

	@RequestMapping(value = "/Params", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboParams(
			@RequestParam(required = false) String query)
	{
		return dao.findParams();
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<EquitiesItem> getEquities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equities,
			@RequestParam(required = false) Integer fundamentals)
	{
		return daoEquities.findAllEquities(filter, equities, fundamentals);
	}

	@RequestMapping(value = "/EquitiesFilter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquitiesFilter(
			@RequestParam(required = false) String query)
	{
		return daoEquities.comboFilter(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		return daoEquities.comboEquities(query);
	}
}
