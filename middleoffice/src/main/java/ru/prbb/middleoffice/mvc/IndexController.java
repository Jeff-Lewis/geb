package ru.prbb.middleoffice.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String displayIndex()
	{
		log.info("GET /");

		return "index";
	}

}
