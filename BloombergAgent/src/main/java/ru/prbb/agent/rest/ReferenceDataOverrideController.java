/**
 * 
 */
package ru.prbb.agent.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.prbb.agent.service.BloombergServices;

/**
 * @author RBr
 */
//@RestController
//@RequestMapping("/ReferenceDataOverride")
public class ReferenceDataOverrideController {

	private final Log log = LogFactory.getLog(getClass());

	private final BloombergServices bs;

	@Autowired
	public ReferenceDataOverrideController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String get() {
		log.trace("GET");

		return "Выполнить запрос //blp/refdata"
				+ "\n"
				+ "Параметр\n"
				+ "securities\n"
				+ "fields\n"
				+ "period\n"
				+ "over\n"
				+ "\n"
				+ "Результат\n"
				+ "[ security -> [ { field, value } ] ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Object execute(
			@RequestParam String[] securities,
			@RequestParam String[] fields,
			@RequestParam String period,
			@RequestParam String over) {
		log.trace("POST");

		try {
			return bs.executeBdpRequestOverride("BdpRequestOverride",
					securities, fields, period, over);
		} catch (Exception e) {
			return e;
		}
	}
}
