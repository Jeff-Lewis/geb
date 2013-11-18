/**
 * 
 */
package ru.prbb.analytics.rest.params;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.params.NewParamDao;

/**
 * Ввод нового параметра<br>
 * Ввод нового параметра blm_datasource_ovr
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/NewParam")
public class NewParamController
{
	@Autowired
	private NewParamDao dao;

	@RequestMapping(value = "/Setup", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData setup(
			@RequestParam String code)
	{
		return new ResultData(dao.setup(code));
	}

	@RequestMapping(value = "/Save", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result save(
			@RequestParam String blm_id,
			@RequestParam String code,
			@RequestParam String name)
	{
		dao.save(blm_id, code, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/SaveOvr", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result saveOvr(
			@RequestParam String code,
			@RequestParam String broker)
	{
		dao.saveOvr(code, broker);
		return Result.SUCCESS;
	}
}
