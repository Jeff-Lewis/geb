package ru.prbb.agent.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author RBr
 */
@Controller
public class WebController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/jquery.js", method = RequestMethod.GET)
	public String getJQueryJs() {
		return "jquery-2.1.3";
	}

	@RequestMapping(value = "/jquery.cookie.js", method = RequestMethod.GET)
	public String getJQueryCookieJs() {
		return "jquery.cookie";
	}
}
