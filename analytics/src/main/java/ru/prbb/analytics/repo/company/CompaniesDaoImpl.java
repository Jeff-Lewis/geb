/**
 * 
 */
package ru.prbb.analytics.repo.company;

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

/**
 * Список компаний
 * 
 * @author RBr
 */
@Repository
public class CompaniesDaoImpl implements CompaniesDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CompaniesListItem> findAll() {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 0}";
		Query q = em.createNativeQuery(sql, CompaniesListItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public CompaniesItem findById(Long id) {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 1, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		Object[] arr = (Object[]) q.getSingleResult();
		CompaniesItem item = new CompaniesItem();
		item.setId_sec(Utils.toLong(arr[0]));
		item.setIsin(Utils.toString(arr[1]));
		item.setSecurity_name(Utils.toString(arr[2]));
		item.setSecurity_code(Utils.toString(arr[3]));
		item.setTicker(Utils.toString(arr[4]));
		item.setCurrency(Utils.toString(arr[5]));
		item.setAdr(Utils.toString(arr[6]));
		item.setIndstry_grp(Utils.toString(arr[7]));
		item.setSvod_grp(Utils.toString(arr[8]));
		item.setKoefUpside(Utils.toDouble(arr[9]));
		item.setKoefUpsideNM(Utils.toDouble(arr[10]));
		item.setPeriod(Utils.toString(arr[11]));
		item.setEps(Utils.toString(arr[12]));
		item.setG10(Utils.toString(arr[13]));
		item.setG5(Utils.toString(arr[14]));
		item.setB10(Utils.toString(arr[15]));
		item.setB5(Utils.toString(arr[16]));
		item.setPe10(Utils.toString(arr[17]));
		item.setPe5(Utils.toString(arr[18]));
		return item;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateById(Long id, Map<String, Object> params) {
		String sql = "{call dbo.anca_WebSet_EquityAttributes_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(1, id);
		for (Entry<String, Object> param : params.entrySet()) {
			q.setParameter(2, param.getKey());
			q.setParameter(3, param.getValue());
			q.executeUpdate();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<CompaniesQuarterItem> findQuarters(Long id) {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 21, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		@SuppressWarnings("unchecked")
		List<Object[]> list = q.getResultList();
		List<CompaniesQuarterItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			CompaniesQuarterItem item = new CompaniesQuarterItem();
			item.setSecurity_code(Utils.toString(arr[0]));
			item.setPeriod(Utils.toString(arr[1]));
			item.setDate(Utils.toDate(arr[2]));
			item.setValue(Utils.toDouble(arr[3]));
			item.setEqy_dps(Utils.toDouble(arr[4]));
			item.setEqy_dvd_yld_ind(Utils.toDouble(arr[5]));
			item.setSales_rev_turn(Utils.toDouble(arr[6]));
			item.setProf_margin(Utils.toDouble(arr[7]));
			item.setOper_margin(Utils.toDouble(arr[8]));
			item.setCrnc(Utils.toString(arr[9]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<CompaniesYearItem> findYears(Long id) {
		String sql = "{call dbo.anca_WebGet_EquityInfo_sp 22, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		@SuppressWarnings("unchecked")
		List<Object[]> list = q.getResultList();
		List<CompaniesYearItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			CompaniesYearItem item = new CompaniesYearItem();
			item.setSecurity_code(Utils.toString(arr[0]));
			item.setPeriod(Utils.toString(arr[1]));
			item.setDate(Utils.toString(arr[2]));
			item.setValue(Utils.toDouble(arr[3]));
			item.setEps_recon_flag(Utils.toInteger(arr[4]));
			item.setEqy_dps(Utils.toDouble(arr[5]));
			item.setEqy_weighted_avg_px(Utils.toDouble(arr[6]));
			item.setEqy_weighted_avg_px_adr(Utils.toDouble(arr[7]));
			item.setBook_val_per_sh(Utils.toDouble(arr[8]));
			item.setOper_roe(Utils.toDouble(arr[9]));
			item.setR_ratio(Utils.toDouble(arr[10]));
			item.setCrnc(Utils.toString(arr[11]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CompaniesFileItem> findFiles(Long id) {
		String sql = "select id_doc, file_type, file_name, insert_date from dbo.sec_docs where id_sec=?";
		Query q = em.createNativeQuery(sql, CompaniesFileItem.class)
				.setParameter(1, id);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<CompaniesExceptionItem> findVarException(Long id) {
		String sql = "{call dbo.anca_WebGet_EquityVarException_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		@SuppressWarnings("unchecked")
		List<Object[]> list = q.getResultList();
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
		return Utils.toSimpleItem(q.getResultList());
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
		return Utils.toSimpleItem(q.getResultList());
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
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findComboEps(String query) {
		String sql = "select id, name from anca_WebGet_ajaxEPSparams_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return q.getResultList();
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
		return Utils.toSimpleItem(q.getResultList());
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
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public CompaniesFileItem fileGetById(Long id, Long id_doc) {
		String sql = "select id_doc, file_type, file_name, insert_date"
				+ " from dbo.sec_docs where id_doc=?";
		Query q = em.createNativeQuery(sql, CompaniesFileItem.class)
				.setParameter(1, id_doc);
		return (CompaniesFileItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public byte[] fileGetContentById(Long id, Long id_doc) {
		String sql = "select file from dbo.sec_docs where id_doc=?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_doc);
		return (byte[]) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int fileDeleteById(Long id, Long id_doc) {
		String sql = "delete from dbo.sec_docs where id_doc=?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_doc);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> getEquityVars(Long id) {
		String sql = "{call dbo.equity_vars_notused ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return Utils.toSimpleItem(q.getResultList());
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
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delEps(Long id, String type) {
		String sql = "{call dbo.del_notst_eps_growth_rate ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, type);
		return q.executeUpdate();
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
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delBookVal(Long id, String type) {
		String sql = "{call dbo.del_bv_growth_notstandard ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, type);
		return q.executeUpdate();
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
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delFormula(Long id, String variable) {
		String sql = "{call dbo.anca_WebSet_dEquityVarException_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, variable);
		return q.executeUpdate();
	}
}
