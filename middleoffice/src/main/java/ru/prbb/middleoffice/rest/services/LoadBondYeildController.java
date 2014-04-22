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
import ru.prbb.middleoffice.repo.services.LoadBondYeildDao;

/**
 * Загрузка доходности облигаций
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/LoadBondYeild")
public class LoadBondYeildController
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServicesM bs;
	@Autowired
	private LoadBondYeildDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData show(
			@RequestParam String dateStart,
			@RequestParam(required = false) String dateEnd,
			@RequestParam String[] securities)
	{
		if (null == dateEnd) {
			final Calendar c = Calendar.getInstance(new Locale("RU", "ru"));
			c.add(Calendar.DAY_OF_MONTH, -1);
			dateEnd = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		}

		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeHistoricalDataRequest("Загрузка доходности облигаций",
						Utils.parseDate(dateStart), Utils.parseDate(dateEnd),
						securities, new String[] { "YLD_CNV_MID" });

		return new ResultData(dao.execute(securities, answer));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SecurityItem> listSecurities(
			@RequestParam(defaultValue = "Bond") String filter,
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
}
