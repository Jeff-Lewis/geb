package ru.prbb.middleoffice.rest.portfolio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewDetailedFinrezItem;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.portfolio.ViewDetailedFinrezDao;

/**
 * Текущий финрез
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewDetailedFinrez")
public class ViewDetailedFinrezController
{
	@Autowired
	private ViewDetailedFinrezDao dao;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<ViewDetailedFinrezItem> show(
			@RequestParam Long security,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long client,
			@RequestParam Long fund)
	{
		return dao.executeSelect(security, Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), client, fund);
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Object export(
			@RequestParam String date,
			@RequestParam Long ticker)
	{
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

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboSecurities(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findCombo(query);
	}
}
