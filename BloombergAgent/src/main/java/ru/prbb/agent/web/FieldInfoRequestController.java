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

import ru.prbb.agent.service.BloombergServices;

/**
 * @author RBr
 */
@RestController
@RequestMapping("/FieldInfoRequest")
public class FieldInfoRequestController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final BloombergServices bs;

	@Autowired
	public FieldInfoRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String getHelp() {
		log.trace("GET");

		return "FieldInfoRequest //blp/apiflds, FieldInfoRequest"
				+ "\n"
				+ "Параметр\n"
				+ "code\n"
				+ "\n"
				+ "Результат\n"
				+ "{ BLM_ID, NAME, CODE }\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Object postExecute(
			@RequestParam(required = false, defaultValue = "FieldInfoRequest") String name,
			@RequestParam String code)
	{
		log.info("POST FieldInfoRequest: name={}", name);
		log.info("POST FieldInfoRequest: code={}", code);

		try {
			return bs.executeFieldInfoRequest(name, code);
		} catch (Exception e) {
			log.error("POST FieldInfoRequest " + e.getMessage(), e);
			return e;
		}
	}
}
