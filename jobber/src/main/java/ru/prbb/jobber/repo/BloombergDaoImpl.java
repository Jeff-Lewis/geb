/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.jobber.bloomberg.BdsRequest.BEST_ANALYST_RECS_BULK;
import ru.prbb.jobber.bloomberg.BdsRequest.PeerData;
import ru.prbb.jobber.domain.AtrDataItem;
import ru.prbb.jobber.domain.BloombergResultItem;
import ru.prbb.jobber.domain.CashFlowResultItem;
import ru.prbb.jobber.domain.HistParamData;
import ru.prbb.jobber.domain.OverrideData;
import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.UpdateFutureData;

/**
 * @author RBr
 * 
 */
@Repository
public class BloombergDaoImpl implements BloombergDao
{
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<String> getSecForAtr() {
		String sql = "select security_code from dbo.mo_job_LoadATR_v";
		Query q = em.createNativeQuery(sql);
		showSql(sql, q);
		return q.getResultList();
	}

	@SuppressWarnings("static-access")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putAtrData(List<AtrDataItem> items) {
		String sql = "{call dbo.mo_WebSet_putATR_sp ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (AtrDataItem item : items) {
			try {
				q.setParameter(1, item.security);
				q.setParameter(2, item.date_time);
				q.setParameter(3, item.atr_value);
				q.setParameter(4, item.atr_period);
				q.setParameter(5, item.algorithm);
				q.setParameter(6, item.ds_high_code);
				q.setParameter(7, item.ds_low_code);
				q.setParameter(8, item.ds_close_code);
				q.setParameter(9, item.period);
				q.setParameter(10, item.calendar);
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SecForJobRequest> getLoadEstimatesPeersData() {
		String sql = "select security_code as code, iso from dbo.anca_job_LoadEstimatesPeersData_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		showSql(sql, q);
		List<SecForJobRequest> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			String code = Utils.toString(arr[0]);
			String iso = Utils.toString(arr[1]);
			res.add(new SecForJobRequest(code, iso));
		}
		return res;
	}

	@SuppressWarnings("static-access")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putOverrideData(List<OverrideData> items) {
		String sql = "{call dbo.put_override_data ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (OverrideData item : items) {
			try {
				q.setParameter(1, item.security);
				q.setParameter(2, item.param);
				q.setParameter(3, item.value);
				q.setParameter(4, item.period);
				q.setParameter(5, item.blm_data_src_over);
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putAnalysData(String peer, List<BEST_ANALYST_RECS_BULK> items) {
		// TODO Auto-generated method stub
		//				return jdbcTemplate.update(,
		String sql = "{call dbo.put_analyst_data ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(1, peer);
		for (BEST_ANALYST_RECS_BULK item : items) {
			try {
				q.setParameter(2, item.firm);
				q.setParameter(3, item.analyst);
				q.setParameter(4, item.recom);
				q.setParameter(5, item.rating);
				q.setParameter(6, item.action_code);
				q.setParameter(7, item.target_price);
				q.setParameter(8, item.period);
				q.setParameter(9, item.date);
				q.setParameter(10, item.barr);
				q.setParameter(11, item.year_return);
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putPeersData(List<PeerData> items) {
		String sql = "{call dbo.put_blmpeers_descr_proc ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (PeerData item : items) {
			try {
				q.setParameter(1, item.sec);
				q.setParameter(2, item.cur_mkt_cap);
				q.setParameter(3, item.oper_roe);
				q.setParameter(4, item.bs_tot_liab2);
				q.setParameter(5, item.pe_ration);
				q.setParameter(6, item.ebitda);
				q.setParameter(7, item.group);
				q.setParameter(8, item.sub);
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putPeersProc(String peer, List<String> items) {
		String sql = "{call dbo.put_blmpeers_proc ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(2, peer);
		for (String item : items) {
			try {
				q.setParameter(1, item);
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<String> getSecForBonds() {
		String sql = "select security_code from mo_job_UpdateBondQuotes_v";
		Query q = em.createNativeQuery(sql);
		showSql(sql, q);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putBondsData(List<BloombergResultItem> items) {
		String sql = "{call dbo.put_current_data ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (BloombergResultItem item : items) {
			try {
				q.setParameter(1, item.getSecurity());
				q.setParameter(2, item.getParams());
				q.setParameter(3, item.getValue());
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SecForJobRequest> getSecForHistData() {
		String sql = "select security_code as code, iso from dbo.anca_job_LoadHistoricalData_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		showSql(sql, q);
		List<SecForJobRequest> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			String code = Utils.toString(arr[0]);
			String iso = Utils.toString(arr[1]);
			res.add(new SecForJobRequest(code, iso));
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putHistParamsData(List<HistParamData> items) {
		String sql = "{call put_hist_data ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (HistParamData item : items) {
			try {
				q.setParameter(1, item.security);
				q.setParameter(2, item.params);
				q.setParameter(3, item.date);
				q.setParameter(4, item.value);
				q.setParameter(5, item.period);
				q.setParameter(6, item.curncy);
				q.setParameter(7, item.calendar);
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<String> getSecForQuotes() {
		String sql = "select security_code from dbo.mo_job_LoadQuotes_v";
		Query q = em.createNativeQuery(sql);
		showSql(sql, q);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putQuotes(List<BloombergResultItem> items) {
		String sql = "{call dbo.put_quotes ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (BloombergResultItem item : items) {
			try {
				q.setParameter(1, item.getSecurity());
				q.setParameter(2, item.getValue());
				q.setParameter(3, item.getDate());
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SecurityItem> getSecForUpdateFutures() {
		String sql = "exec dbo.put_updated_securities_info_list 2";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		showSql(sql, q);
		List<SecurityItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			SecurityItem item = new SecurityItem();
			item.setId(Utils.toLong(arr[0]));
			item.setCode(Utils.toString(arr[1]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putUpdatesFutures(List<UpdateFutureData> items) {
		String sql = "{call dbo.put_updated_securities_info ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (UpdateFutureData item : items) {
			try {
				q.setParameter(1, item.id_sec);
				q.setParameter(2, item.security_name);
				q.setParameter(3, item.name);
				q.setParameter(4, item.short_name);
				q.setParameter(5, item.type_id);
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public void putBondYeild(List<BloombergResultItem> items) {
		String sql = "{call dbo.put_bond_yield_proc ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (BloombergResultItem item : items) {
			try {
				q.setParameter(1, item.getSecurity());
				q.setParameter(2, item.getParams());
				q.setParameter(3, item.getDate());
				q.setParameter(4, item.getValue());
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				item.setValue(e.getMessage());
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putSecurityCashFlow(List<CashFlowResultItem> items) {
		String sql = "{call dbo.put_security_cash_flow_sp ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (CashFlowResultItem item : items) {
			try {
				q.setParameter(1, item.getId());
				q.setParameter(2, Utils.parseDate(item.getDate()));
				q.setParameter(3, item.getValue());
				q.setParameter(4, item.getValue2());
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putQuotesOne(List<BloombergResultItem> items) {
		String sql = "{call dbo.put_quotes ? ,?, ?}";
		Query q = em.createNativeQuery(sql);
		for (BloombergResultItem item : items) {
			try {
				q.setParameter(1, item.getSecurity());
				q.setParameter(2, item.getValue());
				q.setParameter(3, item.getDate());
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				item.setValue(e.getMessage());
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putSecurityCouponSchedule(List<BloombergResultItem> items) {
		String sql = "{call dbo.put_security_coupon_schedule_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (BloombergResultItem item : items) {
			try {
				q.setParameter(1, item.getId());
				q.setParameter(2, Utils.parseDate(item.getDate()));
				q.setParameter(3, item.getValue());
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putFaceAmount(List<BloombergResultItem> items) {
		String sql = "{call dbo.put_face_amount_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (BloombergResultItem item : items) {
			try {
				q.setParameter(1, item.getId());
				q.setParameter(2, Utils.parseDate(item.getDate()));
				q.setParameter(3, item.getValue());
				// TODO q.executeUpdate();
				showSql(sql, q);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * Ввод новой акции
	 * 
	 * @param values
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putSharesData(Map<String, String> values) {
		String sql = "{call dbo.put_equity_proc " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, values.get("ID_BB_GLOBAL"))
				.setParameter(2, values.get("ID_BB"))
				.setParameter(3, values.get("ID_BB_UNIQUE"))
				.setParameter(4, values.get("ID_BB_SEC_NUM_DES"))
				.setParameter(5, values.get("ID_BB_SEC_NUM_SRC"))
				.setParameter(6, values.get("ID_ISIN"))
				.setParameter(7, values.get("ID_CUSIP"))
				.setParameter(8, values.get("PARSEKYABLE_DES"))
				.setParameter(9, values.get("PARSEKYABLE_DES_SOURCE"))
				.setParameter(10, values.get("SECURITY_TYP"))
				.setParameter(11, values.get("MARKET_SECTOR_DES"))
				.setParameter(12, values.get("FEED_SOURCE"))
				.setParameter(13, values.get("TICKER"))
				.setParameter(14, values.get("SECURITY_NAME"))
				.setParameter(15, values.get("NAME"))
				.setParameter(16, values.get("SHORT_NAME"))
				.setParameter(17, values.get("EQY_PRIM_EXCH"))
				.setParameter(18, values.get("EXCH_CODE"))
				.setParameter(19, values.get("EQY_FUND_IND"))
				.setParameter(20, values.get("INDUSTRY_GROUP"))
				.setParameter(21, values.get("INDUSTRY_SUBGROUP"))
				.setParameter(22, values.get("ADR_SH_PER_ADR"))
				.setParameter(23, values.get("CRNCY"))
				.setParameter(24, values.get("EQY_FUND_CRNCY"))
				.setParameter(25, values.get("EQY_PRIM_SECURITY_CRNCY"))
				.setParameter(26, values.get("ADR_CRNCY"))
				.setParameter(27, values.get("BEST_CRNCY_ISO"))
				.setParameter(28, values.get("DVD_CRNCY"))
				.setParameter(29, values.get("EARN_EST_CRNCY"))
				.setParameter(30, values.get("EQY_FUND_TICKER"))
				.setParameter(31, values.get("EQY_FISCAL_YR_END"));
		// TODO q.executeUpdate();
		showSql(sql, q);
	}

	/**
	 * Ввод нового индекса
	 * 
	 * @param values
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putIndexData(Map<String, String> values) {
		String sql = "{call dbo.put_indices_proc " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, values.get("ID_BB_GLOBAL"))
				.setParameter(2, values.get("ID_BB_SEC_NUM_SRC"))
				.setParameter(3, values.get("ID_BB_SEC_NUM_DES"))
				.setParameter(4, values.get("PARSEKYABLE_DES"))
				.setParameter(5, values.get("PARSEKYABLE_DES_SOURCE"))
				.setParameter(6, values.get("TICKER"))
				.setParameter(7, values.get("SECURITY_NAME"))
				.setParameter(8, values.get("NAME"))
				.setParameter(9, values.get("SHORT_NAME"))
				.setParameter(10, values.get("SECURITY_TYP"))
				.setParameter(11, values.get("MARKET_SECTOR_DES"))
				.setParameter(12, values.get("FEED_SOURCE"))
				.setParameter(13, values.get("CRNCY"))
				.setParameter(14, values.get("EQY_FUND_CRNCY"));
		// TODO q.executeUpdate();
		showSql(sql, q);
	}

	/**
	 * Ввод новой облигации
	 * 
	 * @param values
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putBondsData(Map<String, String> values) {
		String sql = "{call dbo.put_bond_proc " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, values.get("ID_BB_UNIQUE"))
				.setParameter(2, values.get("ID_BB_COMPANY"))
				.setParameter(3, values.get("ID_CUSIP"))
				.setParameter(4, values.get("ID_ISIN"))
				.setParameter(5, values.get("PARSEKYABLE_DES"))
				.setParameter(6, values.get("SECURITY_NAME"))
				.setParameter(7, values.get("SECURITY_SHORT_DES"))
				.setParameter(8, values.get("SHORT_NAME"))
				.setParameter(9, values.get("TICKER"))
				.setParameter(10, values.get("NAME"))
				.setParameter(11, values.get("CRNCY"))
				.setParameter(12, Utils.parseDate(values.get("MATURITY")))
				.setParameter(13, values.get("MTY_TYP"))
				.setParameter(14, values.get("COLLAT_TYP"))
				.setParameter(15, values.get("SECURITY_DES"))
				.setParameter(16, values.get("INDUSTRY_SECTOR"))
				.setParameter(17, values.get("SECURITY_TYP"))
				.setParameter(18, values.get("COUNTRY_ISO"))
				.setParameter(19, values.get("CNTRY_OF_DOMICILE"))
				.setParameter(20, values.get("PX_METHOD"))
				.setParameter(21, values.get("PAYMENT_RANK"))
				.setParameter(22, values.get("SINKABLE"))
				.setParameter(23, values.get("DAY_CNT_DES"))
				.setParameter(24, Utils.parseDate(values.get("ANNOUNCE_DT")))
				.setParameter(25, Utils.parseDate(values.get("INT_ACC_DT")))
				.setParameter(26, Utils.parseDate(values.get("FIRST_SETTLE_DT")))
				.setParameter(27, Utils.parseDate(values.get("FIRST_CPN_DT")))
				.setParameter(28, values.get("CPN_CRNCY"))
				.setParameter(29, values.get("FIXED"))
				.setParameter(30, Utils.toDouble(values.get("CPN")))
				.setParameter(31, Utils.toDouble(values.get("PAR_AMT")));
		// TODO q.executeUpdate();
		showSql(sql, q);
	}

	/**
	 * Ввод нового фьючерса
	 * 
	 * @param values
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putFuturesData(Map<String, String> values) {
		String sql = "{call dbo.put_futures_proc " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, values.get("ID_BB_GLOBAL"))
				.setParameter(2, values.get("ID_BB"))
				.setParameter(3, values.get("ID_BB_UNIQUE"))
				.setParameter(4, values.get("ID_BB_SEC_NUM_DES"))
				.setParameter(5, values.get("ID_BB_SEC_NUM_SRC"))
				.setParameter(6, values.get("PARSEKYABLE_DES"))
				.setParameter(7, values.get("PARSEKYABLE_DES_SOURCE"))
				.setParameter(8, values.get("SECURITY_TYP"))
				.setParameter(9, values.get("MARKET_SECTOR_DES"))
				.setParameter(10, values.get("FEED_SOURCE"))
				.setParameter(11, values.get("FUTURES_CATEGORY"))
				.setParameter(12, values.get("FUT_TRADING_UNITS"))
				.setParameter(13, values.get("TICKER"))
				.setParameter(14, values.get("SECURITY_NAME"))
				.setParameter(15, values.get("NAME"))
				.setParameter(16, values.get("SHORT_NAME"))
				.setParameter(17, values.get("EXCH_CODE"))
				.setParameter(18, values.get("CRNCY"))
				.setParameter(19, values.get("QUOTED_CRNCY"))
				.setParameter(20, values.get("FUT_TICK_SIZE"))
				.setParameter(21, values.get("FUT_TICK_VAL"))
				.setParameter(22, values.get("FUT_CONT_SIZE"))
				.setParameter(23, values.get("FUT_VAL_PT"))
				.setParameter(24, values.get("FUT_FIRST_TRADE_DT"))
				.setParameter(25, values.get("LAST_TRADEABLE_DT"))
				.setParameter(26, values.get("FUT_GEN_MONTH"));
		// TODO q.executeUpdate();
		showSql(sql, q);
	}

	/**
	 * @param sql
	 * @param q
	 */
	private void showSql(String sql, Query q) {
		System.out.println(sql);
	}
}
