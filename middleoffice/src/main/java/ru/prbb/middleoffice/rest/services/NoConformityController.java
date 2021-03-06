package ru.prbb.middleoffice.rest.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Export;
import ru.prbb.middleoffice.domain.NoConformityItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.repo.services.NoConformityDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Нет соответствия
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/NoConformity")
public class NoConformityController
		extends BaseController
{

	@Autowired
	private NoConformityDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<NoConformityItem> getShow(HttpServletRequest request)
	{
		log.info("GET NoConformity");
		return dao.show(createUserInfo(request));
	}

	@RequestMapping(value = "/Delete", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postDelete(HttpServletRequest request,
			@RequestParam Long[] ids)
	{
		log.info("POST NoConformity: ids={}", ids);
		dao.delete(createUserInfo(request),ids);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/ExportXls", method = RequestMethod.GET)
	@ResponseBody
	public byte[] export(HttpServletRequest request, HttpServletResponse response)
	{
		log.info("GET NoConformity/ExportXls");
		List<NoConformityItem> list = dao.show(createUserInfo(request));

		Export exp = Export.newInstance();
		exp.setCaption("Нет соответствия");
		exp.addTitle("Нет соответствия");
		exp.setColumns(
				"TradeNum",
				"Date",
				"SecShortName",
				"Operation");
		for (NoConformityItem item : list) {
			exp.addRow(
					item.getTradeNum(),
					item.getTradeDate(),
					item.getSecShortName(),
					item.getOperation());
		}

		String name = "NoConformity.ods";
		response.setHeader("Content-disposition", "attachment;filename=" + name);
		response.setContentType(exp.getContentType());
		return exp.build();
	}
}
