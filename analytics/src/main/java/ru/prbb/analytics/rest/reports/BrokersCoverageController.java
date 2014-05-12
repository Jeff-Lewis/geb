package ru.prbb.analytics.rest.reports;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Покрытие брокеров
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/BrokersCoverage")
public class BrokersCoverageController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BrokersCoverageDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BrokersCoverageItem> getShow()
	{
		log.info("GET BrokersCoverage");
		return dao.execute();
	}

	@RequestMapping(value = "/Change", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postChange(
			@RequestParam Long id,
			@RequestParam String broker,
			@RequestParam Integer value)
	{
		log.info("POST BrokersCoverage: id={}, broker={}, value={}", Utils.asArray(id, broker, value));
		dao.change(id, Utils.getColumnNameByField(broker, BrokersCoverageItem.class), value);
		return Result.SUCCESS;
	}
}
