/**
 * 
 */
package ru.prbb.agent.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.prbb.agent.services.BloombergServices;

/**
 * @author RBr
 */
@RestController
@RequestMapping("/BdhEpsRequest")
public class BdhEpsRequestController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final BloombergServices bs;

	@Autowired
	public BdhEpsRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String getHelp() {
		log.info("GET help");

		return "Выполнить запрос //blp/refdata, HistoricalDataRequest"
				+ "\n"
				+ "Параметр\n"
				+ "dateStart : yyyymmdd\n"
				+ "dateEnd : yyyymmdd\n"
				+ "period\n"
				+ "calendar\n"
				+ "currencies\n"
				+ "securities : [ security|currency ]\n"
				+ "fields\n"
				+ "\n"
				+ "Результат\n"
				+ "[ security|currency -> [ date -> [ {field -> value;period;relative_date;currency;calendar } ] ] ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Object postExecute(
			@RequestParam(required = false, defaultValue = "BdhEpsRequest") String name,
			@RequestParam String dateStart,
			@RequestParam String dateEnd,
			@RequestParam String period,
			@RequestParam String calendar,
			@RequestParam String[] currencies,
			@RequestParam String[] securities,
			@RequestParam String[] fields)
	{
		log.info("POST BdhEpsRequest: name={}", name);
		log.info("POST BdhEpsRequest: dateStart={}, dateEnd={}, period={}, calendar={}", dateStart, dateEnd, period, calendar);
		log.info("POST BdhEpsRequest: currencies={}", (Object) currencies);
		log.info("POST BdhEpsRequest: securities={}", (Object) securities);
		log.info("POST BdhEpsRequest: fields={}", (Object) fields);

		try {
			return bs.executeBdhEpsRequest(name, dateStart, dateEnd, period, calendar,
					currencies, securities, fields);
		} catch (Exception e) {
			log.error("POST BdhEpsRequest " + e.getMessage(), e);
			return e;
		}
	}
}
