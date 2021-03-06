package ru.prbb.middleoffice.rest.operations;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewPortfolioTransferItem;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.operations.DealsTransferDao;
import ru.prbb.middleoffice.repo.portfolio.ViewPortfolioDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Перекидка ЦБ между фондами
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/DealsTransfer")
public class DealsTransferController
		extends BaseController
{

	@Autowired
	private DealsTransferDao dao;
	@Autowired
	private ViewPortfolioDao daoPortfolio;
	@Autowired
	private FundsDao daoFunds;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postSave(HttpServletRequest request,
			@RequestParam Long portfolioId,
			@RequestParam Integer quantity,
			@RequestParam Double price,
			@RequestParam Long fundId,
			@RequestParam Integer batch,
			@RequestParam String comment)
	{
		log.info("POST DealsTransfer: portfolioId={}, quantity={}, price={}, fundId={}, batch={}, comment={}",
				Utils.toArray(portfolioId, quantity, price, fundId, batch, comment));
		dao.execute(createUserInfo(request),portfolioId, quantity, price, fundId, batch, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/PortfolioShowTransfer", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<ViewPortfolioTransferItem> postGetPortfolio(HttpServletRequest request,
			@RequestParam String date,
			@RequestParam Long client)
	{
		log.info("POST DealsTransfer/PortfolioShowTransfer: date={}, client={}", date, client);
		return daoPortfolio.executeSelect(createUserInfo(request),Utils.parseDate(date), client);
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO DealsTransfer: Funds='{}'", query);
		return daoFunds.findCombo(query);
	}
}
