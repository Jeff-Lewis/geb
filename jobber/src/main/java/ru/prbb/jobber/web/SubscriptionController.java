package ru.prbb.jobber.web;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.repo.SubscriptionDao;

@Controller
@RequestMapping(value = "/Subscription", produces = "application/json")
public class SubscriptionController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SubscriptionDao dao;

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public String put(
			@RequestParam String data)
	{
		log.debug("PUT /Subscription: data={}", data);

		String[] rows = data.split("\n");
		for (String row : rows) {
			row
			dao.subsUpdate(list);
		}

		return "OK";
	}

}
