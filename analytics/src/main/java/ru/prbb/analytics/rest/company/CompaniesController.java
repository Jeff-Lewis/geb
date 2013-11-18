package ru.prbb.analytics.rest.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.CompaniesItem;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.company.CompaniesDao;

/**
 * Список компаний
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Companies")
public class CompaniesController
{
	@Autowired
	private CompaniesDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<CompaniesItem> list()
	{
		// {call dbo.anca_WebGet_EquityInfo_sp 0}
		return dao.show();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		//		select id_doc, file_type, file_name, insert_date from dbo.sec_docs where id_sec = ?
		//		{call dbo.anca_WebGet_EquityInfo_sp 1, ?}
		//		exec dbo.anca_WebGet_EquityInfo_sp 21, 
		//		exec dbo.anca_WebGet_EquityInfo_sp 22, 1
		//		exec dbo.output_equity_growthexclusions 1
		//		{call dbo.anca_WebGet_EquityVarException_sp ?}
		return new ResultData(dao.findById(id));
	}
}
