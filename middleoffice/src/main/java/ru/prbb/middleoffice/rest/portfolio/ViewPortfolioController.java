package ru.prbb.middleoffice.rest.portfolio;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Export;
import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultProgress;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewPortfolioItem;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.portfolio.ViewPortfolioDao;

/**
 * Текущий портфель
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewPortfolio")
public class ViewPortfolioController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ViewPortfolioDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<ViewPortfolioItem> postShow(
			@RequestParam String date,
			@RequestParam Long security)
	{
		log.info("POST ViewPortfolio: date={}, security={}", date, security);
		return dao.executeSelect(Utils.parseDate(date), security);
	}

	private Result p = Result.FAIL;

	private Boolean isBusyCalc = Boolean.FALSE;

	@RequestMapping(value = "/Calculate", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postCalculate(
			@RequestParam String date,
			@RequestParam Long security)
	{
		if (isBusyCalc == Boolean.TRUE) {
			log.info("POST ViewPortfolio/Calculate: busy date={}, security={}", date, security);
			return Result.FAIL;
		}
		isBusyCalc = Boolean.TRUE;

		log.info("POST ViewPortfolio/Calculate: date={}, security={}", date, security);
		try {
			p = new ResultProgress(0, "Расчёт запущен.");

			Calendar beg = createCalendar();
			beg.setTime(Utils.parseDate(date));

			Calendar end = createCalendar();
			end.add(Calendar.DAY_OF_MONTH, -1);

			final double total = end.getTimeInMillis() - beg.getTimeInMillis();

			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String time = "";
			String info = " Расчёт с: " + sdf.format(beg.getTime());
			if (Utils.isNotEmpty(security))
				info += " для Тикер: " + security;

			final long stTime = System.currentTimeMillis();
			for (Calendar dateCalc = (Calendar) beg.clone(); dateCalc.before(end); dateCalc.add(Calendar.DAY_OF_MONTH, 1)) {
				final double v = (dateCalc.getTimeInMillis() - beg.getTimeInMillis()) / total;
				String text = time + sdf.format(dateCalc.getTime()) + info;
				p = new ResultProgress(v, text);

				dao.executeCalc(new Date(dateCalc.getTimeInMillis()), security);

				long runTime = System.currentTimeMillis() - stTime;
				time = "Прошло, сек: " + Long.valueOf(runTime / 1000) + " ";
			}
		} finally {
			p = Result.FAIL;
			isBusyCalc = Boolean.FALSE;
		}
		return Result.SUCCESS;
	}

	private Calendar createCalendar() {
		Calendar c = Calendar.getInstance();
		c.clear(Calendar.HOUR_OF_DAY);
		c.clear(Calendar.MINUTE);
		c.clear(Calendar.SECOND);
		c.clear(Calendar.MILLISECOND);
		return c;
	}

	@RequestMapping(value = "/Calculate/Progress", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Result getCalculateProgress()
	{
		return p;
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getExport(HttpServletResponse response,
			@RequestParam String date,
			@RequestParam Long security)
	{
		log.info("GET ViewPortfolio/Export: date={}, security={}", date, security);
		List<ViewPortfolioItem> list =
				dao.executeSelect(Utils.parseDate(date), security);

		Export exp = Export.newInstance();
		exp.setCaption("Текущий портфель");
		exp.addTitle("Текущий портфель на дату " + date);
		exp.setColumns(
				"report_date",
				"client",
				"fund",
				"security_code",
				"short_name",
				"batch",
				"usd_funding",
				"currency",
				"quantity",
				"avg_price",
				"last_price",
				"nkd",
				"position",
				"position_rep_dat",
				"revaluation");
		for (ViewPortfolioItem item : list) {
			exp.addRow(
					item.getReport_date(),
					item.getClient(),
					item.getFund(),
					item.getSecurity_code(),
					item.getShort_name(),
					item.getBatch(),
					item.getUsd_funding(),
					item.getCurrency(),
					item.getQuantity(),
					item.getAvg_price(),
					item.getLast_price(),
					item.getNkd(),
					item.getPosition(),
					item.getPosition_rep_date(),
					item.getRevaluation());
		}

		String name = "Portfolio.ods";
		response.setHeader("Content-disposition", "attachment;filename=" + name);
		response.setContentType(exp.getContentType());
		return exp.build();
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewPortfolio: Securities='{}'", query);
		return daoSecurities.findCombo(query);
	}
}
