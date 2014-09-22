package ru.prbb.middleoffice.rest.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SecurityRiscsItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewPortfolioTransferItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.operations.SetSecurityRiscsDao;
import ru.prbb.middleoffice.repo.portfolio.SecurityRiscsDao;
import ru.prbb.middleoffice.repo.portfolio.ViewPortfolioDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Задать параметры риска
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/SetSecurityRiscs")
public class SetSecurityRiscsController
		extends BaseController
{

	@Autowired
	private SetSecurityRiscsDao dao;
	@Autowired
	private ViewPortfolioDao daoPortfolio;

	@Autowired
	private SecurityRiscsDao daoSecurityRiscs;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postSave(
			@RequestParam Long id,
			@RequestParam Double riskATH,
			@RequestParam Double riskAVG,
			@RequestParam Double stopLoss,
			@RequestParam String comment)
	{
		log.info("POST SetSecurityRiscs: id={}, riskATH={}, riskAVG={}, stopLoss={}, comment={}",
				Utils.toArray(id, riskATH, riskAVG, stopLoss, comment));
		dao.execute(id, riskATH, riskAVG, stopLoss, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<ViewPortfolioTransferItem> postGetPortfolio(
			@RequestParam String date)
	{
		log.info("POST SetSecurityRiscs/Portfolio: date={}", date);
		return daoPortfolio.executeSelect(Utils.parseDate(date));
	}

	@RequestMapping(value = "/UpdateFields", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdate(
			@RequestParam String security,
			@RequestParam String client,
			@RequestParam String fund,
			@RequestParam Integer batch,
			@RequestParam String date)
	{
//		log.info("POST SetSecurityRiscs/Update: security={}, fund={}, batch={}, client={}, date={}",
//				Utils.toArray(security, fund, batch, client, date));

		Long _security = getId(daoEquities.findCombo(security));
		Long _fund = getId(daoFunds.findCombo(fund));
		Long _client = getId(daoClients.findCombo(client));
		List<SecurityRiscsItem> res = daoSecurityRiscs.findAll(_security, _fund, batch, _client, Utils.parseDate(date));

		return res.isEmpty() ? Result.FAIL : new ResultData(res.get(0));
	}

	private Long getId(List<SimpleItem> list) {
		return list.isEmpty() ? null : list.get(0).getId();
	}
}
