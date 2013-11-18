package ru.prbb.middleoffice.rest.operations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.repo.operations.DealsLoadingDao;

/**
 * Загрузка сделок
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/DealsLoading")
public class DealsLoadingController
{
	// @Autowired
	private DealsLoadingDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Object upload()
	{
		return Result.SUCCESS;
	}
}
