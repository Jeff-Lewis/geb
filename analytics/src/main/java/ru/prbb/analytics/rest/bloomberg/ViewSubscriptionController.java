package ru.prbb.analytics.rest.bloomberg;

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

import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.repo.bloomberg.ViewSubscriptionDao;

/**
 * Subscription
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Subscription")
public class ViewSubscriptionController
{
	//	@Autowired
	private ViewSubscriptionDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Map<String, Object>> show()
	{
		// {call dbo.output_subscriptions_prc}
		final ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			list.add(map);
			map.put("id_subscr", new Long(i + 1));
			map.put("subscription_name", "SUBSCRIPTION_NAME_" + (i + 1));
			map.put("subscription_comment", "SUBSCRIPTION_COMMENT_" + (i + 1));
			map.put("subscription_status", "SUBSCRIPTION_STATUS_" + (i + 1));
		}
		return list;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Object add(
			@RequestParam String name,
			@RequestParam(defaultValue = "") String comment)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Object del(
			@PathVariable(value = "id") Long id)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/start", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Object start(
			@PathVariable(value = "id") Long id)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/stop", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Object stop(
			@PathVariable(value = "id") Long id)
	{
		return Result.SUCCESS;
	}
}
