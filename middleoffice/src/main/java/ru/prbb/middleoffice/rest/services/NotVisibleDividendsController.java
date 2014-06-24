package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.NotEnoughDividendsItem;
import ru.prbb.middleoffice.repo.services.NotVisibleDividendsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Нет настроек для дивидендов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/NotVisibleDividends")
public class NotVisibleDividendsController
		extends BaseController
{

	@Autowired
	private NotVisibleDividendsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<NotEnoughDividendsItem> getItems()
	{
		log.info("GET NotVisibleDividends");
		return dao.show();
	}
}
