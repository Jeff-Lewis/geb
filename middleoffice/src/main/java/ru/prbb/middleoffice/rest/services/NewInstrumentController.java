package ru.prbb.middleoffice.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 */
@Controller
@RequestMapping("/rest/NewInstrument")
public class NewInstrumentController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private NewInstrumentDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData postAdd(
			@RequestParam String[] instruments)
	{
		for (String instrument : instruments) {
			log.info("POST NewInstrument: instrument={}", instrument);
		}
		return new ResultData(dao.execute(instruments));
	}
}
