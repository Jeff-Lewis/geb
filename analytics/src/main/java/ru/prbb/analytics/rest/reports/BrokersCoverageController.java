package ru.prbb.analytics.rest.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.BrokersCoverageItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.repo.reports.BrokersCoverageDao;

/**
 * Покрытие брокеров
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/BrokersCoverage")
public class BrokersCoverageController
{
	@Autowired
	private BrokersCoverageDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<BrokersCoverageItem> show()
	{
		// {call dbo.anca_WebGet_EquityBrokerCoverage_sp}
		return dao.execute();
	}

	@RequestMapping(value = "/Change", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result change(
			@RequestParam Long id,
			@RequestParam String broker,
			@RequestParam String value)
	{
		dao.change(id, broker, value);
		return Result.SUCCESS;
	}

}
