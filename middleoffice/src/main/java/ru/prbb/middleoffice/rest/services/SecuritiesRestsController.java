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
import ru.prbb.middleoffice.domain.SecuritiesRestsItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.services.SecuritiesRestsDao;

/**
 * Верификация остатков
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/SecuritiesRests")
public class SecuritiesRestsController
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecuritiesRestsDao dao;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SecuritiesRestsItem> list(
			@RequestParam Long security,
			@RequestParam Long client,
			@RequestParam Long fund,
			@RequestParam Integer batch,
			@RequestParam String date)
	{
		return dao.execute(security, fund, client, batch, Utils.parseDate(date));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result get(
			@PathVariable("id") Long id,
			@RequestParam Byte checkFlag)
	{
		dao.updateById(id, checkFlag);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> showEquities(
			@RequestParam(required = false) String query)
	{
		return daoEquities.findCombo(query);
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> showClients(
			@RequestParam(required = false) String query)
	{
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> showFunds(
			@RequestParam(required = false) String query)
	{
		return daoFunds.findCombo(query);
	}

}
