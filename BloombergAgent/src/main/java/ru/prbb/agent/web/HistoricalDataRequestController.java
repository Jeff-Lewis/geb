/**
 * 
 */
package ru.prbb.agent.web;

import java.util.Arrays;

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
@RequestMapping("/HistoricalDataRequest")
public class HistoricalDataRequestController {

	private final Log log = LogFactory.getLog(getClass());

	private final BloombergServices bs;

	@Autowired
	public HistoricalDataRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String get() {
		log.trace("GET");

		return "Выполнить запрос HistoricalDataRequest"
				+ "\n"
				+ "Параметр\n"
				+ "dateStart : yyyymmdd\n"
				+ "dateEnd : yyyymmdd\n"
				+ "securities : [ security|currency ]\n"
				+ "fields\n"
				+ "\n"
				+ "Результат\n"
				+ "[ security -> [ date -> [ {field -> value;period;currency;calendar } ] ] ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Object execute(
			@RequestParam(required = false, defaultValue = "HistoricalDataRequest") String name,
			@RequestParam String dateStart,
			@RequestParam String dateEnd,
			@RequestParam String[] securities,
			@RequestParam String[] fields) {

		if (log.isInfoEnabled()) {
			log.info("POST execute " + dateStart + ", " + dateEnd);
			log.info("POST execute " + Arrays.asList(securities));
			log.info("POST execute " + Arrays.asList(fields));
		}

		try {
			return bs.executeHistoricalDataRequest(name, dateStart, dateEnd, securities, fields);
		} catch (Exception e) {
			log.error("POST execute " + e.getMessage(), e);
			return e;
		}
	}
}
