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
@RequestMapping("/LoadCashFlowRequest")
public class LoadCashFlowRequestController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final BloombergServices bs;

	@Autowired
	public LoadCashFlowRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getHelp() {
		log.trace("GET");

		return "Выполнить запрос LoadCashFlowRequest"
				+ "\n"
				+ "Параметр\n"
				+ "ids : [ id;security ]\n"
				+ "dates : [ date;security ]\n"
				+ "\n"
				+ "Результат\n"
				+ "[ { id_sec, security, date, value, value2 } ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object postExecute(
			@RequestParam(required = false, defaultValue = "Загрузка дат погашений") String name,
			@RequestParam String[] ids,
			@RequestParam String[] dates)
	{
		log.info("POST LoadCashFlowRequest: name={}", (Object) name);
		log.info("POST LoadCashFlowRequest: ids={}", (Object) ids);
		log.info("POST LoadCashFlowRequest: dates={}", (Object) dates);

		try {
			Map<String, Long> _ids = new HashMap<>(ids.length, 1);
			for (String s : ids) {
				int p = s.indexOf(';');
				Long id = new Long(s.substring(0, p));
				String security = s.substring(p + 1);
				_ids.put(security, id);
			}
			Map<String, String> _dates = new HashMap<>(dates.length, 1);
			for (String s : dates) {
				int p = s.indexOf(';');
				String date = s.substring(0, p);
				String security = s.substring(p + 1);
				_dates.put(security, date);
			}
			return bs.executeLoadCashFlowRequest(name, _ids, _dates);
		} catch (Exception e) {
			log.error("POST LoadCashFlowRequest " + e.getMessage(), e);
			return e;
		}
	}
}
