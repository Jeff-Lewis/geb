package ru.prbb.analytics.rest.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.BrokersCoverageItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.repo.reports.BrokersCoverageDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Покрытие брокеров
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/BrokersCoverage")
public class BrokersCoverageController
		extends BaseController
{

	@Autowired
	private BrokersCoverageDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BrokersCoverageItem> getShow(HttpServletRequest request)
	{
		log.info("GET BrokersCoverage");
		return dao.execute(createUserInfo(request));
	}

	@RequestMapping(value = "/Change", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postChange(HttpServletRequest request,
			@RequestParam Long id,
			@RequestParam String broker,
			@RequestParam Integer value)
	{
		log.info("POST BrokersCoverage: id={}, broker={}, value={}", Utils.asArray(id, broker, value));
		dao.change(createUserInfo(request), id, Utils.getColumnNameByField(broker, BrokersCoverageItem.class), value);
		return Result.SUCCESS;
	}
}
