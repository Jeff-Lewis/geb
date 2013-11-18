package ru.prbb.analytics.rest.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.ViewModelItem;
import ru.prbb.analytics.repo.model.ViewModelDao;

/**
 * Просмотр текущей модели
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewModel")
public class ViewModelController
{
	@Autowired
	private ViewModelDao dao;

	@RequestMapping(value = "/Current", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<ViewModelItem> list()
	{
		// exec MainPageReportProc
		return dao.current();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData info(
			@PathVariable("id") Long id_sec)
	{
		//		"select id_sec,factEPS1Q,factEPS2Q,factEPS3Q,factEPS4Q,TargetPriceCons12M," +
		//		"TargetPriceDecCons,TargetPrice,BestPrice," +
		//		"r,teta,PriceMedian,LastYearAvgWhtPrice,M1Q,M2Q,M3Q,M4Q,forecastEPS2Q," +
		//		"forecastEPS3Q,forecastEPS4Q,forecastEPS,forecastEPS12M,forecastEPS_NextYear," +
		//		"forecastEPS1QNext,forecastEPS2QNext,forecastEPS3QNext,	forecastEPS4QNext," +
		//		"forecastEPScons,forecastEPScons12M,EPSttm,g5,g10,gk,PE_5,PE_10,PE_current," +
		//		"PE_ttm,PE_cons,date_ins from dbo.model_report where " +
		//		"dbo.model_report.date_ins = (select max(mr1.date_ins)" +
		//		" from inv_db.dbo.model_report mr1 where mr1.id_sec = dbo.model_report.id_sec) " +
		//		" and id_sec=?"
		return new ResultData(dao.getById(id_sec));
	}
}
