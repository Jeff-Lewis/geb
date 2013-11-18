package ru.prbb.middleoffice.rest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * 
 */
@Controller
@RequestMapping("/rest/RiskRewardSetupParams")
public class RiskRewardSetupParamsController
{
	//	@Autowired
	private RiskRewardSetupParamsDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<RiskRewardSetupParamsItem> list(
			@RequestParam String date,
			@RequestParam Long security)
	{
		ArrayList<RiskRewardSetupParamsItem> list = new ArrayList<RiskRewardSetupParamsItem>();
		for (long i = 1; i < 11; i++) {
			RiskRewardSetupParamsItem item = new RiskRewardSetupParamsItem();
			item.setId(i);
			item.setSecurity_code("security_code" + i);
			list.add(item);
		}
		return list;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		RiskRewardSetupParamsItem item = new RiskRewardSetupParamsItem();
		item.setId(id);
		item.setSecurity_code("security_code" + id);
		return new ResultData(item);
	}

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam Long[] securities,
			@RequestParam Double slip,
			@RequestParam Double riskTheor,
			@RequestParam Double riskPract,
			@RequestParam Double discount,
			@RequestParam String dateBegin)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Adds", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result adds(
			@RequestParam Long[] securities,
			@RequestParam Double slip,
			@RequestParam Double riskTheor,
			@RequestParam Double riskPract,
			@RequestParam Double discount,
			@RequestParam String dateBegin)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result update(
			@PathVariable("id") Long id,
			@RequestParam Double slip,
			@RequestParam Double riskTheor,
			@RequestParam Double riskPract,
			@RequestParam Double discount,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result deleteById(
			@PathVariable("id") Long id)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> showSecurities(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findCombo(query);
	}

	@RequestMapping(value = "/Add/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SecurityItem> listSecurities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long security)
	{
		return daoSecurities.findAll(filter, security);
	}

	@RequestMapping(value = "/Add/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findComboFilter(query);
	}

	@RequestMapping(value = "/Add/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findCombo(query);
	}
}
