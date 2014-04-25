package ru.prbb.middleoffice.rest.portfolio;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.prbb.middleoffice.repo.portfolio.ViewDetailedFinrezDao;

/**
 * Текущий финрез
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewDetailedFinrez")
public class ViewDetailedFinrezController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ViewDetailedFinrezDao dao;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<ViewDetailedFinrezItem> postShow(
			@RequestParam Long security,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long client,
			@RequestParam Long fund)
	{
		log.info("POST ViewDetailedFinrez: security={}, dateBegin={}, dateEnd={}, client={}, fund={}",
				Utils.toArray(security, dateBegin, dateEnd, client, fund));
		return dao.executeSelect(security, Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), client, fund);
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getExport(HttpServletResponse response,
			@RequestParam Long security,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long client,
			@RequestParam Long fund)
	{
		log.info("POST ViewDetailedFinrez/Export: security={}, dateBegin={}, dateEnd={}, client={}, fund={}",
				Utils.toArray(security, dateBegin, dateEnd, client, fund));
		List<ViewDetailedFinrezItem> list =
				dao.executeSelect(security, Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), client, fund);

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
				"Operati",
				"Realised_profi",
				"Initial_quantit",
				"Deal_quantity",
				"Avg_price",
				"Avg_price_us",
				"Deal_pric",
				"Currenc",
				"Fun",
				"Date_insert",
				"Account");
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
					item.getAccount());
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
}
