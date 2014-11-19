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
import org.springframework.stereotype.Controller;

import ru.prbb.agent.services.BloombergServices;

/**
 * @author RBr
 */
@Controller
@RequestMapping("/LoadBdpOverrideRequest")
public class LoadBdpOverrideRequestController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final BloombergServices bs;

	@Autowired
	public LoadBdpOverrideRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String getHelp() {
		log.trace("GET");

		return "Выполнить запрос //blp/refdata"
				+ "\n"
				+ "Параметр\n"
				+ "currencies : []\n"
				+ "securities : []\n"
				+ "\n"
				+ "Результат\n"
				+ "[ { security, date, value } ] ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Object postExecute(
			@RequestParam(required = false, defaultValue = "BDP override") String name,
			@RequestParam String[] securities,
			@RequestParam String[] currencies)
	{
		log.info("POST LoadBdpOverrideRequest: name={}", name);
		log.info("POST LoadBdpOverrideRequest: currencies={}", (Object) currencies);
		log.info("POST LoadBdpOverrideRequest: securities={}", (Object) securities);

		try {
			return bs.executeBdpOverrideLoad(securities, currencies);
		} catch (Exception e) {
			log.error("POST LoadBdpOverrideRequest " + e.getMessage(), e);
			return e;
		}
	}
}
