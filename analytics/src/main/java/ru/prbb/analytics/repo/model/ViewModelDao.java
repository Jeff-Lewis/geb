/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.ViewModelInfoItem;
import ru.prbb.analytics.domain.ViewModelItem;
import ru.prbb.analytics.domain.ViewModelPriceItem;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Просмотр текущей модели
 * 
 * @author RBr
 */
@Service
public class ViewModelDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ViewModelItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.MainPageReportProc}";
		return ems.getSelectList(user, ViewModelItem.class, sql);
	}

	public ViewModelInfoItem getInfoById(ArmUserInfo user, Long id_sec) {
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

		return ems.getSelectItem(user, ViewModelInfoItem.class, sql, id_sec);
	}

	public List<ViewModelPriceItem> findPriceById(ArmUserInfo user, Long id_sec) {
		String sql = "select equity_fund_ticker, company_short_name, firm_name, bloomberg_code, " +
				"firm_rating, target_price, price_date, price_period, TR" +
				" from dbo.AnalystTargetComputingV" +
				" where id_sec = ?";

		return ems.getSelectList(user, ViewModelPriceItem.class, sql, id_sec);
	}
}
