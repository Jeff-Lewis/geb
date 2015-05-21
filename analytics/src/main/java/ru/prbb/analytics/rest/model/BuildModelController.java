package ru.prbb.analytics.rest.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.BuildModelItem;
import ru.prbb.analytics.domain.EquitiesItem;
import ru.prbb.analytics.domain.PortfolioWatchListItem;
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
		List<BuildModelItem> list = new ArrayList<>(ids.length);
		for (Long id : ids) {
			BuildModelItem item;
			try {
				item = dao.calculateModel(id);				
			} catch (Exception e) {
				item = new BuildModelItem();
				item.setSecurity_code(id.toString());
				item.setStatus(e.getMessage());
			}
			list.add(item);
		}
		return new ResultData(list);
	}

	@RequestMapping(value = "/CalculateSvod", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData calculateSvod()
	{
		log.info("POST BuildModel/CalculateSvod");
		List<PortfolioWatchListItem> items = dao.getPortfolioWatchList();
		List<BuildModelItem> result = new ArrayList<>(items.size());
		for (PortfolioWatchListItem item : items) {
			BuildModelItem res = new BuildModelItem();
			res.setSecurity_code(item.getSecurityCode());
			try {
				String status = "OK";
				switch (item.getPeriod().intValue()) {
				case 5:
					dao.calculateModelQ(item.getSecurityCode());
					break;

				case 6:
					dao.calculateModelHY(item.getSecurityCode());
					break;

				default:
					status = "Unknown PERIOD=" + item.getPeriod();
					break;
				}
				res.setStatus(status);
			} catch (Exception e) {
				res.setStatus(e.getMessage());
				log.error("CalculateSvod for " + item.getShortName(), e);
			}
			result.add(res);
		}
		return new ResultData(result);
		//return new ResultData(dao.calculateSvod());
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
