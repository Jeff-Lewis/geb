package ru.prbb.analytics.rest.bloomberg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ViewSubscriptionItem;
import ru.prbb.analytics.repo.bloomberg.ViewSubscriptionDao;

/**
 * Subscription
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewSubscription")
public class ViewSubscriptionController
{
	@Autowired
	private ViewSubscriptionDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<ViewSubscriptionItem> show()
	{
		return dao.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam String name,
			@RequestParam(required = false) String comment)
	{
		dao.put(name, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result del(
			@PathVariable(value = "id") Long id)
	{
		dao.delete(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/start", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result start(
			@PathVariable(value = "id") Long id)
	{
		dao.start(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/stop", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result stop(
			@PathVariable(value = "id") Long id)
	{
		dao.stop(id);
		return Result.SUCCESS;
	}
}
