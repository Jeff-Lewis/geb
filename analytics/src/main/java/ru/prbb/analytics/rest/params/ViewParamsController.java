/**
 * 
 */
package ru.prbb.analytics.rest.params;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.ViewParamsItem;
import ru.prbb.analytics.repo.params.ViewParamsDao;

/**
 * Справочник параметров
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewParams")
public class ViewParamsController
{
	@Autowired
	private ViewParamsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<ViewParamsItem> list()
	{
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData info(
			@PathVariable("id") String blm_id)
	{
		return new ResultData(dao.findById(blm_id));
	}
}
