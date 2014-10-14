package ru.prbb.middleoffice.rest.services;

import java.util.List;
import java.util.Map;

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
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Загрузка ATR
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LoadATR")
public class LoadATRController
		extends BaseController
{

	@Autowired
	private BloombergServicesM bs;
	@Autowired
	private LoadATRDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData postShow(
			@RequestParam String[] securities,
			@RequestParam String typeMA,
			@RequestParam Integer periodTA,
			@RequestParam String period,
			@RequestParam String calendar,
			@RequestParam String dateStart,
			@RequestParam String dateEnd)
	{
		log.info("POST LoadATR: {}", securities);
		log.info("POST LoadATR: typeMA={}, periodTA={}, period={}, calendar={}, dateStart={}, dateEnd={}",
				Utils.toArray(typeMA, periodTA, period, calendar, dateStart, dateEnd));
		List<Map<String, Object>> answer =
				bs.executeAtrLoad(Utils.parseDate(dateStart), Utils.parseDate(dateEnd),
						securities, typeMA, periodTA, period, calendar);

		return new ResultData(dao.execute(answer, typeMA, periodTA, period, calendar));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityItem> listSecurities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long security)
	{
		log.info("POST LoadATR/Securities: filter={}, security={}", filter, security);
		return daoSecurities.findAll(filter, security);
	}

	@RequestMapping(value = "/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO LoadATR: Filter='{}'", query);
		return daoSecurities.findComboFilter(query);
	}

	@RequestMapping(value = "/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO LoadATR: FilterSecurities='{}'", query);
		return daoSecurities.findCombo(query);
	}

	@RequestMapping(value = "/MAType", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboTypeMA(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO LoadATR: MAType='{}'", query);
		return dao.getTypeMA(query);
	}

	@RequestMapping(value = "/Period", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboPeriod(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO LoadATR: Period='{}'", query);
		return dao.getPeriod(query);
	}

	@RequestMapping(value = "/Calendar", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboCalendar(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO LoadATR: Calendar='{}'", query);
		return dao.getCalendar(query);
	}
}
