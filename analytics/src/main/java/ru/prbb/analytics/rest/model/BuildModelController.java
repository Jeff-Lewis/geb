package ru.prbb.analytics.rest.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.EquitiesItem;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.EquitiesDao;
import ru.prbb.analytics.repo.model.BuildModelDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Расчёт модели по компании
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/BuildModel")
public class BuildModelController
		extends BaseController
{

	@Autowired
	private BuildModelDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(value = "/CalculateModel", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData calculateModel(
			@RequestParam Long[] ids)
	{
		log.info("POST BuildModel/CalculateModel");
		return new ResultData(dao.calculateModel(ids));
	}

	@RequestMapping(value = "/CalculateSvod", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData calculateSvod()
	{
		log.info("POST BuildModel/CalculateSvod");
		return new ResultData(dao.calculateSvod());
	}

	@RequestMapping(value = "/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO BuildModel: Filter='{}'", query);
		return daoEquities.comboFilter(query);
	}

	@RequestMapping(value = "/FilterEquities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO BuildModel: FilterEquities='{}'", query);
		return daoEquities.comboEquities(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<EquitiesItem> listEquities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equity)
	{
		log.info("POST BuildModel: filter={}, equity={}", filter, equity);
		return daoEquities.findAllEquities(filter, equity, 2);
	}
}
