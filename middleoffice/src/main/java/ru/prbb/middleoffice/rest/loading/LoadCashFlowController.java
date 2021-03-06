package ru.prbb.middleoffice.rest.loading;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SecurityCashFlowItem;
import ru.prbb.middleoffice.repo.BloombergServicesM;
import ru.prbb.middleoffice.repo.loading.LoadCashFlowDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Загрузка дат погашений
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LoadCashFlow")
public class LoadCashFlowController
		extends BaseController
{

	@Autowired
	private BloombergServicesM bs;
	@Autowired
	private LoadCashFlowDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData show(HttpServletRequest request,
			@RequestParam String[] securities)
	{
		final Map<String, Long> ids = new HashMap<>();
		final Map<String, String> dates = new HashMap<>();

		for (String s : securities) {
			log.info("POST LoadCashFlow: " + s);
			final int p = s.indexOf(';');
			final int p1 = s.indexOf(';', p + 1);
			final Long id = new Long(s.substring(0, p));
			final String date = s.substring(p + 1, p1);
			final String name = s.substring(p1 + 1);

			ids.put(name, id);
			dates.put(name, date);
		}

		List<Map<String, String>> answer = bs.executeCashFlowLoad(ids, dates);

		return new ResultData(dao.execute(createUserInfo(request),answer));
	}

	@RequestMapping(value = "/New", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData showNew(HttpServletRequest request,
			@RequestParam String[] securities)
	{
		final Map<String, Long> ids = new HashMap<>();
		final Map<String, String> dates = new HashMap<>();

		for (String s : securities) {
			final int p = s.indexOf(';');
			final int p1 = s.indexOf(';', p + 1);
			final Long id = new Long(s.substring(0, p));
			final String date = s.substring(p + 1, p1);
			final String name = s.substring(p1 + 1);

			ids.put(name, id);
			dates.put(name, date);
		}

		List<Map<String, String>> answer = bs.executeCashFlowLoadNew(ids, dates);

		return new ResultData(dao.execute(createUserInfo(request),answer));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityCashFlowItem> getSecurities(HttpServletRequest request)
	{
		log.info("GET LoadCashFlow/Securities");
		return dao.findAllSecurities(createUserInfo(request));
	}
}
