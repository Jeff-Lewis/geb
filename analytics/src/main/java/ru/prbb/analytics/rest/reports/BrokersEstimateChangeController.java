package ru.prbb.analytics.rest.reports;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.BrokersEstimateChangeItem;
import ru.prbb.analytics.repo.reports.BrokersEstimateChangeDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Изменение оценок брокеров
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/BrokersEstimateChange")
public class BrokersEstimateChangeController
		extends BaseController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BrokersEstimateChangeDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BrokersEstimateChangeItem> getShow()
	{
		log.info("GET BrokersEstimateChange");
		return dao.execute();
	}
}
