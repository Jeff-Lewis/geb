package ru.prbb.middleoffice.rest.portfolio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.portfolio.SecurityRiscsDao;

/**
 * Заданные параметры риска
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/SecurityRiscs")
public class SecurityRiscsController
{
	// @Autowired
	private SecurityRiscsDao dao;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData show(
			@RequestParam String security,
			@RequestParam Long client,
			@RequestParam Long fund,
			@RequestParam Integer batch,
			@RequestParam String date)
	{
		return new ResultData(new HashMap<String, Object>());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Map<String, Object> get(
			@PathVariable("id") Long id)
	{
		return new HashMap<String, Object>();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result set(
			@PathVariable("id") Long id,
			@RequestParam Long client,
			@RequestParam Long fund,
			@RequestParam Integer batch,
			@RequestParam Double riskATH,
			@RequestParam Double riskAVG,
			@RequestParam Double stopLoss,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam String comment)
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

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		return daoEquities.findCombo(query);
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
