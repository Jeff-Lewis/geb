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
import ru.prbb.middleoffice.domain.RiskRewardPrice1Item;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.services.RiskRewardPrice1Dao;

/**
 * Цена1 для RR
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/RiskRewardPrice1")
public class RiskRewardPrice1Controller
{
	// @Autowired
	private RiskRewardPrice1Dao dao;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<RiskRewardPrice1Item> list()
	{
		ArrayList<RiskRewardPrice1Item> list = new ArrayList<RiskRewardPrice1Item>();
		for (long i = 1; i < 11; i++) {
			RiskRewardPrice1Item item = new RiskRewardPrice1Item();
			item.setId(i);
			item.setSecurity_code("security_code" + i);
			item.setClient("client" + i);
			item.setFund("fund" + i);
			list.add(item);
		}
		return list;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		RiskRewardPrice1Item item = new RiskRewardPrice1Item();
		item.setId(id);
		item.setSecurity_code("security_code" + id);
		item.setClient("client" + id);
		item.setFund("fund" + id);
		return new ResultData(item);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam Long portfolio,
			@RequestParam Double price)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result updateById(
			@PathVariable("id") Long id,
			@RequestParam Long clientId,
			@RequestParam Long fundId,
			@RequestParam Integer batch,
			@RequestParam Double price,
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
		//dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboClients(
			@RequestParam(required = false) String query)
	{
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		return daoFunds.findCombo(query);
	}
}