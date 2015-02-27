package ru.prbb.jobber.web;

import java.util.ArrayList;
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

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String post(
			@RequestParam Long id,
			@RequestParam String data)
	{
		//log.debug("POST /Subscription id={}: data={}", id, data);

		String[] lines = data.split("\n");
		List<String[]> result = new ArrayList<>(lines.length);
		for (String line : lines) {
			if (!line.isEmpty()) {
				String[] arr = line.split("\t");
				if (arr.length != 3) {
					log.error("Data line:" + line);
				} else {
					result.add(arr);
				}
			}
		}

		try {
			dao.subsUpdate(result);
			return "OK";
		} catch (Exception e) {
			log.error("Subscription update", e);
			return "ERROR:" + e.getMessage();
		}
	}

}
