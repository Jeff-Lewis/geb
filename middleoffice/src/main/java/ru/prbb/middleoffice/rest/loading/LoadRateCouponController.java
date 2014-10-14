package ru.prbb.middleoffice.rest.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SecurityItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.BloombergServicesM;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.services.LoadRateCouponDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Загрузка ставки по купонам
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LoadRateCoupon")
public class LoadRateCouponController
		extends BaseController
{

	@Autowired
	private BloombergServicesM bs;
	@Autowired
	private LoadRateCouponDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData show(
			@RequestParam String[] securities)
	{
		final Map<String, Long> ids = new HashMap<>();

		for (String s : securities) {
			log.info("POST LoadRateCoupon: " + s);
			final int p = s.indexOf(';');
			final Long id = new Long(s.substring(0, p));
			final String name = s.substring(p + 1);

			ids.put(name, id);
		}

		List<Map<String, Object>> answer = bs.executeRateCouponLoad(ids);

		return new ResultData(dao.execute(answer));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityItem> listSecurities(
			@RequestParam(defaultValue = "Bond") String filter,
			@RequestParam(required = false) Long security)
	{
		log.info("POST LoadRateCoupon/Securities: filter={}, security={}", filter, security);
		return daoSecurities.findAll(filter, security);
	}

	@RequestMapping(value = "/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO LoadRateCoupon: FilterSecurities='{}'", query);
		return daoSecurities.findComboFilter(query);
	}

	@RequestMapping(value = "/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO LoadRateCoupon: FilterSecurities='{}'", query);
		return daoSecurities.findCombo(query);
	}
}
