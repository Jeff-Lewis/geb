package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * 
 */
@Controller
@RequestMapping("/rest/BrokerAccounts")
public class BrokerAccountsController
{
	@Autowired
	private BrokerAccountsDao dao;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private BrokersDao daoBrokers;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<BrokerAccountItem> list()
	{
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		final BrokerAccountItem value = dao.findById(id);
		return new ResultData(value);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam String name,
			@RequestParam String client,
			@RequestParam String broker,
			@RequestParam String comment)
	{
		final BrokerAccountItem value = new BrokerAccountItem();
		value.setName(name);
		value.setClient(client);
		value.setBroker(broker);
		value.setComment(comment);
		dao.put(value);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result update(
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam String comment)
	{
		final BrokerAccountItem value = new BrokerAccountItem();
		value.setId(id);
		value.setName(name);
		value.setComment(comment);
		dao.updateById(id, value);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result delete(
			@PathVariable("id") Long id)
	{
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboClients(
			@RequestParam(required = false) String query)
	{
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Brokers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboBrokers(
			@RequestParam(required = false) String query)
	{
		return daoBrokers.findCombo(query);
	}
}
