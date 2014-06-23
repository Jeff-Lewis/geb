package ru.prbb.middleoffice.rest.portfolio;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Export;
import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.TransferOperationsListItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.portfolio.TransferOperationsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Список перекидок
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/TransferOperations")
public class TransferOperationsController
		extends BaseController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private TransferOperationsDao dao;
	@Autowired
	private FundsDao daoFunds;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<TransferOperationsListItem> postShow(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long ticker)
	{
		log.info("POST TransferOperations: dateBegin={}, dateEnd={}, ticker={}", Utils.toArray(dateBegin, dateEnd, ticker));
		return dao.findAll(Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), ticker);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET TransferOperations: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getExport(HttpServletResponse response,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long ticker)
	{
		log.info("GET TransferOperations/Export: dateBegin={}, dateEnd={}, ticker={}", Utils.toArray(dateBegin, dateEnd, ticker));
		List<TransferOperationsListItem> list =
				dao.findAll(Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), ticker);

		Export exp = Export.newInstance();
		exp.setCaption("Список перекидок");
		exp.addTitle("Список перекидок c: " + dateBegin + " по: " + dateEnd);
		exp.setColumns(
				"Client",
				"Security",
				"Currency",
				"Funding",
				"TransferQuantity",
				"TransferDate",
				"SourceFund",
				"SourceBatch",
				"SourceQuantity",
				"SourceOperation",
				"DestinationFund",
				"DestinationBatch",
				"DestinationOperation",
				"Comment");
		for (TransferOperationsListItem item : list) {
			exp.addRow(
					item.getClient(),
					item.getSecurity(),
					item.getCurrency(),
					item.getFunding(),
					item.getTransferQuantity(),
					item.getTransferDate(),
					item.getSourceFund(),
					item.getSourceBatch(),
					item.getSourceQuantity(),
					item.getSourceOperation(),
					item.getDestinationFund(),
					item.getDestinationBatch(),
					item.getDestinationOperation(),
					item.getComment());
		}

		String name = "TransferOperations.ods";
		response.setHeader("Content-disposition", "attachment;filename=" + name);
		response.setContentType(exp.getContentType());
		return exp.build();
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@RequestParam Long[] ids)
	{
		log.info("DEL TransferOperations: ids={}", ids);
		dao.deleteById(ids);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Set", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postSet(
			@RequestParam Long[] ids,
			@RequestParam String field,
			@RequestParam String value)
	{
		log.info("POST TransferOperations/Set: field={}, value={}, ids={}", Utils.toArray(field, value, ids));
		char ch = Character.toUpperCase(field.charAt(0));
		StringBuilder sb = new StringBuilder(field);
		sb.setCharAt(0, ch);
		field = sb.toString();

		dao.updateById(ids, field, value);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO TransferOperations: Funds='{}'", query);
		return daoFunds.findCombo(query);
	}

	@RequestMapping(value = "/Tickers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboTickers(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO TransferOperations: Tickers='{}'", query);
		return daoEquities.findComboPortfolio(query);
	}
}
