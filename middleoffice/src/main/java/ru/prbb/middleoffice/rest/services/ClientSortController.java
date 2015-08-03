package ru.prbb.middleoffice.rest.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.ClientSortItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.repo.services.ClientSortDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Сортировка клиентов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ClientSort")
public class ClientSortController
		extends BaseController
{

	@Autowired
	private ClientSortDao dao;

	@RequestMapping(value = "/Selected", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ClientSortItem> getSelected(HttpServletRequest request)
	{
		log.info("GET ClientSort/Selected");
		return dao.showSelected(createUserInfo(request));
	}

	@RequestMapping(value = "/Unselected", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ClientSortItem> getUnselected(HttpServletRequest request)
	{
		log.info("GET ClientSort/Unselected");
		return dao.showUnselected(createUserInfo(request));
	}

	@RequestMapping(value = "/Action", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAction(HttpServletRequest request,
			@RequestParam Long id,
			@RequestParam Integer flag)
	{
		log.info("POST ClientSort/Action: id={}, flag={}", id, flag);

		dao.action(createUserInfo(request),id, flag);

		return Result.SUCCESS;
	}

	@RequestMapping(value = "/SetDate", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postSetDate(HttpServletRequest request,
			@RequestParam Long id,
			@RequestParam String dateBegin)
	{
		log.info("POST ClientSort/SetDate: id={}, dateBegin={}", id, dateBegin);

		dao.setDate(createUserInfo(request),id, Utils.parseDate(dateBegin));

		return Result.SUCCESS;
	}

}
