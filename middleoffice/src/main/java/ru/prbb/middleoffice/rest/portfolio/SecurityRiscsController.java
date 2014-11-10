package ru.prbb.middleoffice.rest.portfolio;

import java.math.BigDecimal;
import java.util.List;

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
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.portfolio.SecurityRiscsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Заданные параметры риска
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/SecurityRiscs")
public class SecurityRiscsController
		extends BaseController
{

	@Autowired
	private SecurityRiscsDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<SecurityRiscsItem> postShow(
			@RequestParam Long security,
			@RequestParam Long client,
			@RequestParam Long fund,
			@RequestParam Integer batch,
			@RequestParam String date)
	{
		log.info("POST SecurityRiscs: security={}, fund={}, batch={}, client={}, date={}",
				Utils.toArray(security, fund, batch, client, date));
		return dao.findAll(security, fund, batch, client, Utils.parseDate(date));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET SecurityRiscs: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(
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
		log.info("POST SecurityRiscs: id={}, client={}, fund={}, batch={}, riskATH={}, riskAVG={}, stopLoss={},"
				+ " dateBegin={}, dateEnd={}, comment={}",
				Utils.toArray(id, client, fund, batch, riskATH, riskAVG, stopLoss,
						dateBegin, dateEnd, comment));
		dao.updateById(id, client, fund, batch, riskATH, riskAVG, stopLoss,
				Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL SecurityRiscs: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO SecurityRiscs: Securities='{}'", query);
		return daoSecurities.findCombo(query);
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboClients(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO SecurityRiscs: Clients='{}'", query);
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO SecurityRiscs: Funds='{}'", query);
		return daoFunds.findCombo(query);
	}
}
