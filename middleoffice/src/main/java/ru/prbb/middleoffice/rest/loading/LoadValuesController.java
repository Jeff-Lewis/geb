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
import ru.prbb.middleoffice.domain.SecurityValuesItem;
import ru.prbb.middleoffice.repo.BloombergServicesM;
import ru.prbb.middleoffice.repo.loading.LoadValuesDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Загрузка номинала
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LoadValues")
public class LoadValuesController
		extends BaseController
{

	@Autowired
	private BloombergServicesM bs;
	@Autowired
	private LoadValuesDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData show(HttpServletRequest request,
			@RequestParam String[] securities)
	{
		final Map<String, Long> ids = new HashMap<>();

		for (String s : securities) {
			log.info("POST LoadValues: " + s);
			final int p = s.indexOf(':');
			final Long id = new Long(s.substring(0, p));
			final String name = s.substring(p + 1);

			ids.put(name, id);
		}

		List<Map<String, String>> answer = bs.executeValuesLoad(ids);

		return new ResultData(dao.execute(createUserInfo(request),answer));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityValuesItem> listSecurities(HttpServletRequest request)
	{
		log.info("GET LoadValues/Securities");
		return dao.findAllSecurities(createUserInfo(request));
	}
}
