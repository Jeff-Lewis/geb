package ru.prbb.analytics.rest.company;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.CompaniesExceptionItem;
import ru.prbb.analytics.domain.CompaniesFileItem;
import ru.prbb.analytics.domain.CompaniesListItem;
import ru.prbb.analytics.domain.CompaniesQuarterItem;
import ru.prbb.analytics.domain.CompaniesYearItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.company.CompaniesDao;
import ru.prbb.analytics.repo.model.BuildEPSDao;
import ru.prbb.analytics.repo.model.BuildModelDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Список компаний
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Companies")
public class CompaniesController
		extends BaseController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private CompaniesDao dao;
	@Autowired
	private BuildModelDao daoBuildModel;
	@Autowired
	private BuildEPSDao daoBuildEPS;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CompaniesListItem> getItems()
	{
		log.info("GET Companies");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET Companies: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/{id}/id", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getId(
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/id: id={}", id);
		return new ResultData(id);
	}

	@RequestMapping(value = "/{id}/eps", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postEps(
			@PathVariable("id") Long id,
			@RequestParam String type,
			@RequestParam Integer baseYear,
			@RequestParam Integer calcYear)
	{
		log.info("POST Companies/eps: id={}, type={}, baseYear={}, calcYear={}",
				Utils.asArray(id, type, baseYear, calcYear));
		dao.addEps(id, type, baseYear, calcYear);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/eps", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteEps(
			@PathVariable("id") Long id,
			@RequestParam String type)
	{
		type = Utils.encode(type);
		log.info("DEL Companies/eps: id={}, type={}", id, type);
		dao.delEps(id, type);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/bv", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postBV(
			@PathVariable("id") Long id,
			@RequestParam String type,
			@RequestParam Integer baseYear,
			@RequestParam Integer calcYear)
	{
		log.info("POST Companies/bv: id={}, type={}, baseYear={}, calcYear={}",
				Utils.asArray(id, type, baseYear, calcYear));
		dao.addBookVal(id, type, baseYear, calcYear);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/bv", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteBV(
			@PathVariable("id") Long id,
			@RequestParam String type)
	{
		type = Utils.encode(type);
		log.info("DEL Companies/bv: id={}, type={}", id, type);
		dao.delBookVal(id, type);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/formula", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postFormula(
			@PathVariable("id") Long id,
			@RequestParam String variable,
			@RequestParam String expression,
			@RequestParam String comment)
	{
		log.info("POST Companies/formula: id={}, variable={}, expression={}, comment={}",
				Utils.asArray(id, variable, expression, comment));
		dao.addFormula(id, variable, expression, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/formula", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteFormula(
			@PathVariable("id") Long id,
			@RequestParam String variable)
	{
		variable = Utils.encode(variable);
		log.info("DEL Companies/formula: id={}, variable={}", id, variable);
		dao.delFormula(id, variable);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Variables", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> getVariables(
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/Variables: id={}", id);
		return dao.getEquityVars(id);
	}

	@RequestMapping(value = "/{id}/Exceptions", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CompaniesExceptionItem> getExceptions(
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/Exceptions: id={}", id);
		return dao.findVarException(id);
	}

	@RequestMapping(value = "/{id}/Quarters", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CompaniesQuarterItem> getQuarters(
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/Quarters: id={}", id);
		return dao.findQuarters(id);
	}

	@RequestMapping(value = "/{id}/Years", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CompaniesYearItem> getYears(
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/Years: id={}", id);
		return dao.findYears(id);
	}

	@RequestMapping(value = "/{id}/EquityChange", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postEquityChange(
			@PathVariable("id") Long id,
			@RequestParam String bloomCode,
			@RequestParam String adr,
			@RequestParam String currency_calc,
			@RequestParam String group,
			@RequestParam String koefZero,
			@RequestParam String koefOne,
			@RequestParam String period,
			@RequestParam String eps)
	{
		log.info("POST Companies/EquityChange: id={}, bloomCode={}, adr={}, currency_calc={}, group={}," +
				" koefZero={}, koefOne={}, period={}, eps={}",
				Utils.asArray(id, bloomCode, adr, currency_calc, group,
						koefZero, koefOne, period, eps));
		Map<String, String> params = new HashMap<>();
		params.put("bloomberg_code", Utils.parseString(bloomCode));
		params.put("adr", Utils.parseString(adr));
		params.put("currency", Utils.parseString(currency_calc));
		params.put("pivot_group", Utils.parseString(group));
		params.put("koef", Utils.parseString(koefZero));
		params.put("new_koef", Utils.parseString(koefOne));
		params.put("period", Utils.parseString(period));
		params.put("eps", Utils.parseString(eps));

		dao.updateById(id, params);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/CalculateEps", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Result getCalculateEps(
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/CalculateEps: id={}", id);
		daoBuildEPS.calculate(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/BuildModel", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Result getBuildModel(
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/BuildModel: id={}", id);
		daoBuildModel.calculateModel(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Files", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CompaniesFileItem> getFiles(
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/Files: id={}", id);
		return dao.findFiles(id);
	}

	@RequestMapping(value = "/{id}/Upload", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpload(
			@PathVariable("id") Long id,
			@RequestParam("upload") MultipartFile file)
	{
		String name = file.getOriginalFilename();
		log.info("POST Companies/Upload: id={}, file={}", id, name);
		try {
			String type = file.getContentType();
			byte[] content = file.getBytes();
			dao.fileUpload(id, name, type, content);
		} catch (IOException e) {
			log.error("POST Companies/Upload", e);
			return Result.FAIL;
		}
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Files/{idFile}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public byte[] getFile(HttpServletResponse response,
			@PathVariable("id") Long id,
			@PathVariable("idFile") Long id_doc)
	{
		log.info("GET Companies/Files: id={}, idFile={}", id, id_doc);
		CompaniesFileItem item = dao.fileGetById(id, id_doc);
		response.setHeader("Content-disposition", "attachment;filename=" + item.getFile_name());
		response.setContentType(item.getFile_type());

		return dao.fileGetContentById(id, id_doc);
	}

	@RequestMapping(value = "/{id}/Files/{idFile}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteFile(
			@PathVariable("id") Long id,
			@PathVariable("idFile") Long id_doc)
	{
		log.info("DEL Companies/Files: id={}, idFile={}", id, id_doc);
		dao.fileDeleteById(id, id_doc);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Currencies", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboCurrencies(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Companies: Currencies='{}'", query);
		return dao.findComboCurrencies(query);
	}

	@RequestMapping(value = "/GroupSvod", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboGroupSvod(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Companies: GroupSvod='{}'", query);
		return dao.findComboGroupSvod(query);
	}

	@RequestMapping(value = "/Period", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboPeriod(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Companies: Period='{}'", query);
		return dao.findComboPeriod(query);
	}

	@RequestMapping(value = "/Eps", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEps(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Companies: Eps='{}'", query);
		return dao.findComboEps(query);
	}

	@RequestMapping(value = "/Variables", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboVariables(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Companies: Variables='{}'", query);
		return dao.findComboVariables(query);
	}
}
