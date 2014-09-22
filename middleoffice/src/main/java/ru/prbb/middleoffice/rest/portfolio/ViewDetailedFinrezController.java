package ru.prbb.middleoffice.rest.portfolio;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Export;
import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewDetailedFinrezItem;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.dictionary.InitiatorsDao;
import ru.prbb.middleoffice.repo.portfolio.ViewDetailedFinrezDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Текущий финрез
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewDetailedFinrez")
public class ViewDetailedFinrezController
		extends BaseController
{

	@Autowired
	private ViewDetailedFinrezDao dao;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;
	@Autowired
	private SecuritiesDao daoSecurities;
	@Autowired
	private InitiatorsDao daoInitiators;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<ViewDetailedFinrezItem> postShow(
			@RequestParam Long security,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long client,
			@RequestParam Long fund,
			@RequestParam Long initiator)
	{
		log.info("POST ViewDetailedFinrez: security={}, dateBegin={}, dateEnd={}, client={}, fund={}, initiator={}",
				Utils.toArray(security, dateBegin, dateEnd, client, fund, initiator));
		return dao.executeSelect(security, Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), client, fund, initiator);
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getExport(HttpServletResponse response,
			@RequestParam Long security,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long client,
			@RequestParam Long fund,
			@RequestParam Long initiator)
	{
		log.info("POST ViewDetailedFinrez/Export: security={}, dateBegin={}, dateEnd={}, client={}, fund={}, initiator={}",
				Utils.toArray(security, dateBegin, dateEnd, client, fund));
		List<ViewDetailedFinrezItem> list =
				dao.executeSelect(security, Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), client, fund, initiator);

		Export exp = Export.newInstance();
		exp.setCaption("Текущий финрез");
		exp.addTitle("Текущий финрез с: " + dateBegin + " по: " + dateEnd);
		exp.setColumns(
				"Client",
				"Portfolio",
				"Security",
				"Batch",
				"Deals_realise",
				"Deal_id",
				"TradeNum",
				"Trade_date",
				"Operation",
				"Realised_profit",
				"Initial_quantity",
				"Deal_quantity",
				"Avg_price",
				"Avg_price_usd",
				"Deal_price",
				"Currency",
				"Funding",
				"Date_insert",
				"Account",
				"Initiator");
		for (ViewDetailedFinrezItem item : list) {
			exp.addRow(
					item.getClient(),
					item.getPortfolio(),
					item.getSecurity_code(),
					item.getBatch(),
					item.getDeals_realised_profit_id(),
					item.getDeal_id(),
					item.getTradeNum(),
					item.getTrade_date(),
					item.getOperation(),
					item.getRealised_profit(),
					item.getInitial_quantity(),
					item.getDeal_quantity(),
					item.getAvg_price(),
					item.getAvg_price_usd(),
					item.getDeal_price(),
					item.getCurrency(),
					item.getFunding(),
					item.getDate_insert(),
					item.getAccount(),
					item.getInitiator());
		}

		String name = "Finrez.ods";
		response.setHeader("Content-disposition", "attachment;filename=" + name);
		response.setContentType(exp.getContentType());
		return exp.build();
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboClients(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDetailedFinrez: Clients='{}'", query);
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDetailedFinrez: Funds='{}'", query);
		return daoFunds.findCombo(query);
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDetailedFinrez: Securities='{}'", query);
		return daoSecurities.findCombo(query);
	}

	@RequestMapping(value = "/Initiator", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboInitiator(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDetailedFinrez: Initiator='{}'", query);
		return daoInitiators.findCombo(query);
	}
}
