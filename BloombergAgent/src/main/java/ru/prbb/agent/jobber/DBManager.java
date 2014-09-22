package ru.prbb.agent.jobber;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ru.prbb.bloomberg.model.OverrideData;
import ru.prbb.bloomberg.model.SecForJobRequest;

@Service
public class DBManager {
	private static final Log log = LogFactory.getLog(DBManager.class);

//	@Autowired
	private JdbcTemplate jdbcTemplate;

	private interface Callback<T> {
		T execute();
	}

	private <T> T retryCall(Callback<T> callback) {
		for (int i = 0; i < 100; i++) {
			try {
				return callback.execute();
			} catch (DataAccessException e) {
				log.error("Unsuccessful attempt " + i, e);
				// TODO: handle exception
			}
			try {
				Thread.sleep(1000 * 60);
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
		throw new RuntimeException("DataAccessException");
	}

	public List<SecForJobRequest> getLoadEstimatesPeersData() {
		return retryCall(new Callback<List<SecForJobRequest>>() {
			@Override
			public List<SecForJobRequest> execute() {
				return jdbcTemplate.query("select security_code, iso from dbo.anca_job_LoadEstimatesPeersData_v",
						new RowMapper<SecForJobRequest>() {

							@Override
							public SecForJobRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
								final String security_code = rs.getString(1);
								final String iso = rs.getString(2);
								return new SecForJobRequest(security_code, iso);
							}

						});
			}
		});
	}

	public List<SecForJobRequest> getSecForHistData() {
		return retryCall(new Callback<List<SecForJobRequest>>() {
			@Override
			public List<SecForJobRequest> execute() {
				return jdbcTemplate.query("select security_code, iso from dbo.anca_job_LoadHistoricalData_v",
						new RowMapper<SecForJobRequest>() {

							@Override
							public SecForJobRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
								final String security_code = rs.getString(1);
								final String iso = rs.getString(2);
								return new SecForJobRequest(security_code, iso);
							}

						});
			}
		});
	}

	public int putOverrideData(final OverrideData d) {
		return retryCall(new Callback<Integer>() {
			@Override
			public Integer execute() {
				return jdbcTemplate.update("{call dbo.put_override_data ?, ?, ?, ?, ?}",
						d.security, d.param, d.value, d.period, d.blm_data_src_over);
			}
		});
	}

	public List<String> getSecForQuotes() {
		return retryCall(new Callback<List<String>>() {
			@Override
			public List<String> execute() {
				return jdbcTemplate.queryForList("select security_code from dbo.mo_job_LoadQuotes_v", String.class);
			}
		});
	}

	public static class Security {
		public final Long id;
		public final String code;

		public Security(long id, String code) {
			this.id = id;
			this.code = code;
		}
	}

	public List<Security> getSecForUpdateFutures() {
		return retryCall(new Callback<List<Security>>() {
			@Override
			public List<Security> execute() {
				return jdbcTemplate.query("exec dbo.put_updated_securities_info_list 2",
						new RowMapper<Security>() {
							public Security mapRow(ResultSet rs, int rowNum) throws SQLException {
								final long id = rs.getLong("id_sec");
								final String code = rs.getString("security_code");
								return new Security(id, code);
							}
						});
			}
		});
	}

	public static class HistParamData {
		public final String security;
		public final String params;
		public final String date;
		public final String value;
		public final String period;
		public final String curncy;
		public final String calendar;

		public HistParamData(String security, String params, String date, String value, String period, String curncy,
				String calendar) {
			this.security = security;
			this.params = params;
			this.date = date;
			this.value = value;
			this.period = period;
			this.curncy = curncy;
			this.calendar = calendar;
		}
	}

