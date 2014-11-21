/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.ViewModelInfoItem;
import ru.prbb.analytics.domain.ViewModelItem;
import ru.prbb.analytics.domain.ViewModelPriceItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * Просмотр текущей модели
 * 
 * @author RBr
 * 
 */
@Service
public class ViewModelDaoImpl extends BaseDaoImpl implements ViewModelDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewModelItem> findAll() {
		String sql = "{call dbo.MainPageReportProc}";
		Query q = em.createNativeQuery(sql, ViewModelItem.class);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public ViewModelInfoItem getInfoById(Long id_sec) {
		String sql = "select id_sec, factEPS1Q, factEPS2Q, factEPS3Q, factEPS4Q, TargetPriceCons12M, " +
				"TargetPriceDecCons, TargetPrice, BestPrice, r, teta, PriceMedian, LastYearAvgWhtPrice, " +
				"M1Q, M2Q, M3Q, M4Q, forecastEPS2Q, forecastEPS3Q, forecastEPS4Q, forecastEPS, " +
				"forecastEPS12M, forecastEPS_NextYear, forecastEPS1QNext, forecastEPS2QNext, " +
				"forecastEPS3QNext,	forecastEPS4QNext, forecastEPScons, forecastEPScons12M, " +
				"EPSttm, g5, g10, gk, PE_5, PE_10, PE_current, PE_ttm, PE_cons, date_ins" +
				" from dbo.model_report" +
				" where  dbo.model_report.date_ins = " +
				"  (select max(mr1.date_ins)" +
				"     from inv_db.dbo.model_report mr1" +
				"    where mr1.id_sec = dbo.model_report.id_sec) " +
				" and id_sec = ?";
		Query q = em.createNativeQuery(sql, ViewModelInfoItem.class)
				.setParameter(1, id_sec);
		return (ViewModelInfoItem) getSingleResult(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<ViewModelPriceItem> findPriceById(Long id_sec) {
		String sql = "select equity_fund_ticker, company_short_name, firm_name, bloomberg_code, " +
				"firm_rating, target_price, price_date, price_period, TR" +
				" from dbo.AnalystTargetComputingV" +
				" where id_sec = ?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id_sec);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<ViewModelPriceItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			ViewModelPriceItem item = new ViewModelPriceItem();
			item.setEquity_fund_ticker(Utils.toString(arr[0]));
			item.setCompany_short_name(Utils.toString(arr[1]));
			item.setFirm_name(Utils.toString(arr[2]));
			item.setBloomberg_code(Utils.toString(arr[3]));
			item.setFirm_rating(Utils.toInteger(arr[4]));
			item.setTarget_price(Utils.toDouble(arr[5]));
			item.setPrice_date(Utils.toString(arr[6]));
			item.setPrice_period(Utils.toString(arr[7]));
			item.setTR(Utils.toInteger(arr[8]));
			res.add(item);
		}
		return res;
	}
}
