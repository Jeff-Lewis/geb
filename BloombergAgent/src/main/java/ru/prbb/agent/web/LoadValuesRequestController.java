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
@RequestMapping("/LoadValuesRequest")
public class LoadValuesRequestController {

	private final Log log = LogFactory.getLog(getClass());

	private final BloombergServices bs;

	@Autowired
	public LoadValuesRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String get() {
		log.trace("GET");

		return "Выполнить запрос LoadValuesRequest"
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
	public Object execute(
			@RequestParam(required = false, defaultValue = "Загрузка номинала") String name,
			@RequestParam String[] ids) {

		if (log.isInfoEnabled()) {
			log.info("POST execute " + Arrays.asList(ids));
		}

		try {
			Map<String, Long> _ids = new HashMap<>(ids.length, 1);
			for (String s : ids) {
				int p = s.indexOf(';');
				Long id = new Long(s.substring(0, p));
				String security = s.substring(p + 1);
				_ids.put(security, id);
			}
			return bs.executeLoadValuesRequest(name, _ids);
		} catch (Exception e) {
			log.error("POST execute " + e.getMessage(), e);
			return e;
		}
	}
}
