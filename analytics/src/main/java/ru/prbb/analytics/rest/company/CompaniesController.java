package ru.prbb.analytics.rest.company;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.CompanyItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
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
	List<CompanyItem> list()
	{
		return dao.findAll();
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

	@RequestMapping(value = "/Exceptions", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Object> getExceptions(
			@RequestParam Long id)
	{
		String sql = "{call dbo.anca_WebGet_EquityVarException_sp :id}";
		// 'Exception', 'comment'
		return new ArrayList<Object>();
	}

	@RequestMapping(value = "/Quarters", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Object> getQuarters(
			@RequestParam Long id)
	{
		String sql = "exec dbo.anca_WebGet_EquityInfo_sp 21, :id";
		//		'period', 'value', 'crnc', 'date', 'eqy_dps',
		//		'eqy_dvd_yld_ind', 'sales_rev_turn', 'prof_margin',
		//		'oper_margin', 'crnc'
		return new ArrayList<Object>();
	}

	@RequestMapping(value = "/Years", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Object> getYears(
			@RequestParam Long id)
	{
		String sql = "exec dbo.anca_WebGet_EquityInfo_sp 22, :id";
		//		'period', 'value', 'crnc', 'date', 'eps_recon_flag',
		//		'eqy_dps', 'eqy_weighted_avg_px', 'eqy_weighted_avg_px_adr',
		//		'book_val_per_sh', 'oper_roe', 'r_ratio', 'crnc'
		return new ArrayList<Object>();
	}

	@RequestMapping(value = "/Files", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Object> getFiles(
			@RequestParam Long id)
	{
		String sql = "select id_doc, file_type, file_name, insert_date from dbo.sec_docs where id_sec=:id";
		//		'id_doc', 'file_name', 'id_doc', 'file_name'
		return new ArrayList<Object>();
	}

	@RequestMapping(value = "/{id}/EquityChange", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result execEquityChange(
			@PathVariable Long id,
			@RequestParam String bloomCode,
			@RequestParam Double adr,
			@RequestParam String currency_calc,
			@RequestParam String group,
			@RequestParam Double koefZero,
			@RequestParam Double koefOne,
			@RequestParam String period,
			@RequestParam String eps)
	{
		//		class MyPair {
		//			final String attribute;
		//			final String value;
		//
		//			MyPair(String attribute, String value) {
		//				this.attribute = attribute;
		//				this.value = value;
		//			}
		//		}
		//
		//		final List<MyPair> params = Lists.newArrayList();
		//		params.add(new MyPair("bloomberg_code", security_code));
		//		params.add(new MyPair("adr", adr));
		//		params.add(new MyPair("currency", currency_iso));
		//		params.add(new MyPair("pivot_group", group_name));
		//		params.add(new MyPair("koef", upside_koef_0));
		//		params.add(new MyPair("new_koef", upside_koef_1));
		//		params.add(new MyPair("period", period));
		//		params.add(new MyPair("eps", eps));
		//
		//		String sql = "{call dbo.anca_WebSet_EquityAttributes_sp ?, ?, ?}";
		//		log.info(sql);
		//		jdbcTemplate.batchUpdate(sql,
		//				new BatchPreparedStatementSetter() {
		//
		//					@Override
		//					public void setValues(PreparedStatement ps, int i) throws SQLException {
		//						final MyPair param = params.get(i);
		//						ps.setLong(1, id_sec);
		//						ps.setString(2, param.attribute);
		//						ps.setString(3, Translate.toNull(param.value));
		//					}
		//
		//					@Override
		//					public int getBatchSize() {
		//						return params.size();
		//					}
		//				});
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/CalculateEps", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result execCalculateEps(
			@PathVariable Long id)
	{
		String sql = "{call main_create_eps_proc ?}";
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/BuildModelCompany", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result execBuildModelCompany(
			@PathVariable Long id)
	{
		String sql = "{call dbo.build_model_proc_p ?}";
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/ScanSave", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result getScanSave(
			@PathVariable Long id)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/ScanOpen", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result getScanOpen(
			@PathVariable Long id)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/ScanDelete", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result getScanDelete(
			@PathVariable Long id)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Currencies", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCurrencies(
			@RequestParam(required = false) String query)
	{
		return dao.findComboCurrencies(query);
	}

	@RequestMapping(value = "/GroupSvod", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboGroupSvod(
			@RequestParam(required = false) String query)
	{
		return dao.findComboGroupSvod(query);
	}

	@RequestMapping(value = "/Period", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboPeriod(
			@RequestParam(required = false) String query)
	{
		return dao.findComboPeriod(query);
	}

	@RequestMapping(value = "/Eps", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEps(
			@RequestParam(required = false) String query)
	{
		return dao.findComboEps(query);
	}

	@RequestMapping(value = "/Variables", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboVariables(
			@RequestParam(required = false) String query)
	{
		return dao.findComboVariables(query);
	}
}
