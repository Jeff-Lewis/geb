package ru.prbb.analytics.rest.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.EquityItem;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.EquitiesDao;
import ru.prbb.analytics.repo.model.BuildModelDao;

/**
 * Расчёт модели по компании
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/BuildModel")
public class BuildModelController
{
	@Autowired
	private BuildModelDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(value = "/CalculateModel", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData calculateModel(
			@RequestParam Long[] ids)
	{
		return new ResultData(dao.calculateModel(ids));
	}

	@RequestMapping(value = "/CalculateSvod", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData calculateSvod()
	{
		return new ResultData(dao.calculateSvod());
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		return daoEquities.comboFilter();
	}

	@RequestMapping(value = "/FilterEquities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFilterEquities(
			@RequestParam(required = false) String query)
	{
		return daoEquities.comboEquities(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<EquityItem> listEquities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equity)
	{
		return daoEquities.findAllEquities(filter, equity, 2);
	}
}
