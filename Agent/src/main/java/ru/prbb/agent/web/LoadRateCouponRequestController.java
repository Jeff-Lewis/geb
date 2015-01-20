/**
 * 
 */
package ru.prbb.agent.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.agent.services.BloombergServices;

/**
 * @author RBr
 */
@Controller
@RequestMapping("/LoadRateCouponRequest")
public class LoadRateCouponRequestController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final BloombergServices bs;

	@Autowired
	public LoadRateCouponRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getHelp() {
		log.trace("GET");

		return "Выполнить запрос LoadRateCouponRequest"
				+ "\n"
				+ "Параметр\n"
				+ "ids : [ id;security ]\n"
				+ "\n"
				+ "Результат\n"
				+ "[ { id_sec, security, date, value } ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object postExecute(
			@RequestParam(required = false, defaultValue = "Загрузка ставки по купонам") String name,
			@RequestParam String[] ids)
	{
		log.info("POST LoadRateCouponRequest: name={}", name);
		log.info("POST LoadRateCouponRequest: ids={}", (Object) ids);

		try {
			Map<String, Long> _ids = new HashMap<>(ids.length, 1);
			for (String s : ids) {
				int p = s.indexOf(';');
				Long id = new Long(s.substring(0, p));
				String security = s.substring(p + 1);
				_ids.put(security, id);
			}
			return bs.executeLoadRateCouponRequest(name, _ids);
		} catch (Exception e) {
			log.error("POST LoadRateCouponRequest " + e.getMessage(), e);
			return e;
		}
	}
}
