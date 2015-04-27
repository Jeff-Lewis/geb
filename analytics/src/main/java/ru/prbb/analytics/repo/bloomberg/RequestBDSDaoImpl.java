/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * BDS запрос
 * 
 * @author RBr
 * 
 */
@Service
public class RequestBDSDaoImpl extends BaseDaoImpl implements RequestBDSDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void execute(String[] securities, Map<String, Object> answer) {

		@SuppressWarnings("unchecked")
		Map<String, List<Map<String, String>>> ba =
				(Map<String, List<Map<String, String>>>) answer.get("BEST_ANALYST_RECS_BULK");
		if (ba != null) {
			for (String security : securities) {
				List<Map<String, String>> items = ba.get(security);
				if (null == items) {
					continue;
				}
				final List<AnalysData> data = new ArrayList<>(items.size());
				for (Map<String, String> item : items) {
					String firm = item.get("firm");
					String analyst = item.get("analyst");
					String recom = item.get("recom");
					String rating = item.get("rating");
					String action_code = item.get("action_code");
					String target_price = item.get("target_price");
					String period = item.get("period");
					String date = item.get("date");
					String barr = item.get("barr");
					String year_return = item.get("year_return");

					data.add(new AnalysData(security, firm, analyst, recom,
							rating, action_code, target_price, period, date,
							barr, year_return));
				}
				putAnalysData(data);
			}
		}

		@SuppressWarnings("unchecked")
		Map<String, List<Map<String, String>>> ehwe =
				(Map<String, List<Map<String, String>>>) answer.get("EARN_ANN_DT_TIME_HIST_WITH_EPS");
		if (ehwe != null) {
			for (String security : securities) {
				List<Map<String, String>> items = ehwe.get(security);
				if (null == items) {
					continue;
				}
				final List<EarnsEpsData> data = new ArrayList<>(items.size());
				for (Map<String, String> item : items) {
					String year_period = item.get("year_period");
					String announsment_date = item.get("announsment_date");
					String announsment_time = item.get("announsment_time");
					String earnings_EPS = item.get("earnings_EPS");
					String comparable_EPS = item.get("comparable_EPS");
					String estimate_EPS = item.get("estimate_EPS");

					data.add(new EarnsEpsData(security, year_period,
							announsment_date, announsment_time, earnings_EPS,
							comparable_EPS, estimate_EPS));
				}
				putEarnsEps(data);
			}
		}

		@SuppressWarnings("unchecked")
		Map<String, List<Map<String, String>>> eap =
				(Map<String, List<Map<String, String>>>) answer.get("ERN_ANN_DT_AND_PER");
		if (eap != null) {
			for (String security : securities) {
				List<Map<String, String>> items = eap.get(security);
				if (null == items) {
					continue;
				}
				final List<EarnsData> data = new ArrayList<>(items.size());
				for (Map<String, String> item : items) {
					String ead = item.get("ead");
					String eyap = item.get("eyap");
					data.add(new EarnsData(security, ead, eyap));
				}
				putEarnsData(data);
			}
		}

