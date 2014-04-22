package ru.prbb.middleoffice.rest.services;

import java.util.HashMap;
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

import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SecurityValuesItem;
import ru.prbb.middleoffice.repo.BloombergServicesM;
import ru.prbb.middleoffice.repo.services.LoadValuesDao;

/**
 * Загрузка номинала
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/LoadValues")
public class LoadValuesController
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServicesM bs;
	@Autowired
	private LoadValuesDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData show(
			@RequestParam String[] securities)
	{
		final Map<String, Long> ids = new HashMap<>();

		for (String s : securities) {
			final int p = s.indexOf(':');
			final Long id = new Long(s.substring(0, p));
			final String name = s.substring(p + 1);

			ids.put(name, id);
		}

		List<Map<String, Object>> answer = bs.executeValuesLoad(ids);

		return new ResultData(dao.execute(answer));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SecurityValuesItem> listSecurities()
	{
		return dao.findAllSecurities();
	}
}
