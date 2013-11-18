package ru.prbb.analytics.rest.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.utils.SendingDao;

/**
 * Рассылка E-mail и SMS
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Sending")
public class SendingController
{
	//	@Autowired
	private SendingDao dao;

	@RequestMapping(value = "/Phone", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboPhone(
			@RequestParam String query)
	{
		// select id, name, comment from dbo.sms_group
		return new ArrayList<SimpleItem>();
	}

	@RequestMapping(value = "/Mail", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboMail(
			@RequestParam String query)
	{
		// select name, value as mail from ncontacts_request_v where type ='E-mail' and lower(name) like ?
		return new ArrayList<SimpleItem>();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData download(
			@PathVariable("id") Long id)
	{
		// {call dbo.sms_template_proc}
		// {call dbo.sms_template_trader}
		return new ResultData("Рассылка для " + id);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Map<String, Object>> show(
			@RequestParam String text,
			@RequestParam String recp,
			@RequestParam String recm)
	{
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < 3; ++i) {
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", "status" + i);
			map.put("mail", "mail" + i);
			list.add(map);
		}
		return list;
	}
}
