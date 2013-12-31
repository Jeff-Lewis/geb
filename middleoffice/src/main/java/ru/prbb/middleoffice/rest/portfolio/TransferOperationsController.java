package ru.prbb.middleoffice.rest.portfolio;

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
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.TransferOperationsListItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.portfolio.TransferOperationsDao;

/**
 * Список перекидок
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/TransferOperations")
public class TransferOperationsController
{
	@Autowired
	private TransferOperationsDao dao;
	@Autowired
	private FundsDao daoFunds;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<TransferOperationsListItem> show(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long ticker)
	{
		return dao.findAll(Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), ticker);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result export(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long ticker)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result del(
			@RequestParam Long[] ids)
	{
		dao.deleteById(ids);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Set", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result set(
			@RequestParam Long[] ids,
			@RequestParam String field,
			@RequestParam String value)
	{
		char ch = Character.toUpperCase(field.charAt(0));
		StringBuilder sb = new StringBuilder(field);
		sb.setCharAt(0, ch);
		field = sb.toString();

		dao.updateById(ids, field, value);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		return daoFunds.findCombo(query);
	}

	@RequestMapping(value = "/Tickers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboTickers(
			@RequestParam(required = false) String query)
	{
		return daoEquities.findComboPortfolio(query);
	}
}
