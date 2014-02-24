package ru.prbb.agent.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.prbb.agent.SetupAgent;

/**
 * @author RBr
 */
@RestController
public class IndexController {

	private final Log log = LogFactory.getLog(getClass());

	private final SetupAgent setup;

	@Autowired
	public IndexController(SetupAgent setup) {
		this.setup = setup;
	}

	/**
	 * @return Версия программы
	 */
	@RequestMapping(value = "/", produces = "text/plain;charset=utf-8")
	public String index() {
		log.trace("");

		return "BloombergAgent/" + setup.getHostAgent();
	}
}
