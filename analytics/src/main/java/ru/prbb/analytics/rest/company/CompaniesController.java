package ru.prbb.analytics.rest.company;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping(value = "/rest/Companies", produces = "application/json")
public class CompaniesController
		extends BaseController
{

	@Autowired
	private CompaniesDao dao;
	@Autowired
	private BuildModelDao daoBuildModel;
	@Autowired
	private BuildEPSDao daoBuildEPS;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<CompaniesListItem> getItems(HttpServletRequest request)
	{
		log.info("GET Companies");
		return dao.findAll(createUserInfo(request));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultData getItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Companies: id={}", id);
		return new ResultData(dao.findById(createUserInfo(request), id));
	}

	@RequestMapping(value = "/{id}/id", method = RequestMethod.GET)
	@ResponseBody
	public ResultData getId(
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/id: id={}", id);
		return new ResultData(id);
	}

	@RequestMapping(value = "/{id}/eps", method = RequestMethod.POST)
	@ResponseBody
	public Result postEps(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String type,
			@RequestParam Integer baseYear,
			@RequestParam Integer calcYear)
	{
		log.info("POST Companies/eps: id={}, type={}, baseYear={}, calcYear={}",
				Utils.asArray(id, type, baseYear, calcYear));
		dao.addEps(createUserInfo(request), id, type, baseYear, calcYear);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/eps", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteEps(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String type)
	{
		type = transform(type);
		log.info("DEL Companies/eps: id={}, type={}", id, type);
		dao.delEps(createUserInfo(request), id, type);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/bv", method = RequestMethod.POST)
	@ResponseBody
	public Result postBV(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String type,
			@RequestParam Integer baseYear,
			@RequestParam Integer calcYear)
	{
		log.info("POST Companies/bv: id={}, type={}, baseYear={}, calcYear={}",
				Utils.asArray(id, type, baseYear, calcYear));
		dao.addBookVal(createUserInfo(request), id, type, baseYear, calcYear);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/bv", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteBV(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String type)
	{
		type = transform(type);
		log.info("DEL Companies/bv: id={}, type={}", id, type);
		dao.delBookVal(createUserInfo(request), id, type);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/formula", method = RequestMethod.POST)
	@ResponseBody
	public Result postFormula(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String variable,
			@RequestParam String expression,
			@RequestParam String comment)
	{
		log.info("POST Companies/formula: id={}, variable={}, expression={}, comment={}",
				Utils.asArray(id, variable, expression, comment));
		dao.addFormula(createUserInfo(request), id, variable, expression, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/formula", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteFormula(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String variable)
	{
		variable = transform(variable);
		log.info("DEL Companies/formula: id={}, variable={}", id, variable);
		dao.delFormula(createUserInfo(request), id, variable);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Variables", method = RequestMethod.GET)
	@ResponseBody
	public List<SimpleItem> getVariables(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/Variables: id={}", id);
		return dao.getEquityVars(createUserInfo(request), id);
	}

	@RequestMapping(value = "/{id}/Exceptions", method = RequestMethod.GET)
	@ResponseBody
	public List<CompaniesExceptionItem> getExceptions(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/Exceptions: id={}", id);
		return dao.findVarException(createUserInfo(request), id);
	}

	@RequestMapping(value = "/{id}/Quarters", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<CompaniesQuarterItem> getQuarters(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam(required = false, defaultValue = "2") Integer idCalendar)
	{
		log.info("GET Companies/Quarters: id={}, idCalendar={}", id, idCalendar);
		return dao.findQuarters(createUserInfo(request), id, idCalendar);
	}

	@RequestMapping(value = "/{id}/Quarters", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteQuarters(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String code,
			@RequestParam String period,
			@RequestParam String date,
			@RequestParam String currency)
	{
		log.info("DELETE Companies/Quarters: id={}, code={}, period={}, date={}, currency={}",
				Utils.asArray(id, code, period, date, currency));
		dao.delQuarters(createUserInfo(request), id, code, period, Utils.parseDate(date), currency);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Years", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<CompaniesYearItem> getYears(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam(required = false, defaultValue = "2") Integer idCalendar)
	{
		log.info("GET Companies/Years: id={}, idCalendar={}", id, idCalendar);
		return dao.findYears(createUserInfo(request), id, idCalendar);
	}

	@RequestMapping(value = "/{id}/Years", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteYears(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String code,
			@RequestParam String period,
			@RequestParam String date,
			@RequestParam String currency)
	{
		log.info("DELETE Companies/Years: id={}, code={}, period={}, date={}, currency={}",
				Utils.asArray(id, code, period, date, currency));
		dao.delYears(createUserInfo(request), id, code, period, Utils.parseDate(date), currency);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/EquityChange", method = RequestMethod.POST)
	@ResponseBody
	public Result postEquityChange(HttpServletRequest request,
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

		dao.updateById(createUserInfo(request), id, params);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/CalculateEps", method = RequestMethod.GET)
	@ResponseBody
	public Result getCalculateEps(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/CalculateEps: id={}", id);
		daoBuildEPS.calculate(createUserInfo(request), id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/BuildModel", method = RequestMethod.GET)
	@ResponseBody
	public Result getBuildModel(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/BuildModel: id={}", id);
		daoBuildModel.calculateModel(createUserInfo(request), id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/HistData", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteHistData(HttpServletRequest request,
			@PathVariable("id") Long id) {
		log.info("DEL Companies/HistData: id={}", id);
		dao.delHistData(createUserInfo(request), id);
		return Result.SUCCESS;

	}

	@RequestMapping(value = "/{id}/Files", method = RequestMethod.GET)
	@ResponseBody
	public List<CompaniesFileItem> getFiles(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Companies/Files: id={}", id);
		return dao.findFiles(createUserInfo(request), id);
	}

	@RequestMapping(value = "/{id}/Upload", method = RequestMethod.POST)
	@ResponseBody
	public Result postUpload(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam("upload") MultipartFile file)
	{
		String name = file.getOriginalFilename();
		log.info("POST Companies/Upload: id={}, file={}", id, name);
		try {
			String type = file.getContentType();
			byte[] content = file.getBytes();
			dao.fileUpload(createUserInfo(request), id, name, type, content);
		} catch (IOException e) {
			log.error("POST Companies/Upload", e);
			return Result.FAIL;
		}
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Files/{idFile}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getFile(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") Long id,
			@PathVariable("idFile") Long id_doc)
	{
		log.info("GET Companies/Files: id={}, idFile={}", id, id_doc);
		CompaniesFileItem item = dao.fileGetById(createUserInfo(request), id, id_doc);
		response.setHeader("Content-disposition", "attachment;filename=" + item.getFile_name());
		response.setContentType(item.getFile_type());

		return dao.fileGetContentById(createUserInfo(request), id, id_doc);
	}

	@RequestMapping(value = "/{id}/Files/{idFile}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteFile(HttpServletRequest request,
			@PathVariable("id") Long id,
			@PathVariable("idFile") Long id_doc)
	{
		log.info("DEL Companies/Files: id={}, idFile={}", id, id_doc);
		dao.fileDeleteById(createUserInfo(request), id, id_doc);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Currencies", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<SimpleItem> comboCurrencies(HttpServletRequest request,
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Companies: Currencies='{}'", query);
		return dao.findComboCurrencies(createUserInfo(request), query);
	}

	@RequestMapping(value = "/GroupSvod", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<SimpleItem> comboGroupSvod(HttpServletRequest request,
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Companies: GroupSvod='{}'", query);
		return dao.findComboGroupSvod(createUserInfo(request), query);
	}

	@RequestMapping(value = "/Period", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<SimpleItem> comboPeriod(HttpServletRequest request,
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Companies: Period='{}'", query);
		return dao.findComboPeriod(createUserInfo(request), query);
	}

	@RequestMapping(value = "/Eps", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<SimpleItem> comboEps(HttpServletRequest request,
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Companies: Eps='{}'", query);
		return dao.findComboEps(createUserInfo(request), query);
	}

	@RequestMapping(value = "/Calendar", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<SimpleItem> comboCalendar(HttpServletRequest request,
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Companies: Calendar='{}'", query);
		return dao.findComboCalendar(createUserInfo(request), query);
	}

	@RequestMapping(value = "/Variables", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<SimpleItem> comboVariables(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Companies: Variables='{}'", query);
		return dao.findComboVariables(null, query);
	}

	private String transform(String s) {
		String res = s;
		try {
			String utf = new String(s.getBytes("ISO-8859-1"));
			res = new String(utf.getBytes(), "CP1251");
			res = utf;
		} catch (UnsupportedEncodingException ignore) {
			log.error("UnsupportedEncodingException", ignore);
		}
		return res;
	}
}
