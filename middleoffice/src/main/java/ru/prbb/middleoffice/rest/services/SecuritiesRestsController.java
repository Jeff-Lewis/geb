package ru.prbb.middleoffice.rest.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Верификация остатков
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/SecuritiesRests")
public class SecuritiesRestsController
		extends BaseController
{

	@Autowired
	private SecuritiesRestsDao dao;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecuritiesRestsItem> postItems(HttpServletRequest request,
			@RequestParam Long security,
			@RequestParam Long client,
			@RequestParam Long fund,
			@RequestParam Integer batch,
			@RequestParam String date)
	{
		log.info("POST SecuritiesRests: security={}, fund={}, client={}, batch={}, date={}",
				Utils.toArray(security, fund, client, batch, date));
		return dao.execute(createUserInfo(request),security, fund, client, batch, Utils.parseDate(date));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postCheckFlag(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam Byte checkFlag)
	{
		log.info("POST SecuritiesRests: id={}, checkFlag={}", id, checkFlag);
		dao.updateById(createUserInfo(request),id, checkFlag);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO SecuritiesRests: Equities='{}'", query);
		return daoEquities.findCombo(query);
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboClients(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO SecuritiesRests: Clients='{}'", query);
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO SecuritiesRests: Funds='{}'", query);
		return daoFunds.findCombo(query);
	}

}
