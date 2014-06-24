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
import ru.prbb.analytics.rest.BaseController;

/**
 * Справочник параметров
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewParams")
public class ViewParamsController
		extends BaseController
{

	@Autowired
	private ViewParamsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ViewParamsItem> getItems()
	{
		log.info("GET ViewParams");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") String blm_id)
	{
		log.info("GET ViewParams: blm_id={}", blm_id);
		return new ResultData(dao.findById(blm_id));
	}
}
