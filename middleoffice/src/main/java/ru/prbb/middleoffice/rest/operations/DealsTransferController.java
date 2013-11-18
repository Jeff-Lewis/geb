package ru.prbb.middleoffice.rest.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.operations.DealsTransferDao;

/**
 * Перекидка ЦБ между фондами
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/DealsTransfer")
public class DealsTransferController
{
	// @Autowired
	private DealsTransferDao dao;
	@Autowired
	private FundsDao daoFunds;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Object save(
			@RequestParam Long portfolioId,
			@RequestParam Integer quantity,
			@RequestParam Double price,
			@RequestParam Long fundId,
			@RequestParam Integer batch,
			@RequestParam String comment)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Map<String, Object>> getPortfolio(
			@RequestParam String date)
	{
		// {call dbo.mo_WebGet_SelectPlReport_sp ?, null, 1}
		return new ArrayList<Map<String, Object>>();
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		return daoFunds.findCombo(query);
	}
}
