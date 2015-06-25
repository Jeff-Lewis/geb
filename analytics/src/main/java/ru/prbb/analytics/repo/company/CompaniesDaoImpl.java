/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.CompaniesExceptionItem;
import ru.prbb.analytics.domain.CompaniesFileItem;
import ru.prbb.analytics.domain.CompaniesItem;
import ru.prbb.analytics.domain.CompaniesListItem;
import ru.prbb.analytics.domain.CompaniesQuarterItem;
import ru.prbb.analytics.domain.CompaniesYearItem;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * Список компаний
 * 
 * @author RBr
 */
@Repository
public class CompaniesDaoImpl extends BaseDaoImpl implements CompaniesDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CompaniesListItem> findAll() {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 0}";
		Query q = em.createNativeQuery(sql, CompaniesListItem.class);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public CompaniesItem findById(Long id) {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 1, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		Object[] arr = (Object[]) getSingleResult(q, sql);
		CompaniesItem item = new CompaniesItem();
		int i = 0;
		// id
		item.setId_sec(Utils.toLong(arr[i++]));
		// ISIN
		item.setIsin(Utils.toString(arr[i++]));
		// Название компании
		item.setSecurity_name(Utils.toString(arr[i++]));
		// Код Блумберг
		item.setSecurity_code(Utils.toString(arr[i++]));
		// Родной тикер
		item.setTicker(Utils.toString(arr[i++]));
		// Валюта расчета
		item.setCurrency(Utils.toString(arr[i++]));
		// ADR
		item.setAdr(Utils.toString(arr[i++]));
		// Сектор
		item.setIndstry_grp(Utils.toString(arr[i++]));
		// Группа в сводной
		item.setSvod_grp(Utils.toString(arr[i++]));
		// Koef Upside
		item.setKoefUpside(Utils.toDouble(arr[i++]));
		// Koef Upside н.м.
		item.setKoefUpsideNM(Utils.toDouble(arr[i++]));
		// Периодичность отчетности
		item.setPeriod(Utils.toString(arr[i++]));
		// EPS
		item.setEps(Utils.toString(arr[i++]));
		// g10 =
		item.setG10(Utils.toString(arr[i++]));
		// g5 =
		item.setG5(Utils.toString(arr[i++]));
		// b10 =
		item.setB10(Utils.toString(arr[i++]));
		// b5 =
		item.setB5(Utils.toString(arr[i++]));
		// PE10 =
		item.setPe10(Utils.toString(arr[i++]));
		// PE5 =
		item.setPe5(Utils.toString(arr[i++]));
		// crncy
		item.setCurrencyTrade(Utils.toString(arr[i++]));
		// eqy_fund_crncy
		item.setCurrencyReport(Utils.toString(arr[i++]));
		// px_last
		item.setPx_last(Utils.toDouble(arr[i++]));
		// peCurrent
		item.setPeCurrent(Utils.toDouble(arr[i++]));
		// methodOld
		item.setMethodOld(Utils.toDouble(arr[i++]));
		// methodNew
		item.setMethodNew(Utils.toDouble(arr[i++]));
		// consensus
		item.setConsensus(Utils.toDouble(arr[i++]));
		// roe
		item.setRoe(Utils.toDouble(arr[i++]));
		return item;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateById(Long id, Map<String, String> params) {
		String sql = "{call dbo.anca_WebSet_EquityAttributes_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(1, id);
		for (Entry<String, String> param : params.entrySet()) {
			q.setParameter(2, param.getKey());
			q.setParameter(3, param.getValue());
			storeSql(sql, q);
			executeUpdate(q, sql);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<CompaniesQuarterItem> findQuarters(Long id, Number idCalendar) {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 21, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, idCalendar);
		@SuppressWarnings("unchecked")
		List<Object[]> list = getResultList(q, sql);
		List<CompaniesQuarterItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			CompaniesQuarterItem item = new CompaniesQuarterItem();
			int idx = 0;
			//Код Блумберг
			item.setSecurity_code(Utils.toString(arr[idx++]));
			//Период
			item.setPeriod(Utils.toString(arr[idx++]));
			//date
			item.setDate(Utils.toDate(arr[idx++]));
			//EPS
			item.setEps(Utils.toDouble(arr[idx++]));
			//IS_EPS
			item.setIs_eps(Utils.toDouble(arr[idx++]));
			//IS_COMP_EPS_ADJUSTED
			item.setIs_comp_eps_adjusted(Utils.toDouble(arr[idx++]));
			//IS_COMP_EPS_EXCL_STOCK_COMP
			item.setIs_comp_eps_excl_stock_comp(Utils.toDouble(arr[idx++]));
			//IS_BASIC_EPS_CONT_OPS
			item.setIs_basic_eps_cont_ops(Utils.toDouble(arr[idx++]));
			//IS_DIL_EPS_CONT_OPS
			item.setIs_dil_eps_cont_ops(Utils.toDouble(arr[idx++]));
			//EBITDA
			item.setEbitda(Utils.toDouble(arr[idx++]));
			//BEST_EBITDA
			item.setBest_ebitda(Utils.toDouble(arr[idx++]));
			//SALES_REV_TURN
			item.setSales_rev_turn(Utils.toDouble(arr[idx++]));
			//NET_REV
			item.setNet_rev(Utils.toDouble(arr[idx++]));
			//PROF_MARGIN
			item.setProf_margin(Utils.toDouble(arr[idx++]));
			//OPER_MARGIN
			item.setOper_margin(Utils.toDouble(arr[idx++]));
			//OPER_ROE
			item.setOper_roe(Utils.toDouble(arr[idx++]));
			//EQY_DPS
			item.setEqy_dps(Utils.toDouble(arr[idx++]));
			//EQY_DVD_YLD_IND
			item.setEqy_dvd_yld_ind(Utils.toDouble(arr[idx++]));
			//Currency
			item.setCurrency(Utils.toString(arr[idx++]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delQuarters(Long id_sec, String security_code, String period, Date date, String iso) {
		String sql = "{call dbo.anca_WebSet_dEquityPeriodData_sp 'Q', ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_sec)
				.setParameter(2, security_code)
				.setParameter(3, period)
				.setParameter(4, date)
				.setParameter(5, iso);   
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}


	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<CompaniesYearItem> findYears(Long id, Number idCalendar) {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 22, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, idCalendar);
		@SuppressWarnings("unchecked")
		List<Object[]> list = getResultList(q, sql);
		List<CompaniesYearItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			CompaniesYearItem item = new CompaniesYearItem();
			int idx = 0;
			//Код Блумберг
			item.setSecurity_code(Utils.toString(arr[idx++]));
			//Период
			item.setPeriod(Utils.toString(arr[idx++]));
			//date
			item.setDate(Utils.toString(arr[idx++]));
			//EPS
			item.setEps(Utils.toDouble(arr[idx++]));
			//IS_EPS
			item.setIs_eps(Utils.toDouble(arr[idx++]));
			//IS_COMP_EPS_ADJUSTED
			item.setIs_comp_eps_adjusted(Utils.toDouble(arr[idx++]));
			//IS_COMP_EPS_EXCL_STOCK_COMP
			item.setIs_comp_eps_excl_stock_comp(Utils.toDouble(arr[idx++]));
			//IS_BASIC_EPS_CONT_OPS
			item.setIs_basic_eps_cont_ops(Utils.toDouble(arr[idx++]));
			//IS_DIL_EPS_CONT_OPS
			item.setIs_dil_eps_cont_ops(Utils.toDouble(arr[idx++]));
			//EBITDA
			item.setEbitda(Utils.toDouble(arr[idx++]));
			//BEST_EBITDA
			item.setBest_ebitda(Utils.toDouble(arr[idx++]));
			//SALES_REV_TURN
			item.setSales_rev_turn(Utils.toDouble(arr[idx++]));
			//NET_REV
			item.setNet_rev(Utils.toDouble(arr[idx++]));
			//OPER_MARGIN
			item.setOper_margin(Utils.toDouble(arr[idx++]));
			//PROF_MARGIN
			item.setProf_margin(Utils.toDouble(arr[idx++]));
			//OPER_ROE
			item.setOper_roe(Utils.toDouble(arr[idx++]));
			//RETENTION_RATIO
			item.setRetention_ratio(Utils.toDouble(arr[idx++]));
			//EQY_DPS
			item.setEqy_dps(Utils.toDouble(arr[idx++]));
			//EQY_DVD_YLD_IND
			item.setEqy_dvd_yld_ind(Utils.toDouble(arr[idx++]));
			//IS_AVG_NUM_SH_FOR_EPS
			item.setIs_avg_num_sh_for_eps(Utils.toDouble(arr[idx++]));
			//BOOK_VAL_PER_SH
			item.setBook_val_per_sh(Utils.toDouble(arr[idx++]));
			//EQY_WEIGHTED_AVG_PX
			item.setEqy_weighted_avg_px(Utils.toDouble(arr[idx++]));
			//EQY_WEIGHTED_AVG_PX_ADR
			item.setEqy_weighted_avg_px_adr(Utils.toDouble(arr[idx++]));
			//EPS_RECONSTRUCT_FLAG
			item.setEps_reconstruct_flag(Utils.toInteger(arr[idx++]));
			//Currency
			item.setCurrency(Utils.toString(arr[idx++]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delYears(Long id_sec, String security_code, String period, Date date, String iso) {
		String sql = "{call dbo.anca_WebSet_dEquityPeriodData_sp 'Y', ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_sec)
				.setParameter(2, security_code)
				.setParameter(3, period)
				.setParameter(4, date)
				.setParameter(5, iso);   
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}


	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CompaniesFileItem> findFiles(Long id) {
		String sql = "select id_doc, file_type, file_name, insert_date from dbo.sec_docs where id_sec=?";
		Query q = em.createNativeQuery(sql, CompaniesFileItem.class)
				.setParameter(1, id);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<CompaniesExceptionItem> findVarException(Long id) {
		String sql = "{call dbo.anca_WebGet_EquityVarException_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		@SuppressWarnings("unchecked")
		List<Object[]> list = getResultList(q, sql);
		List<CompaniesExceptionItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			CompaniesExceptionItem item = new CompaniesExceptionItem();
			item.setException(Utils.toString(arr[0]));
			item.setComment(Utils.toString(arr[1]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findComboCurrencies(String query) {
		String sql = "select iso as name from dbo.currency_iso_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += " where lower(iso) like ?";
			q = em.createNativeQuery(sql)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return Utils.toSimpleItem(getResultList(q, sql));
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findComboGroupSvod(String query) {
		String sql = "select name from dbo.PivotGroupsV";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return Utils.toSimpleItem(getResultList(q, sql));
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findComboPeriod(String query) {
		String sql = "select period_id as id, name from dbo.period_type";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findComboEps(String query) {
		String sql = "select id, name from dbo.anca_WebGet_ajaxEPSparams_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findComboCalendar(String query) {
		String sql = "select calendar_id as id, name from calendar_type";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findComboVariables(String query) {
		String sql = "select var_user_name as name from dbo.model_variables";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += " where lower(var_user_name) like ?";
			q = em.createNativeQuery(sql)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return Utils.toSimpleItem(getResultList(q, sql));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int fileUpload(Long id, String name, String type, byte[] content) {
		String sql = "insert into dbo.sec_docs " +
				"(id_sec, file, file_type, file_name) " +
				"values (?, ?, ?, ?)";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, content)
				.setParameter(3, type)
				.setParameter(4, name);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public CompaniesFileItem fileGetById(Long id, Long id_doc) {
		String sql = "select id_doc, file_type, file_name, insert_date from dbo.sec_docs"
				+ " where id_doc=?";
		Query q = em.createNativeQuery(sql, CompaniesFileItem.class)
				.setParameter(1, id_doc);
		return (CompaniesFileItem) getSingleResult(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public byte[] fileGetContentById(Long id, Long id_doc) {
		String sql = "select file from dbo.sec_docs where id_doc=?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_doc);
		return (byte[]) getSingleResult(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int fileDeleteById(Long id, Long id_doc) {
		String sql = "delete from dbo.sec_docs where id_doc=?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_doc);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> getEquityVars(Long id) {
		String sql = "{call dbo.equity_vars_notused ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return Utils.toSimpleItem(getResultList(q, sql));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int addEps(Long id, String type, Integer baseYear, Integer calcYear) {
		String sql = "{call dbo.add_notst_eps_growth_rate ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, type)
				.setParameter(3, baseYear)
				.setParameter(4, calcYear);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delEps(Long id, String type) {
		String sql = "{call dbo.del_notst_eps_growth_rate ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, type);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int addBookVal(Long id, String type, Integer baseYear, Integer calcYear) {
		String sql = "{call dbo.add_bv_growth_notstandard ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, type)
				.setParameter(3, baseYear)
				.setParameter(4, calcYear);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delBookVal(Long id, String type) {
		String sql = "{call dbo.del_bv_growth_notstandard ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, type);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int addFormula(Long id, String variable, String expression, String comment) {
		String sql = "{call dbo.anca_WebSet_putEquityVarException_sp ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, variable)
				.setParameter(3, expression)
				.setParameter(4, comment);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delFormula(Long id, String variable) {
		String sql = "{call dbo.anca_WebSet_dEquityVarException_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, variable);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delHistData(Long id_sec) {
		String sql = "{call dbo.anca_WebSet_dhist_params_values_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_sec);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

}
