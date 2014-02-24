package ru.prbb.agent.rest;

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
 * Загрузка номинала
 * 
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
	public Map<String, Object> execute(
			@RequestParam String name,
			@RequestParam String[] securities,
			@RequestParam String[] fields) {
		log.trace("POST");

		return bs.executeBdsRequest(name, securities, fields);
	}
}
