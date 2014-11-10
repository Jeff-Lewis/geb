package ru.prbb.jobber.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class IndexController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model)
	{
		log.info("GET /");
		return "index";
	}
}
