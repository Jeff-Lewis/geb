package ru.prbb.jobber.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.repo.AgentsDao;

@Controller
@RequestMapping(value = "/Agents", produces = "application/json")
public class AgentsController
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AgentsDao dao;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String get()
	{
		log.info("GET /Agents");

		StringBuilder res = new StringBuilder();
		for (String host : dao.list()) {
			res.append(host).append('\n');
		}

		return res.toString();
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public String put(
			@RequestParam String host)
	{
		log.info("PUT /Agents: host={}", host);

		dao.add(host);

		return "OK";
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public String del(
			@RequestParam String host)
	{
		log.info("DELETE /Agents: host={}", host);

		dao.remove(host);

		return "OK";
	}

}
