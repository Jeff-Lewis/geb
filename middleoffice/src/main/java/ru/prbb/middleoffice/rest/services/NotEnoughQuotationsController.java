package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.NotEnoughQuotationsItem;
import ru.prbb.middleoffice.repo.services.NotEnoughQuotationsDao;

/**
 * Не хватает котировок
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/NotEnoughQuotations")
public class NotEnoughQuotationsController
{
	@Autowired
	private NotEnoughQuotationsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<NotEnoughQuotationsItem> show()
	{
		return dao.show();
	}
}
