package ru.prbb.middleoffice.rest.dictionary;

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
import ru.prbb.middleoffice.domain.BrokerAccountItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.dictionary.BrokerAccountsDao;
import ru.prbb.middleoffice.repo.dictionary.BrokersDao;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;

/**
 * Брокерские счета
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/BrokerAccounts")
public class BrokerAccountsController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BrokerAccountsDao dao;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private BrokersDao daoBrokers;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BrokerAccountItem> getItems()
	{
		log.info("GET Accounts");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET Accounts: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam String name,
			@RequestParam String client,
			@RequestParam String broker,
			@RequestParam String comment)
	{
		log.info("POST Accounts add: name={}, client={}, broker={}, comment={}", Utils.toArray(name, client, broker, comment));
		dao.put(name, client, broker, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam String comment)
	{
		log.info("POST Accounts update: id={}, name={}, comment={}", Utils.toArray(id, name, comment));
		dao.updateById(id, name, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL Accounts: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboClients(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Accounts: Clients='{}'", query);
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Brokers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboBrokers(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Accounts: Brokers='{}'", query);
		return daoBrokers.findCombo(query);
	}
}
