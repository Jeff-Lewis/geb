package ru.prbb.middleoffice.rest.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.services.SendingDao;

/**
 * Рассылка E-mail и SMS
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Sending")
public class SendingController
{
	@Autowired
	private SendingDao dao;

	@RequestMapping(value = "/Phone", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboPhone(
			@RequestParam(required = false) String query)
	{
		return dao.findComboPhone(query);
	}

	@RequestMapping(value = "/Mail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboMail(
			@RequestParam(required = false) String query)
	{
		return dao.findComboMail(query);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData download(
			@PathVariable("id") Long id)
	{
		String text;
		switch (id.intValue()) {
		case 0:
			text = dao.getAnalitic();
			break;
		case 1:
			text = dao.getTrader();
			break;

		default:
			text = "Текст не задан.";
			break;
		}
		return new ResultData(text);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Map<String, Object>> show(
			@RequestParam String text,
			@RequestParam String recp,
			@RequestParam String recm)
	{
		return dao.execute(text, recp, recm);
	}
}
