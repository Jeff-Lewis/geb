package ru.prbb.analytics.rest.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.BrokersForecastDateItem;
import ru.prbb.analytics.domain.BrokersForecastItem;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.EquitiesDao;
import ru.prbb.analytics.repo.reports.BrokersForecastDao;
import ru.prbb.analytics.repo.utils.BrokersDao;

/**
 * Прогнозы по брокерам
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/BrokersForecast")
public class BrokersForecastController
{
	@Autowired
	private BrokersForecastDao dao;
	@Autowired
	private BrokersDao daoBrokers;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<BrokersForecastItem> show(
			@RequestParam(required = false) String date,
			@RequestParam(required = false) String broker,
			@RequestParam(required = false) String equity)
	{
		return dao.execute(date, broker, equity);
	}

	@RequestMapping(value = "/Dates", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<BrokersForecastDateItem> comboDates(
			@RequestParam(required = false) String query)
	{
		return dao.findBrokerDates();
	}

	@RequestMapping(value = "/Brokers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboBrokers(
			@RequestParam(required = false) String query)
	{
		return daoBrokers.findCombo(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		return daoEquities.comboEquities(query);
	}
}
