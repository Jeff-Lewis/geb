/**
 * 
 */
package ru.prbb.jobber.repo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.jobber.domain.AtrLoadDataItem;
import ru.prbb.jobber.domain.OverrideData;
import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SendMessageItem;

/**
 * @author RBr
 */
@Repository
public class BloombergDaoImpl implements BloombergDao
{

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManager em;

	protected void showSql(String sql, Query q) {
		try {
			StringBuilder res = new StringBuilder(sql);
			if (!q.getParameters().isEmpty()) {
				List<Parameter<?>> ps = new ArrayList<>(q.getParameters());
				Collections.sort(ps, new Comparator<Parameter<?>>() {

					@Override
					public int compare(Parameter<?> o1, Parameter<?> o2) {
						return o1.getPosition().compareTo(o2.getPosition());
					}
				});
				res.append('(');
				for (Parameter<?> p : ps) {
					try {
						Object pv = q.getParameterValue(p);
						res.append(pv);
						res.append(',');
					} catch (IllegalStateException e) {
						res.append("NULL,");
					}
				}
				res.setCharAt(res.length() - 1, ')');
			}

			String msg = res.toString();
			log.info(msg);
		} catch (Exception e) {
			log.error("showSql", e);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SecForJobRequest> getLoadEstimatesPeersData() {
		String sql = "select security_code as code, iso from dbo.anca_job_LoadEstimatesPeersData_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
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
	public void putPeersData(List<Map<String, Object>> answer) {
		String sql = "{call dbo.put_blmpeers_descr_proc ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (Map<String, Object> data : answer) {
			try {
				q.setParameter(1, Utils.toString(data.get("sec")));
				q.setParameter(2, Utils.toDouble(data.get("cur_mkt_cap")));
				q.setParameter(3, Utils.toDouble(data.get("oper_roe")));
				q.setParameter(4, Utils.toDouble(data.get("bs_tot_liab2")));
				q.setParameter(5, Utils.toDouble(data.get("pe_ration")));
				q.setParameter(6, Utils.toDouble(data.get("ebitda")));
				q.setParameter(7, Utils.toString(data.get("group")));
				q.setParameter(8, Utils.toString(data.get("sub")));
				showSql(sql, q);
				q.executeUpdate();
			} catch (Exception e) {
				log.error("putPeersData " + data, e);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putAnalysData(String[] securities, Map<String, List<Map<String, String>>> answer) {
		String sql = "{call dbo.put_analyst_data ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (String security : securities) {
			List<Map<String, String>> datas = answer.get(security);
			if (null == datas) {
				log.warn("no datas for " + security);
				continue;
			}
			for (Map<String, String> data : datas) {
				try {
					q.setParameter(1, security);
					q.setParameter(2, Utils.parseString(data.get("firm")));
					q.setParameter(3, Utils.parseString(data.get("analyst")));
					q.setParameter(4, Utils.parseString(data.get("recom")));
					q.setParameter(5, Utils.parseString(data.get("rating")));
					q.setParameter(6, Utils.parseString(data.get("action_code")));
					q.setParameter(7, Utils.parseString(data.get("target_price")));
					q.setParameter(8, Utils.parseString(data.get("period")));
					q.setParameter(9, Utils.parseString(data.get("date")));
					q.setParameter(10, Utils.parseString(data.get("barr")));
					q.setParameter(11, Utils.parseString(data.get("year_return")));
					showSql(sql, q);
					q.executeUpdate();
				} catch (Exception e) {
					log.error("putAnalysData " + data, e);
				}
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putPeersProc(String[] securities, Map<String, List<String>> answer) {
		String sql = "{call dbo.put_blmpeers_proc ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (String security : securities) {
			List<String> datas = answer.get(security);
			if (null == datas) {
				log.warn("no datas for " + security);
				continue;
			}
			for (String data : datas) {
				data += " Equity";
				try {
					q.setParameter(1, security);
					q.setParameter(2, data);
					showSql(sql, q);
					q.executeUpdate();
				} catch (Exception e) {
					log.error("putPeersProc " + data, e);
				}
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
	public void putUpdatesFutures(List<SecurityItem> securities, Map<String, Map<String, String>> answer) {
		String sql = "{call dbo.put_updated_securities_info ?, ?, ?, ?, 2, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (SecurityItem security : securities) {
			Map<String, String> data = answer.get(security.getCode());
			if (null == data) {
				log.warn("no datas for " + security);
				continue;
			}
			try {
				q.setParameter(1, security.getId());
				q.setParameter(2, data.get("SECURITY_NAME"));
				q.setParameter(3, data.get("NAME"));
				q.setParameter(4, data.get("SHORT_NAME"));
				q.setParameter(5, Utils.parseDate(data.get("FUT_FIRST_TRADE_DT")));
				q.setParameter(6, Utils.parseDate(data.get("LAST_TRADEABLE_DT")));
				showSql(sql, q);
				q.executeUpdate();
			} catch (Exception e) {
				log.error("putUpdatesFutures " + data, e);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int execQuotesPortfolio(Date date) {
		String sql = "{call dbo.PlPortfolioOnDate ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, date);
		return q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<String> getSecForQuotes() {
		String sql = "select security_code from dbo.mo_job_LoadQuotes_v";
		Query q = em.createNativeQuery(sql);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putQuotes(String date, String[] securities, Map<String, Map<String, Map<String, String>>> answer) {
		String sql = "{call dbo.put_quotes ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (String security : securities) {
			Map<String, Map<String, String>> datas = answer.get(security);
			if (null == datas) {
				log.warn("no datas for " + security);
				continue;
			}
			for (Entry<String, Map<String, String>> entry : datas.entrySet()) {
				date = entry.getKey();
				Map<String, String> data = entry.getValue();
				try {
					q.setParameter(1, security);
					q.setParameter(2, Utils.toDouble(data.get("PX_LAST")));
					q.setParameter(3, date);
					showSql(sql, q);
					q.executeUpdate();
				} catch (Exception e) {
					log.error("putQuotes " + data, e);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<String> getSecForAtr() {
		String sql = "select security_code from dbo.mo_job_LoadATR_v";
		Query q = em.createNativeQuery(sql);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putAtrData(String[] securities, List<AtrLoadDataItem> answer) {
		String sql = "{call dbo.mo_WebSet_putATR_sp ?, ?, ?, 7, 'Exponential', 'PX_HIGH', 'PX_LOW', 'PX_LAST', 'DAILY', 'CALENDAR'}";
		Query q = em.createNativeQuery(sql);
		for (AtrLoadDataItem item : answer) {
			try {
				q.setParameter(1, Utils.toString(item.getSecurity()));
				q.setParameter(2, Utils.parseDate(item.getDate()));
				q.setParameter(3, Utils.toDouble(item.getValue()));
				showSql(sql, q);
				q.executeUpdate();
			} catch (Exception e) {
				log.error("putAtrData " + item, e);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putOverrideData(List<SecForJobRequest> securities, Map<String, Map<String, String>> answer) {
		String sql = "{call dbo.put_override_data ?, 'BEST_EPS_GAAP', ?, ?, 'BST'}";
		Query q = em.createNativeQuery(sql);

		for (SecForJobRequest security : securities) {
			final Map<String, String> values = answer.get(security.code);

			if (null != values) {
				for (String period : values.keySet()) {
					final String value = values.get(period);
					OverrideData item = new OverrideData(security.code, value, period);
					try {
						q.setParameter(1, item.security);
						q.setParameter(2, item.value);
						q.setParameter(3, item.period);
						showSql(sql, q);
						q.executeUpdate();
					} catch (Exception e) {
						log.error("putOverrideData " + item, e);
					}
				}
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
	public void putHistParamsData(String date, String[] currencies, String[] cursec,
			Map<String, Map<String, Map<String, String>>> answer) {
		String sql = "{call put_hist_data ?, ?, ?, ?, 'DAILY', ?, 'CALENDAR'}";
		Query q = em.createNativeQuery(sql);

		final String[] fields = {
				"EQY_WEIGHTED_AVG_PX",
				"PX_HIGH", "PX_LAST", "PX_LOW", "PX_VOLUME",
				"TOT_BUY_REC", "TOT_HOLD_REC", "TOT_SELL_REC"
		};

		for (String currency : currencies) {
			for (String cs : cursec) {
				if (cs.startsWith(currency)) {
					final String security = cs.substring(currency.length());

					final Map<String, String> values = answer.get(cs).get(date);
					if (null == values) {
						log.warn("no datas for " + security);
						continue;
					}

					for (String field : fields) {
						if (values.containsKey(field)) {
							final String value = values.get(field);
							try {
								q.setParameter(1, security);
								q.setParameter(2, field);
								q.setParameter(3, date);
								q.setParameter(4, value);
								q.setParameter(5, currency);
								showSql(sql, q);
								q.executeUpdate();
							} catch (Exception e) {
								log.error("putHistParamsData " + values, e);
							}
						}
					}

				}
			}
		}
	}

	/**
	 * Загрузка курсов валют
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<String> getSecForCurrency() {
		String sql = "select blm_query_code from dbo.mo_job_LoadCurrency_rate_cbr_v";
		Query q = em.createNativeQuery(sql);
		return q.getResultList();
	}

	/**
	 * Загрузка курсов валют
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putCurrencyData(String[] securities, Map<String, Map<String, String>> answer) {
		String sql = "{call dbo.put_currency_rate_cbr_sp null, null, ?, null, ?, ?, null}";
		Query q = em.createNativeQuery(sql);
		for (String security : securities) {
			Map<String, String> data = answer.get(security);
			if (null == data) {
				log.warn("no datas for " + security);
				continue;
			}
			try {
				q.setParameter(1, security);
				q.setParameter(2, Utils.parseDouble(data.get("QUOTE_FACTOR")).intValue());
				q.setParameter(3, Utils.parseDouble(data.get("PX_LAST")));
				showSql(sql, q);
				q.executeUpdate();
			} catch (Exception e) {
				log.error("putCurrencyData " + data, e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<String> getSecForBonds() {
		String sql = "select security_code from mo_job_UpdateBondQuotes_v";
		Query q = em.createNativeQuery(sql);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void putBondsData(String[] securities, Map<String, Map<String, String>> answer) {
		String[] fields = { "CHG_PCT_1D", "YLD_YTM_MID" };
		String sql = "{call dbo.put_current_data ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (String security : securities) {
			Map<String, String> data = answer.get(security);
			if (null == data) {
				log.warn("no datas for " + security);
				continue;
			}
			for (String field : fields) {
				try {
					q.setParameter(1, security);
					q.setParameter(2, field);
					q.setParameter(3, data.get(field));
					showSql(sql, q);
					q.executeUpdate();
				} catch (Exception e) {
					log.error("putBondsData " + data, e);
				}
			}
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SendMessageItem> exec(String sql) {
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<SendMessageItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			SendMessageItem item = new SendMessageItem();
			switch (arr.length) {
			case 4:
				item.setType(Utils.toNumber(arr[0]));
				item.setAddrs(Utils.toString(arr[1]));
				item.setSubj(Utils.toString(arr[2]));
				item.setText(Utils.toString(arr[3]));
				break;

			case 3:
				item.setType(1);
				item.setAddrs(Utils.toString(arr[0]));
				item.setSubj(Utils.toString(arr[1]));
				item.setText(Utils.toString(arr[2]));
				break;

			case 2:
				item.setType(0);
				item.setAddrs(Utils.toString(arr[0]));
				item.setSubj(null);
				item.setText(Utils.toString(arr[1]));
				break;

			default:
				throw new IllegalStateException("Unknow SendMessageItem for " + sql);
			}
			res.add(item);
		}
		return res;
	}
}
