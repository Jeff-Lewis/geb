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
import ru.prbb.analytics.domain.BrokersForecastDateItem;
import ru.prbb.analytics.domain.BrokersForecastItem;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.EquitiesDao;
import ru.prbb.analytics.repo.reports.BrokersForecastDao;
import ru.prbb.analytics.repo.utils.BrokersDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Прогнозы по брокерам
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/BrokersForecast")
public class BrokersForecastController
		extends BaseController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BrokersForecastDao dao;
	@Autowired
	private BrokersDao daoBrokers;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<BrokersForecastItem> show(
			@RequestParam(required = false) String date,
			@RequestParam(required = false) Long broker,
			@RequestParam(required = false) Long equity)
	{
		log.info("POST BrokersForecast: date={}, broker={}, equity={}", Utils.asArray(date, broker, equity));
		return dao.execute(date, broker, equity);
	}

	@RequestMapping(value = "/Dates", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<BrokersForecastDateItem> comboDates(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO BrokersForecast: Dates='{}'", query);
		return dao.findBrokerDates();
	}

	@RequestMapping(value = "/Brokers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboBrokers(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO BrokersForecast: Brokers='{}'", query);
		return daoBrokers.findCombo(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO BrokersForecast: Equities='{}'", query);
		return daoEquities.comboEquities(query);
	}
}
