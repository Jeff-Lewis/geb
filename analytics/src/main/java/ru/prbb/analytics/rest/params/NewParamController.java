/**
 * 
 */
package ru.prbb.analytics.rest.params;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.params.NewParamDao;

/**
 * Ввод нового параметра<br>
 * Ввод нового параметра blm_datasource_ovr
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/NewParam")
public class NewParamController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private NewParamDao dao;

	@RequestMapping(value = "/Setup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData postSetup(
			@RequestParam String code)
	{
		log.info("POST NewParam/Setup: code={}", code);
		return new ResultData(dao.setup(code));
	}

	@RequestMapping(value = "/Save", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postSave(
			@RequestParam String blm_id,
			@RequestParam String code,
			@RequestParam String name)
	{
		log.info("POST NewParam/Save: blm_id={}, code={}, name={}", Utils.asArray(blm_id, code, name));
		dao.save(blm_id, code, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/SaveOvr", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postSaveOvr(
			@RequestParam String code,
			@RequestParam String broker)
	{
		log.info("POST NewParam/SaveOvr: code={}, broker={}", code, broker);
		dao.saveOvr(code, broker);
		return Result.SUCCESS;
	}
}
