package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.NotEnoughCouponsItem;
import ru.prbb.middleoffice.repo.services.NotVisibleCouponsDao;

/**
 * Нет настроек для купонов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/NotVisibleCoupons")
public class NotVisibleCouponsController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private NotVisibleCouponsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<NotEnoughCouponsItem> getItems()
	{
		log.info("GET NotVisibleCoupons");
		return dao.show();
	}
}