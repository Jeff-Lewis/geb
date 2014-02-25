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
@RequestMapping("/BdsRequest")
public class BdsRequestController {

	private final Log log = LogFactory.getLog(getClass());

	private final BloombergServices bs;

	@Autowired
	public BdsRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String get() {
		log.trace("GET");

		return "BdsRequest"
				+ "\n"
				+ "Параметр\n"
				+ "name\n"
				+ "securities\n"
				+ "fields"
				+ "\n"
				+ "Результат\n"
				+ "[ { id, security, date, value } ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Object execute(
			@RequestParam String[] securities,
			@RequestParam String[] fields) {
		log.trace("POST");

		try {
			return bs.executeBdsRequest("BdsRequest", securities, fields);
		} catch (Exception e) {
			return e;
		}
	}
}
