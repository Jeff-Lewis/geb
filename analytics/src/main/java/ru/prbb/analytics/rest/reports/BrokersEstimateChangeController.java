package ru.prbb.analytics.rest.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.BrokersEstimateChangeItem;
import ru.prbb.analytics.repo.reports.BrokersEstimateChangeDao;

/**
 * Изменение оценок брокеров
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/BrokersEstimateChange")
public class BrokersEstimateChangeController
{
	@Autowired
	private BrokersEstimateChangeDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<BrokersEstimateChangeItem> show()
	{
		return dao.execute();
	}
}
