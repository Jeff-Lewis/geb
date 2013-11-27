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
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SecuritySubscrItem;
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

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SecuritySubscrItem> getSecurities(
			@RequestParam(required = false) Long id)
	{
		return dao.findAllSecurities(id);
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.get(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result del(
			@PathVariable("id") Long id)
	{
		dao.delete(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/start", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result start(
			@PathVariable("id") Long id)
	{
		dao.start(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/stop", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result stop(
			@PathVariable("id") Long id)
	{
		dao.stop(id);
		return Result.SUCCESS;
	}
}
