package ru.prbb.middleoffice.rest.services;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SecurityItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.BloombergServicesM;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.services.LoadATRDao;

/**
 * Загрузка ATR
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/LoadATR")
public class LoadATRController
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServicesM bs;
	@Autowired
	private LoadATRDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData show(
			@RequestParam String[] securities,
			@RequestParam String typeMA,
			@RequestParam Integer periodTA,
			@RequestParam String period,
			@RequestParam String calendar,
			@RequestParam String dateStart,
			@RequestParam String dateEnd)
	{
		List<Map<String, Object>> answer =
				bs.executeAtrLoad(Utils.parseDate(dateStart), Utils.parseDate(dateEnd),
						securities, typeMA, periodTA, period, calendar);

		return new ResultData(dao.execute(answer, typeMA, periodTA, period, calendar));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SecurityItem> listSecurities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long security)
	{
		return daoSecurities.findAll(filter, security);
	}

	@RequestMapping(value = "/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findComboFilter(query);
	}

	@RequestMapping(value = "/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findCombo(query);
	}

	@RequestMapping(value = "/MAType", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboTypeMA(
			@RequestParam(required = false) String query)
	{
		return dao.getTypeMA(query);
	}

	@RequestMapping(value = "/Period", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboPeriod(
			@RequestParam(required = false) String query)
	{
		return dao.getPeriod(query);
	}

	@RequestMapping(value = "/Calendar", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCalendar(
			@RequestParam(required = false) String query)
	{
		return dao.getCalendar(query);
	}
}
