package ru.prbb.middleoffice.rest.loading;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.LoadCurrenciesItem;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.repo.BloombergServicesM;
import ru.prbb.middleoffice.repo.loading.LoadCurrenciesDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Загрузка курсов валют
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LoadCurrencies")
public class LoadCurrenciesController
		extends BaseController
{

	@Autowired
	private BloombergServicesM bs;
	@Autowired
	private LoadCurrenciesDao dao;

	@RequestMapping(value = "/Securities", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<LoadCurrenciesItem> getSecurities()
	{
		log.info("POST LoadCurrencies/Securities");
		return dao.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData show(
			@RequestParam String dateStart,
			@RequestParam(required = false) String dateEnd,
			@RequestParam String[] securities)
	{
		log.info("POST LoadCurrencies: dateStart={}, dateEnd={}, securities={}", Utils.toArray(dateStart, dateEnd, securities));
		if (null == dateEnd) {
			final Calendar c = Calendar.getInstance(new Locale("RU", "ru"));
			c.add(Calendar.DAY_OF_MONTH, -1);
			dateEnd = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		}

		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeHistoricalDataRequest("Загрузка курсов валют",
						Utils.parseDate(dateStart), Utils.parseDate(dateEnd),
						securities, new String[] { "PX_LAST" });

		return new ResultData(dao.execute(securities, answer));
	}

}
