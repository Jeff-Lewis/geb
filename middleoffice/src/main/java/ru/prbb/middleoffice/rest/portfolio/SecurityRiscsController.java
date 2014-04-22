package ru.prbb.middleoffice.rest.portfolio;

import java.math.BigDecimal;
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
import ru.prbb.middleoffice.domain.SecurityRiscsItem;
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
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecurityRiscsDao dao;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<SecurityRiscsItem> show(
			@RequestParam Long security,
			@RequestParam Long client,
			@RequestParam Long fund,
			@RequestParam Integer batch,
			@RequestParam String date)
	{
		return dao.findAll(security, fund, batch, client, Utils.parseDate(date));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result set(
			@PathVariable("id") Long id,
			@RequestParam Long client,
			@RequestParam Long fund,
			@RequestParam Integer batch,
			@RequestParam BigDecimal riskATH,
			@RequestParam BigDecimal riskAVG,
			@RequestParam BigDecimal stopLoss,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam String comment)
	{
		dao.updateById(id, client, fund, batch, riskATH, riskAVG, stopLoss,
				Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result deleteById(
			@PathVariable("id") Long id)
	{
		dao.deleteById(id);
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
