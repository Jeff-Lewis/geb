package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.RiskRewardSetupParamsItem;
import ru.prbb.middleoffice.domain.SecurityItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.services.RiskRewardSetupParamsDao;

/**
 * Задание параметров отчета RR
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/RiskRewardSetupParams")
public class RiskRewardSetupParamsController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RiskRewardSetupParamsDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<RiskRewardSetupParamsItem> getItems(
			@RequestParam Long security,
			@RequestParam String date)
	{
		log.info("POST RiskRewardSetupParams: security={}, date={}", security, date);
		return dao.findAll(security, Utils.parseDate(date));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET RiskRewardSetupParams: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam Long[] securities,
			@RequestParam Double slip,
			@RequestParam Double riskTheor,
			@RequestParam Double riskPract,
			@RequestParam Double discount,
			@RequestParam String dateBegin)
	{
		log.warn("POST RiskRewardSetupParams: securities={}, slip={}, riskTheor={}, riskPract={}, discount={}, dateBegin={}",
				Utils.toArray(securities, slip, riskTheor, riskPract, discount, dateBegin));
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Adds", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItems(
			@RequestParam Long[] securities,
			@RequestParam Double slip,
			@RequestParam Double riskTheor,
			@RequestParam Double riskPract,
			@RequestParam Double discount,
			@RequestParam String dateBegin)
	{
		log.warn("POST RiskRewardSetupParams: securities={}, slip={}, riskTheor={}, riskPract={}, discount={}, dateBegin={}",
				Utils.toArray(securities, slip, riskTheor, riskPract, discount, dateBegin));
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(
			@PathVariable("id") Long id,
			@RequestParam Double slip,
			@RequestParam Double riskTheor,
			@RequestParam Double riskPract,
			@RequestParam Double discount,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		log.warn("POST RiskRewardSetupParams: id={}, slip={}, riskTheor={}, riskPract={}, discount={}, dateBegin={}, dateEnd={}",
				Utils.toArray(id, slip, riskTheor, riskPract, discount, dateBegin, dateEnd));
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL RiskRewardSetupParams: id={}", id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RiskRewardSetupParams: Securities='{}'", query);
		return daoSecurities.findCombo(query);
	}

	@RequestMapping(value = "/Add/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityItem> postSecurities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long security)
	{
		log.info("COMBO RiskRewardSetupParams/Add: filter={}, security={}", filter, security);
		return daoSecurities.findAll(filter, security);
	}

	@RequestMapping(value = "/Add/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RiskRewardSetupParams/Add: Filter='{}'", query);
		return daoSecurities.findComboFilter(query);
	}

	@RequestMapping(value = "/Add/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RiskRewardSetupParams/Add: FilterSecurities='{}'", query);
		return daoSecurities.findCombo(query);
	}
}
