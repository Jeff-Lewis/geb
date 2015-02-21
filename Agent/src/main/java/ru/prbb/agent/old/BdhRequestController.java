/**
 * 
 */
package ru.prbb.agent.old;

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
@RequestMapping("/BdhRequest")
public class BdhRequestController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final BloombergServices bs;

	@Autowired
	public BdhRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getHelp() {
		log.trace("GET help");

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
				+ "[ security|currency -> [ date -> [ {field -> value;period;currency;calendar } ] ] ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object getExecute(
			@RequestParam(required = false, defaultValue = "BdhRequest") String name,
			@RequestParam String dateStart,
			@RequestParam String dateEnd,
			@RequestParam String period,
			@RequestParam String calendar,
			@RequestParam String[] currencies,
			@RequestParam String[] securities,
			@RequestParam String[] fields)
	{
		log.info("POST BdhRequest: name={}", name);
		log.info("POST BdhRequest: dateStart={}, dateEnd={}, period={}, calendar={}", dateStart, dateEnd, period, calendar);
		log.info("POST BdhRequest: currencies={}", (Object) currencies);
		log.info("POST BdhRequest: securities={}", (Object) securities);
		log.info("POST BdhRequest: fields={}", (Object) fields);

		try {
			return bs.executeBdhRequest(name, dateStart, dateEnd, period, calendar,
					currencies, securities, fields);
		} catch (Exception e) {
			log.error("POST BdhRequest " + e.getMessage(), e);
			return e;
		}
	}
}
