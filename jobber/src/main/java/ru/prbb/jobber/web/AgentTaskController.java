package ru.prbb.jobber.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/AgentTask", produces = "application/json")
public class AgentTaskController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String get()
	{
		return "ERROR:Deprecated";
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String put(HttpServletRequest request)
	{
		return "ERROR:Deprecated";
	}

}
