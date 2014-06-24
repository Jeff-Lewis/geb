package ru.prbb.analytics.rest.bloomberg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SecuritySubscrItem;
import ru.prbb.analytics.domain.ViewSubscriptionItem;
import ru.prbb.analytics.repo.bloomberg.ViewSubscriptionDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Subscription
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewSubscription")
public class ViewSubscriptionController
		extends BaseController
{

	@Autowired
	private ViewSubscriptionDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ViewSubscriptionItem> getItems()
	{
		log.info("GET ViewSubscription");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET ViewSubscription: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItem(
			@RequestParam String name,
			@RequestParam(required = false) String comment)
	{
		log.info("POST ViewSubscription: name={}, comment={}", name, comment);
		dao.put(name, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL ViewSubscription: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result post(
			@PathVariable("id") Long id,
			@RequestParam String action,
			@RequestParam Long[] ids)
	{
		log.info("POST ViewSubscription: id={}, action={}, ids={}", Utils.asArray(id, action, (Object) ids));
		action = action.toUpperCase();
		switch (action) {
		case "ADD":
			dao.staffAdd(id, ids);
			return Result.SUCCESS;

		case "DEL":
			dao.staffDel(id, ids);
			return Result.SUCCESS;
		}
		return Result.FAIL;
	}

	@RequestMapping(value = "/Securities", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SecuritySubscrItem> getSecurities()
	{
		log.info("GET ViewSubscription/Securities");
		return dao.findAllSecurities();
	}

	@RequestMapping(value = "/Securities", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<SecuritySubscrItem> postSecurities(
			@RequestParam Long id)
	{
		log.info("POST ViewSubscription/Securities: id={}", id);
		return dao.findAllSecurities(id);
	}

	@RequestMapping(value = "/{id}/start", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Result start(
			@PathVariable("id") Long id)
	{
		log.info("GET ViewSubscription/start: id={}", id);
		dao.start(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/stop", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Result stop(
			@PathVariable("id") Long id)
	{
		log.info("GET ViewSubscription/stop: id={}", id);
		dao.stop(id);
		return Result.SUCCESS;
	}
}
