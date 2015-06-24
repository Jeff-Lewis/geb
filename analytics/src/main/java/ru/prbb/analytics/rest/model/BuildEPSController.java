package ru.prbb.analytics.rest.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.BuildEPSItem;
import ru.prbb.analytics.domain.EquitiesItem;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.EquitiesDao;
import ru.prbb.analytics.repo.model.BuildEPSDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Расчёт EPS по компании
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/BuildEPS")
public class BuildEPSController
		extends BaseController
{

	@Autowired
	private BuildEPSDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(value = "/CalculateEps", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData calculateEPS(
			@RequestParam Long[] ids)
	{
		log.info("POST BuildEPS/CalculateEps: ids={}", (Object) ids);
		final List<BuildEPSItem> res = new ArrayList<>(ids.length);
		for (Long id : ids) {
			try {
				BuildEPSItem item = dao.calculate(id);
				res.add(item);
			} catch (Exception e) {
				BuildEPSItem item = new BuildEPSItem();
				item.setSecurity_code(id.toString());
				item.setBv_growth_rate(e.getMessage());
				res.add(item);
			}
		}
		return new ResultData(res);
	}

	@RequestMapping(value = "/CalculateEpsAll", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData calculateEPS()
	{
		log.info("GET BuildEPS/CalculateEpsAll");
		return new ResultData(dao.calculate());
	}

	@RequestMapping(value = "/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO BuildEPS: Filter='{}'", query);
		return daoEquities.comboFilter(query);
	}

	@RequestMapping(value = "/FilterEquities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO BuildEPS: FilterEquities='{}'", query);
		return daoEquities.comboEquities(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<EquitiesItem> listEquities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equity)
	{
		log.info("POST BuildEPS: filter={}, equity={}", filter, equity);
		return daoEquities.findAllEquities(filter, equity, 1);
	}
}
