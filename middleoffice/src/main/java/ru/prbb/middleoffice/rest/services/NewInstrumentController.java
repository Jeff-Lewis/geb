package ru.prbb.middleoffice.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.repo.services.NewInstrumentDao;

/**
 * Ввод нового инструмента
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/NewInstrument")
public class NewInstrumentController
{
	@Autowired
	private NewInstrumentDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData add(
			@RequestParam String[] instruments)
	{
		return new ResultData(dao.execute(instruments));
	}
}
