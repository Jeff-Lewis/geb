package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.OptionsItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.dictionary.OptionsDao;
import ru.prbb.middleoffice.repo.dictionary.TradesystemsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Опционы
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Options")
public class OptionsController
		extends BaseController
{

	@Autowired
	private OptionsDao dao;
	@Autowired
	private TradesystemsDao daoTradesystems;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<OptionsItem> getItems()
	{
		log.info("GET Options");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET Options: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/Coefficients/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getCoefficientsItem(
			@PathVariable("id") Long id)
	{
		log.info("GET Options/Coefficients: id={}", id);
		return new ResultData(dao.findCoefficientById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam String name,
			@RequestParam Double coef,
			@RequestParam String comment,
			@RequestParam Long tradeSystemId)
	{
		log.info("POST Options add: name={}, coef={}, comment={}, tradeSystemId={}",
				Utils.toArray(name, coef, comment, tradeSystemId));
		dao.put(name, coef, comment, tradeSystemId);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Coefficients", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddCoefficientItem(
			@RequestParam Long futureId,
			@RequestParam Double coef,
			@RequestParam String comment,
			@RequestParam Long tradeSystemId)
	{
		log.info("POST Options add: futureId={}, coef={}, comment={}, tradeSystemId={}",
				Utils.toArray(futureId, coef, comment, tradeSystemId));
		dao.putCoefficient(futureId, coef, comment, tradeSystemId);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(
			@PathVariable("id") Long id,
			@RequestParam String name)
	{
		log.info("POST Options update: id={}, name={}", id, name);
		dao.updateById(id, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Coefficients/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateCoefficientsItem(
			@PathVariable("id") Long coefId,
			@RequestParam Double coef,
			@RequestParam String comment,
			@RequestParam Long tradeSystemId,
			@RequestParam Long futureId)
	{
		log.info("POST Options/Coefficients update: coefId={}, coef={}, comment={}, tradeSystemId={}, futureId={}",
				Utils.toArray(coefId, coef, comment, tradeSystemId, futureId));
		dao.updateCoefficientById(coefId, coef, comment, tradeSystemId, futureId);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result delete(
			@PathVariable("id") Long id)
	{
		log.info("DEL Options: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Coefficients/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteCoefficients(
			@PathVariable("id") Long id)
	{
		log.info("DEL Options/Coefficients: id={}", id);
		dao.deleteCoefficientById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Tradesystems", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboTradesystems(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Options: Tradesystems='{}'", query);
		return daoTradesystems.findCombo(query);
	}

}
