package ru.prbb.middleoffice.rest.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
import ru.prbb.middleoffice.repo.services.LoadQuotesDao;

/**
 * Загрузка котировок
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LoadQuotes")
public class LoadQuotesController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServicesM bs;
	@Autowired
	private LoadQuotesDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData show(
			@RequestParam String dateStart,
			@RequestParam(required = false) String dateEnd,
			@RequestParam String[] securities)
	{
		log.info("POST LoadQuotes: dateStart={}, dateEnd={}, securities={}", Utils.toArray(dateStart, dateEnd, securities));
		if (null == dateEnd) {
			final Calendar c = Calendar.getInstance(new Locale("RU", "ru"));
			c.add(Calendar.DAY_OF_MONTH, -1);
			dateEnd = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		}

		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeHistoricalDataRequest("Загрузка котировок",
						Utils.parseDate(dateStart), Utils.parseDate(dateEnd),
						securities, new String[] { "PX_LAST" });

		return new ResultData(dao.execute(securities, answer));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityItem> listSecurities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long security)
	{
		log.info("POST LoadQuotes/Securities: filter={}, security={}", filter, security);
		return daoSecurities.findAll(filter, security);
	}

	@RequestMapping(value = "/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO LoadQuotes: Filter='{}'", query);
		return daoSecurities.findComboFilter(query);
	}

	@RequestMapping(value = "/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO LoadQuotes: FilterSecurities='{}'", query);
		return daoSecurities.findCombo(query);
	}
}
