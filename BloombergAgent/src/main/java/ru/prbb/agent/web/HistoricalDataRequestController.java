/**
 * 
 */
package ru.prbb.agent.web;

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
				+ "securities\n"
				+ "fields\n"
				+ "\n"
				+ "Результат\n"
				+ "[ security -> [ date -> [ {field -> value;period;currency;calendar } ] ] ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Object execute(
			@RequestParam String dateStart,
			@RequestParam String dateEnd,
			@RequestParam String[] securities,
			@RequestParam String[] fields) {
		log.trace("POST");

		try {
			return bs.executeHistoricalDataRequest("HistoricalDataRequest",
					dateStart, dateEnd, securities, fields);
		} catch (Exception e) {
			return e;
		}
	}
}
