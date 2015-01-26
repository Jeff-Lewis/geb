package ru.prbb.jobber.web;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.domain.AgentItem;
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
	public Collection<AgentItem> get()
	{
		log.info("GET /Agents");
		return dao.list();
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public String put(
			@RequestParam String host)
	{
		log.info("PUT /Agents: host={}", host);

		dao.add(toInetAddress(host));

		return "OK";
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public String del(
			@RequestParam String host)
	{
		log.info("DELETE /Agents: host={}", host);

		dao.remove(toInetAddress(host));

		return "OK";
	}

	private InetAddress toInetAddress(String host) {
		try {
			return InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			log.error("Get InetAddress for " + host);
			throw new RuntimeException(e);
		}
	}

}
