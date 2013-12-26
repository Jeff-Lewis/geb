package ru.prbb.analytics.rest.company;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.CompaniesExceptionItem;
import ru.prbb.analytics.domain.CompaniesFileItem;
import ru.prbb.analytics.domain.CompaniesListItem;
import ru.prbb.analytics.domain.CompaniesQuarterItem;
import ru.prbb.analytics.domain.CompaniesYearItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.company.CompaniesDao;
import ru.prbb.analytics.repo.company.CompaniesDao.AttrVal;
import ru.prbb.analytics.repo.model.BuildEPSDao;
import ru.prbb.analytics.repo.model.BuildModelDao;

/**
 * Список компаний
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Companies")
public class CompaniesController
{
	@Autowired
	private CompaniesDao dao;
	@Autowired
	private BuildModelDao daoBuildModel;
	@Autowired
	private BuildEPSDao daoBuildEPS;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<CompaniesListItem> list()
	{
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/Exceptions", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<CompaniesExceptionItem> getExceptions(
			@RequestParam Long id)
	{
		return dao.findVarException(id);
	}

	@RequestMapping(value = "/Quarters", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<CompaniesQuarterItem> getQuarters(
			@RequestParam Long id)
	{
		return dao.findQuarters(id);
	}

	@RequestMapping(value = "/Years", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<CompaniesYearItem> getYears(
			@RequestParam Long id)
	{
		return dao.findYears(id);
	}

	@RequestMapping(value = "/Files", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<CompaniesFileItem> getFiles(
			@RequestParam Long id)
	{
		return dao.findFiles(id);
	}

	@RequestMapping(value = "/{id}/EquityChange", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result execEquityChange(
			@PathVariable Long id,
			@RequestParam String bloomCode,
			@RequestParam String adr,
			@RequestParam String currency_calc,
			@RequestParam String group,
			@RequestParam String koefZero,
			@RequestParam String koefOne,
			@RequestParam String period,
			@RequestParam String eps)
	{
		List<AttrVal> params = new ArrayList<>();
		if (Utils.isNotEmpty(bloomCode))
			params.add(new AttrVal("bloomberg_code", bloomCode));
		if (Utils.isNotEmpty(adr))
			params.add(new AttrVal("adr", adr));
		if (Utils.isNotEmpty(currency_calc))
			params.add(new AttrVal("currency", currency_calc));
		if (Utils.isNotEmpty(group))
			params.add(new AttrVal("pivot_group", group));
		if (Utils.isNotEmpty(koefZero))
			params.add(new AttrVal("koef", koefZero));
		if (Utils.isNotEmpty(koefOne))
			params.add(new AttrVal("new_koef", koefOne));
		if (Utils.isNotEmpty(period))
			params.add(new AttrVal("period", period));
		if (Utils.isNotEmpty(eps))
			params.add(new AttrVal("eps", eps));
		dao.updateById(id, params);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/CalculateEps", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result execCalculateEps(
			@PathVariable Long id)
	{
		daoBuildEPS.calculate(new Long[] { id });
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/BuildModelCompany", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result execBuildModelCompany(
			@PathVariable Long id)
	{
		daoBuildModel.calculateModel(new Long[] { id });
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/ScanSave", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result getScanSave(
			@PathVariable Long id)
	{
		// TODO ScanSave
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/ScanOpen", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result getScanOpen(
			@PathVariable Long id)
	{
		// TODO ScanOpen
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/ScanDelete", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result getScanDelete(
			@PathVariable Long id)
	{
		// TODO ScanDelete
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Currencies", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCurrencies(
			@RequestParam(required = false) String query)
	{
		return dao.findComboCurrencies(query);
	}

	@RequestMapping(value = "/GroupSvod", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboGroupSvod(
			@RequestParam(required = false) String query)
	{
		return dao.findComboGroupSvod(query);
	}

	@RequestMapping(value = "/Period", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboPeriod(
			@RequestParam(required = false) String query)
	{
		return dao.findComboPeriod(query);
	}

	@RequestMapping(value = "/Eps", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEps(
			@RequestParam(required = false) String query)
	{
		return dao.findComboEps(query);
	}

	@RequestMapping(value = "/Variables", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboVariables(
			@RequestParam(required = false) String query)
	{
		return dao.findComboVariables(query);
	}
}
