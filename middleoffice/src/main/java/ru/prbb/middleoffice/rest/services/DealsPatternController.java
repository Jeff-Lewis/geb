package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.DealsPatternItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.repo.services.DealsPatternDao;

/**
 * Сохраненные шаблоны
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/DealsPattern")
public class DealsPatternController
{
	@Autowired
	private DealsPatternDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<DealsPatternItem> show()
	{
		return dao.show();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result deleteById(
			@PathVariable("id") Long id)
	{
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Object download(
			@PathVariable("id") Long id)
	{
		return null;
	}

	@RequestMapping(value = "/Upload", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Object upload(
			@RequestParam Long id)
	{
		return null;
	}
}
