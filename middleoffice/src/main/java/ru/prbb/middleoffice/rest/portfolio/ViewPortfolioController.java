package ru.prbb.middleoffice.rest.portfolio;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	private static final String PROGRESS = "Progress";

	@Autowired
	private ViewPortfolioDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<ViewPortfolioItem> show(
			@RequestParam String date,
			@RequestParam Long security)
	{
		return dao.executeSelect(Utils.parseDate(date), security);
	}

	@RequestMapping(value = "/Calculate", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result calculate(HttpServletRequest request,
			@RequestParam String date,
			@RequestParam Long security)
	{
		HttpSession session = request.getSession(false);
		if (null == session) {
			return Result.FAIL;
		}
		ResultProgress p;
		try {
			p = new ResultProgress(0, "");
			session.setAttribute(PROGRESS, p);

			Calendar dateCalc = createCalendar();
			dateCalc.setTime(Utils.parseDate(date));

			Calendar end = createCalendar();
			end.add(Calendar.DAY_OF_MONTH, -1);

			final long begin = dateCalc.getTimeInMillis();
			final double total = end.getTimeInMillis() - begin;

			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			while (dateCalc.before(end)) {
				final double v = (dateCalc.getTimeInMillis() - begin) / total;
				p = new ResultProgress(v, sdf.format(dateCalc.getTime()));
				session.setAttribute(PROGRESS, p);

				dao.executeCalc(new Date(dateCalc.getTimeInMillis()), security);

				dateCalc.add(Calendar.DAY_OF_MONTH, 1);
			}
		} finally {
			session.removeAttribute(PROGRESS);
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
	public @ResponseBody
	ResultProgress progress(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if (null == session) {
			return ResultProgress.FAIL;
		}
		ResultProgress p = (ResultProgress) session.getAttribute(PROGRESS);
		if (null == p) {
			return ResultProgress.FAIL;
		}
		return p;
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET)
	@ResponseBody
	public byte[] export(HttpServletResponse response,
			@RequestParam String date,
			@RequestParam Long security)
	{
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
	public @ResponseBody
	List<SimpleItem> comboSecurities(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findCombo(query);
	}
}
