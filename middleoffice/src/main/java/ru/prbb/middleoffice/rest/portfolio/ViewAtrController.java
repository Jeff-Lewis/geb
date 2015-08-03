package ru.prbb.middleoffice.rest.portfolio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.portfolio.ViewAtrDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Отображение ATR
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewAtr")
public class ViewAtrController
		extends BaseController
{

	@Autowired
	private ViewAtrDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData postShow(HttpServletRequest request,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long[] securities)
	{
		log.info("POST ViewAtr: dateBegin={}, dateEnd={}, securities={}", Utils.toArray(dateBegin, dateEnd, securities));
		return new ResultData(dao.execute(createUserInfo(request),Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), securities));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityItem> getSecurities(HttpServletRequest request,
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long security)
	{
		log.info("POST ViewAtr: filter={}, security={}", filter, security);
		return daoSecurities.findAll(createUserInfo(request),filter, security);
	}

	@RequestMapping(value = "/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewAtr: Filter={}", query);
		return daoSecurities.findComboFilter(query);
	}

	@RequestMapping(value = "/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewAtr: FilterSecurities={}", query);
		return daoSecurities.findCombo(query);
	}
}
