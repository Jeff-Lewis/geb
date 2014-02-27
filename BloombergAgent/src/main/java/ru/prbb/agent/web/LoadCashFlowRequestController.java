/**
 * 
 */
package ru.prbb.agent.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.prbb.agent.service.BloombergServices;

/**
 * @author RBr
 */
@RestController
@RequestMapping("/LoadCashFlowRequest")
public class LoadCashFlowRequestController {

	private final Log log = LogFactory.getLog(getClass());

	private final BloombergServices bs;

	@Autowired
	public LoadCashFlowRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String get() {
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
	public Object execute(
			@RequestParam(required = false, defaultValue = "Загрузка дат погашений") String name,
			@RequestParam String[] ids,
			@RequestParam String[] dates) {

		if (log.isInfoEnabled()) {
			log.info("POST execute " + Arrays.asList(ids));
			log.info("POST execute " + Arrays.asList(dates));
		}

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
			log.error("POST execute " + e.getMessage(), e);
			return e;
		}
	}
}