		@SuppressWarnings("unchecked")
		Map<String, List<String>> pt =
				(Map<String, List<String>>) answer.get("BLOOMBERG_PEERS");
		if (pt != null) {
			for (String security : securities) {
				List<String> items = pt.get(security);
				if (null == items) {
					continue;
				}
				final List<PeersData> data = new ArrayList<>(items.size());
				for (String peer : items) {
					final PeersData d = new PeersData(security, peer);
					data.add(d);
				}
				putPeersProc(data);
			}
		}

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> pd = (List<Map<String, Object>>) answer.get("PEERS");
		if (pd != null) {
			final List<PeersDescData> pdData = new ArrayList<>(pd.size());
			for (Map<String, Object> peer : pd) {
				String security = (String) peer.get("sec");
				Double cur_mkt_cap = (Double) peer.get("cur_mkt_cap");
				Double pe_ratio = (Double) peer.get("pe_ration");
				Double oper_roe = (Double) peer.get("oper_roe");
				Double bs_tot_liab2 = (Double) peer.get("bs_tot_liab2");
				Double ebitda = (Double) peer.get("ebitda");
				String ind_grp = (String) peer.get("group");
				String ind_sgrp = (String) peer.get("sub");

				pdData.add(new PeersDescData(security, cur_mkt_cap, oper_roe,
						bs_tot_liab2, pe_ratio, ebitda, ind_grp, ind_sgrp));
			}
			putPeersData(pdData);
		}
	}

	private class AnalysData {

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

		public AnalysData(String tempOrg,
				String firm,
				String analyst,
				String recom,
				String rating,
				String action_code,
				String target_price,
				String period,
				String date,
				String barr,
				String year_return) {
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

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(tempOrg);
			builder.append("[firm=");
			builder.append(firm);
			builder.append(", analyst=");
			builder.append(analyst);
			builder.append(", recom=");
			builder.append(recom);
			builder.append(", rating=");
			builder.append(rating);
			builder.append(", action_code=");
			builder.append(action_code);
			builder.append(", target_price=");
			builder.append(target_price);
			builder.append(", period=");
			builder.append(period);
			builder.append(", date=");
			builder.append(date);
			builder.append(", barr=");
			builder.append(barr);
			builder.append(", year_return=");
			builder.append(year_return);
			builder.append("]");
			return builder.toString();
		}
	}

	private void putAnalysData(List<AnalysData> data) {
		String sql = "{call dbo.put_analyst_data ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (AnalysData d : data) {
			q.setParameter(1, d.tempOrg);
			q.setParameter(2, d.firm);
			q.setParameter(3, d.analyst);
			q.setParameter(4, d.recom);
			q.setParameter(5, d.rating);
			q.setParameter(6, d.action_code);
			q.setParameter(7, d.target_price);
			q.setParameter(8, d.period);
			q.setParameter(9, d.date);
			q.setParameter(10, d.barr);
			q.setParameter(11, d.year_return);
			storeSql(sql, q);
			executeUpdate(q, sql);
		}
	}

	private class EarnsEpsData {

		public final String tempOrg;
		public final String period;
		public final String ann_date;
		public final String ann_time;
		public final String actual_eps;
		public final String comparable_eps;
		public final String estimate_eps;

		public EarnsEpsData(String tempOrg, String period, String ann_date, String ann_time, String actual_eps,
				String comparable_eps, String estimate_eps) {
			this.tempOrg = tempOrg;
			this.period = period;
			this.ann_date = ann_date;
			this.ann_time = ann_time;
			this.actual_eps = actual_eps;
			this.comparable_eps = comparable_eps;
			this.estimate_eps = estimate_eps;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(tempOrg);
			builder.append("[period=");
			builder.append(period);
			builder.append(", ann_date=");
			builder.append(ann_date);
			builder.append(", ann_time=");
			builder.append(ann_time);
			builder.append(", actual_eps=");
			builder.append(actual_eps);
			builder.append(", comparable_eps=");
			builder.append(comparable_eps);
			builder.append(", estimate_eps=");
			builder.append(estimate_eps);
			builder.append("]");
			return builder.toString();
		}

	}

	private void putEarnsEps(final List<EarnsEpsData> data) {
		String sql = "{call put_earn_announcement_eps ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (EarnsEpsData d : data) {
			q.setParameter(1, d.tempOrg);
			q.setParameter(2, d.period);
			q.setParameter(3, d.ann_date);
			q.setParameter(4, d.ann_time);
			q.setParameter(5, d.actual_eps);
			q.setParameter(6, d.comparable_eps);
			q.setParameter(7, d.estimate_eps);
			storeSql(sql, q);
			executeUpdate(q, sql);
		}
	}

	private class EarnsData {

		public final String tempOrg;
		public final String ead;
		public final String eyap;

		public EarnsData(String tempOrg, String ead, String eyap) {
			this.tempOrg = tempOrg;
			this.ead = ead;
			this.eyap = eyap;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(tempOrg);
			builder.append("[ead=");
			builder.append(ead);
			builder.append(", eyap=");
			builder.append(eyap);
			builder.append("]");
			return builder.toString();
		}
	}

	private void putEarnsData(final List<EarnsData> data) {
		String sql = "{call dbo.ern_announcement_proc ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (EarnsData d : data) {
			q.setParameter(1, d.tempOrg);
			q.setParameter(2, d.ead);
			q.setParameter(3, d.eyap);
			storeSql(sql, q);
			executeUpdate(q, sql);
		}
	}

	private class PeersData {

		public final String sec;
		public final String name;

		public PeersData(String sec, String name) {
			this.sec = sec;
			this.name = name;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(sec);
			builder.append("[name=");
			builder.append(name);
			builder.append("]");
			return builder.toString();
		}
	}

	private void putPeersProc(final List<PeersData> data) {
		String sql = "{call dbo.put_blmpeers_proc ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (PeersData d : data) {
			q.setParameter(1, d.sec);
			q.setParameter(2, d.name);
			storeSql(sql, q);
			executeUpdate(q, sql);
		}
	}

	private class PeersDescData {

		public final String sec;
		public final double cur_mkt_cap;
		public final double oper_roe;
		public final double bs_tot_liab2;
		public final double pe_ration;
		public final double ebitda;
		public final String group;
		public final String sub;

		public PeersDescData(String sec, double cur_mkt_cap, double oper_roe, double bs_tot_liab2, double pe_ration,
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

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(sec);
			builder.append("[cur_mkt_cap=");
			builder.append(cur_mkt_cap);
			builder.append(", oper_roe=");
			builder.append(oper_roe);
			builder.append(", bs_tot_liab2=");
			builder.append(bs_tot_liab2);
			builder.append(", pe_ration=");
			builder.append(pe_ration);
			builder.append(", ebitda=");
			builder.append(ebitda);
			builder.append(", group=");
			builder.append(group);
			builder.append(", sub=");
			builder.append(sub);
			builder.append("]");
			return builder.toString();
		}
	}

	private void putPeersData(final List<PeersDescData> data) {
		String sql = "{call put_blmpeers_descr_proc ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (PeersDescData d : data) {
			q.setParameter(1, d.sec);
			q.setParameter(2, d.cur_mkt_cap);
			q.setParameter(3, d.oper_roe);
			q.setParameter(4, d.bs_tot_liab2);
			q.setParameter(5, d.pe_ration);
			q.setParameter(6, d.ebitda);
			q.setParameter(7, d.group);
			q.setParameter(8, d.sub);
			storeSql(sql, q);
			executeUpdate(q, sql);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findParams(String query) {
		String sql = "select code from dbo.bulk_request_params_v";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(getResultList(q, sql));
	}

}