	public int[] putHistParamsData(final List<HistParamData> data) {
		return retryCall(new Callback<int[]>() {
			@Override
			public int[] execute() {
				return jdbcTemplate.batchUpdate("{call put_hist_data ?, ?, ?, ?, ?, ?, ?}",
						new BatchPreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps, int i) throws SQLException {
								final HistParamData d = data.get(i);
								ps.setString(1, d.security);
								ps.setString(2, d.params);
								ps.setString(3, d.date);
								ps.setString(4, d.value);
								ps.setString(5, d.period);
								ps.setString(6, d.curncy);
								ps.setString(7, d.calendar);
							}

							@Override
							public int getBatchSize() {
								return data.size();
							}
						});
			}
		});
	}

	public static class QuoteData {
		public final String security;
		public final double value;
		public final String date;

		public QuoteData(String security, double value, String date) {
			this.security = security;
			this.value = value;
			this.date = date;
		}
	}

	public int[] putQuotes(final List<QuoteData> data) {
		return retryCall(new Callback<int[]>() {
			@Override
			public int[] execute() {
				return jdbcTemplate.batchUpdate("{call put_quotes ?, ?, ?}",
						new BatchPreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps, int i) throws SQLException {
								final QuoteData d = data.get(i);
								ps.setString(1, d.security);
								ps.setDouble(2, d.value);
								ps.setString(3, d.date);
							}

							@Override
							public int getBatchSize() {
								return data.size();
							}
						});
			}
		});
	}

	/**
	 * Загрузка котировок
	 * 
	 * @param records
	 */
	public void putQuotesOne(final List<QuoteData> records) {
		jdbcTemplate.batchUpdate("{call put_quotes ? ,?, ?}",
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						final QuoteData r = records.get(i);
						ps.setString(1, r.security);
						ps.setDouble(2, r.value);
						ps.setString(3, r.date);
					}

					@Override
					public int getBatchSize() {
						return records.size();
					}
				});
	}

	public static class AnalystData {
		public final String tempOrg;
		public final String firm;
		public final String analyst;
		public final String recom;
		public final String rating;
		public final String action_code;
		public final String target_price;
		public final String period;
		public final String date;
		public final String barr;
		public final String year_return;

		public AnalystData(String tempOrg, String firm, String analyst, String recom, String rating,
				String action_code, String target_price, String period, String date, String barr, String year_return) {
			this.tempOrg = tempOrg;
			this.firm = firm;
			this.analyst = analyst;
			this.recom = recom;
			this.rating = rating;
			this.action_code = action_code;
			this.target_price = target_price;
			this.period = period;
			this.date = date;
			this.barr = barr;
			this.year_return = year_return;
		}
	}

	public int putAnalysData(final AnalystData d) {
		return retryCall(new Callback<Integer>() {
			@Override
			public Integer execute() {
				return jdbcTemplate.update("{call dbo.put_analyst_data ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
						d.tempOrg, d.firm, d.analyst, d.recom, d.rating, d.action_code, d.target_price, d.period,
						d.date, d.barr, d.year_return);
			}
		});
	}

	public static class UpdateFutureData {
		public final long id_sec;
		public final String security_name;
		public final String name;
		public final String short_name;
		public final long type_id = 2;
		public final Date first_tradeable_date;
		public final Date last_tradeable_date;
	 
		public UpdateFutureData(Long securityCode, String secName, String name, String shortName,
				Date first_tradeable_date, Date last_tradeable_date) {
			this.id_sec = securityCode;
			this.security_name = secName;
			this.name = name;
			this.short_name = shortName;
			this.first_tradeable_date = first_tradeable_date;
			this.last_tradeable_date = last_tradeable_date;
		}
	}

	public int[] putUpdatesFutures(final List<UpdateFutureData> data) {
		return retryCall(new Callback<int[]>() {
			@Override
			public int[] execute() {
				return jdbcTemplate.batchUpdate("{call dbo.put_updated_securities_info ?, ?, ?, ?, ?, ?, ?}",
						new BatchPreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps, int i) throws SQLException {
								final UpdateFutureData d = data.get(i);
								ps.setLong(1, d.id_sec);
								ps.setString(2, d.security_name);
								ps.setString(3, d.name);
								ps.setString(4, d.short_name);
								ps.setLong(5, d.type_id);
								ps.setDate(6, d.first_tradeable_date);
								ps.setDate(7, d.last_tradeable_date);
							}

							@Override
							public int getBatchSize() {
								return data.size();
							}
						});
			}
		});
	}

	public static class PeerData {
		public final String sec;
		public final double cur_mkt_cap;
		public final double oper_roe;
		public final double bs_tot_liab2;
		public final double pe_ration;
		public final double ebitda;
		public final String group;
		public final String sub;

		public PeerData(String sec, double cur_mkt_cap, double oper_roe, double bs_tot_liab2, double pe_ration,
				double ebitda, String group, String sub) {
			this.sec = sec;
			this.cur_mkt_cap = cur_mkt_cap;
			this.oper_roe = oper_roe;
			this.bs_tot_liab2 = bs_tot_liab2;
			this.pe_ration = pe_ration;
			this.ebitda = ebitda;
			this.group = group;
			this.sub = sub;
		}
	}

	public int putPeersData(final PeerData d) {
		return retryCall(new Callback<Integer>() {
			@Override
			public Integer execute() {
				return jdbcTemplate.update("{call put_blmpeers_descr_proc ?, ?, ?, ?, ?, ?, ?, ?}",
						d.sec, d.cur_mkt_cap, d.oper_roe, d.bs_tot_liab2, d.pe_ration, d.ebitda, d.group, d.sub);
			}
		});
	}

	public int putPeersProc(final String sec, final String peer) {
		return retryCall(new Callback<Integer>() {
			@Override
			public Integer execute() {
				return jdbcTemplate.update("{call dbo.put_blmpeers_proc ?, ?}", sec, peer);
			}
		});
	}

	public List<String> getSecForBonds() {
		return retryCall(new Callback<List<String>>() {
			@Override
			public List<String> execute() {
				return jdbcTemplate.queryForList("select security_code from dbo.mo_job_UpdateBondQuotes_v",
						String.class);
			}
		});
	}

	public static class CurrentData {
		public final String security;
		public final String param;
		public final String value;

		public CurrentData(String security, String param, String value) {
			this.security = security;
			this.param = param;
			this.value = value;
		}
	}

	public int[] putCurrentData(final List<CurrentData> datas) {
		return retryCall(new Callback<int[]>() {
			@Override
			public int[] execute() {
				return jdbcTemplate.batchUpdate("{call dbo.put_current_data ?, ?, ?}",
						new BatchPreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps, int i) throws SQLException {
								final CurrentData d = datas.get(i);
								ps.setString(1, d.security);
								ps.setString(2, d.param);
								ps.setString(3, d.value);
							}

							@Override
							public int getBatchSize() {
								return datas.size();
							}
						});
			}
		});
	}

	/**
	 * Список подписок и их статус
	 * 
	 * @return
	 */
	public List<Map<String, Object>> subsGetList() {
		return jdbcTemplate.queryForList("{call dbo.output_subscriptions_prc}");
	}

	/**
	 * Список компаний в подписке
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> subsGetSecs(Long id) {
		return jdbcTemplate.queryForList("{call dbo.secs_in_subscription_prc ?}", id);
	}

	public void subsUpdate(String security_code, String last_price, String last_chng) {
		jdbcTemplate.update("{call dbo.upd_sect_subs_proc ?, ?, ?}", security_code, last_price, last_chng);
	}

	/**
	 * Загрузка дат погашений
	 * 
	 * @param security_id
	 * @param maturity_date
	 * @param coupon_cash_flow
	 * @param principal_cash_flow
	 */
	public void putSecurityCashFlow(Long security_id, Date maturity_date,
			Double coupon_cash_flow, Double principal_cash_flow) {
		jdbcTemplate.update("{call dbo.put_security_cash_flow_sp ?, ?, ?, ?}",
				security_id, maturity_date, coupon_cash_flow, principal_cash_flow);
	}

	/**
	 * Загрузка номинала
	 * 
	 * @param security_id
	 * @param date_time
	 * @param value
	 */
	public void putFaceAmount(Long security_id, Date date_time, Double value) {
		jdbcTemplate.update("{call dbo.put_face_amount_sp ?, ?, ?}", security_id, date_time, value);
	}

	/**
	 * Загрузка ставки по купонам
	 * 
	 * @param security_id
	 * @param date_time
	 * @param value
	 */
	public void putSecurityCouponSchedule(Long security_id, Date date_time, Double value) {
		jdbcTemplate.update("{call dbo.put_security_coupon_schedule_sp ?, ?, ?}", security_id, date_time, value);
	}

	public static class BondYeildRecord {
		public final String security;
		public final String params;
		public final String date;
		public final String char_value;

		public BondYeildRecord(String security, String params, String date, String char_value) {
			this.security = security;
			this.params = params;
			this.date = date;
			this.char_value = char_value;
		}
	}

	/**
	 * Загрузка доходности облигаций
	 * 
	 * @param records
	 */
	public void putBondYeild(final List<BondYeildRecord> records) {
		jdbcTemplate.batchUpdate("{call put_bond_yield_proc ? ,?, ?}",
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						final BondYeildRecord r = records.get(i);
						ps.setString(1, r.security);
						ps.setString(2, r.params);
						ps.setString(3, r.date);
						ps.setString(4, r.char_value);
					}

					@Override
					public int getBatchSize() {
						return records.size();
					}
				});
	}

	public void putSecurityData(String id_bb_global, String id_bb, String id_bb_unique, String id_bb_sec_num_des,
			String id_bb_sec_num_src, String id_isin, String id_cusip, String parsekeyable_des,
			String parsekyable_des_source, String security_typ, String market_sector_des, String feed_source,
			String ticker, String security_name, String name, String short_name, String eqy_prim_exch,
			String exch_code, String eqy_fund_ind, String indstry_group, String indstry_subgroup,
			String adr_sh_per_adr, String crncy, String eqy_fund_crncy, String eqy_prim_security_crncy,
			String adr_crncy, String best_crncy_iso, String dvd_crncy, String earn_est_crncy,
			String equity_fund_ticker, String eqy_fiscal_yr_end, String period) {
		jdbcTemplate.update("{call dbo.put_equity_proc " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
				id_bb_global, id_bb, id_bb_unique, id_bb_sec_num_des, id_bb_sec_num_src, id_isin, id_cusip,
				parsekeyable_des, parsekyable_des_source, security_typ, market_sector_des, feed_source, ticker,
				security_name, name, short_name, eqy_prim_exch, exch_code, eqy_fund_ind, indstry_group,
				indstry_subgroup, adr_sh_per_adr, crncy, eqy_fund_crncy, eqy_prim_security_crncy, adr_crncy,
				best_crncy_iso, dvd_crncy, earn_est_crncy, equity_fund_ticker, eqy_fiscal_yr_end, period);
	}

	public void putIndexData(String id_bb_global, String id_bb_sec_num_src, String id_bb_sec_num_des,
			String parsekeyable_des, String parsekyable_des_source, String ticker, String security_name, String name,
			String short_name, String security_typ, String market_sector_des, String feed_source, String crncy,
			String eqy_fund_crncy) {
		jdbcTemplate.update("{call dbo.put_indices_proc " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
				id_bb_global, id_bb_sec_num_src,
				id_bb_sec_num_des, parsekeyable_des, parsekyable_des_source, ticker, security_name, name, short_name,
				security_typ, market_sector_des, feed_source, crncy, eqy_fund_crncy);
	}

	public void putBondsData(String id_bb_unique, String id_bb_company, String id_cusip, String id_isin,
			String parsekeyable_des, String security_name, String security_short_des, String short_name, String ticker,
			String name, String crncy, Date maturity, String mty_typ, String collateral_type,
			String security_description, String industry_sector, String security_typ, String country_iso,
			String cntry_of_domicile, String pricing_method, String payment_rank, String sinkable,
			String des_day_cnt, Date announcement_date, Date interest_accrual_date,
			Date fst_settle_date, Date fst_coupon_date, String cpn_crncy, String fixed, Double cpn, Double par_amt) {
		jdbcTemplate.update("{call dbo.put_bond_proc " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
				id_bb_unique, id_bb_company, id_cusip, id_isin, parsekeyable_des, security_name, security_short_des,
				short_name, ticker, name, crncy, maturity, mty_typ, collateral_type, security_description,
				industry_sector, security_typ, country_iso, cntry_of_domicile, pricing_method, payment_rank, sinkable,
				des_day_cnt, announcement_date, interest_accrual_date, fst_settle_date, fst_coupon_date,
				cpn_crncy, fixed, cpn, par_amt);
	}

	public void putNewFuturesData(String id_bb_global, String id_bb, String id_bb_unique, String id_bb_sec_num_des,
			String id_bb_sec_num_src, String parsekeyable_des, String parsekyable_des_source, String security_typ,
			String market_sector_des, String feed_source, String futures_category, String fut_trading_units,
			String ticker, String security_name, String name, String short_name, String exch_code, String crncy,
			String quoted_crncy, String tick_size, String tick_value, String contract_size, String value_1pt,
			String first_tradeable_date, String last_tradeable_date, String fut_gen_month) {
		jdbcTemplate.update("{call dbo.put_futures_proc " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
				id_bb_global, id_bb, id_bb_unique, id_bb_sec_num_des, id_bb_sec_num_src, parsekeyable_des,
				parsekyable_des_source, security_typ, market_sector_des, feed_source, futures_category,
				fut_trading_units, ticker, security_name, name, short_name, exch_code, crncy, quoted_crncy, tick_size,
				tick_value, contract_size, value_1pt, first_tradeable_date, last_tradeable_date, fut_gen_month);
	}

	public List<String> getSecForAtr() {
		return retryCall(new Callback<List<String>>() {
			@Override
			public List<String> execute() {
				return jdbcTemplate.queryForList("select security_code from dbo.mo_job_LoadATR_v", String.class);
			}
		});
	}

	public void putAtrData(String security, Date date_time, Double atr_value, Integer atr_period, String algorithm,
			String ds_high_code, String ds_low_code, String ds_close_code, String period, String calendar) {
		jdbcTemplate.update("{call dbo.mo_WebSet_putATR_sp ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
				security, date_time, atr_value, atr_period, algorithm, ds_high_code, ds_low_code,
				ds_close_code, period, calendar);
	}
}
