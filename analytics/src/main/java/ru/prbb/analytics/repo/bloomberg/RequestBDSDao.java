/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.UserHistory.AccessAction;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * BDS запрос
 * 
 * @author RBr
 */
@Service
public class RequestBDSDao
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManagerService ems;

	public void execute(ArmUserInfo user, String[] securities, Map<String, Object> answer) {

		@SuppressWarnings("unchecked")
		Map<String, List<Map<String, String>>> ba =
				(Map<String, List<Map<String, String>>>) answer.get("BEST_ANALYST_RECS_BULK");
		if (ba != null) {
			for (String security : securities) {
				List<Map<String, String>> items = ba.get(security);
				if (null == items) {
					continue;
				}
				for (Map<String, String> item : items) {
					try {
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

						ems.executeUpdate(AccessAction.OTHER, user,
								"{call dbo.put_analyst_data ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
								security, firm, analyst, recom, rating, action_code, target_price, period, date, barr, year_return);
					} catch (Exception e) {
						log.error("put_analyst_data " + security, e);
					}
				}
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
				for (Map<String, String> item : items) {
					try {
						String year_period = item.get("year_period");
						String announsment_date = item.get("announsment_date");
						String announsment_time = item.get("announsment_time");
						String earnings_EPS = item.get("earnings_EPS");
						String comparable_EPS = item.get("comparable_EPS");
						String estimate_EPS = item.get("estimate_EPS");

						ems.executeUpdate(AccessAction.OTHER, user,
								"{call dbo.put_earn_announcement_eps ?, ?, ?, ?, ?, ?, ?}",
								security, year_period, announsment_date, announsment_time, earnings_EPS, comparable_EPS, estimate_EPS);
					} catch (Exception e) {
						log.error("put_earn_announcement_eps " + security, e);
					}
				}
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
				for (Map<String, String> item : items) {
					try {
						String ead = item.get("ead");
						String eyap = item.get("eyap");
						ems.executeUpdate(AccessAction.OTHER, user,
								"{call dbo.ern_announcement_proc ?, ?, ?}",
								security, ead, eyap);
					} catch (Exception e) {
						log.error("ern_announcement_proc " + security, e);
					}
				}
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
				for (String peer : items) {
					try {
						ems.executeUpdate(AccessAction.OTHER, user,
								"{call dbo.put_blmpeers_proc ?, ?}",
								security, peer);
					} catch (Exception e) {
						log.error("put_blmpeers_proc " + security, e);
					}
				}
			}
		}

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> pd = (List<Map<String, Object>>) answer.get("PEERS");
		if (pd != null) {
			for (Map<String, Object> peer : pd) {
				String security = (String) peer.get("sec");
				try {
					Number cur_mkt_cap = (Number) peer.get("cur_mkt_cap");
					Number pe_ration = (Number) peer.get("pe_ration");
					Number oper_roe = (Number) peer.get("oper_roe");
					Number bs_tot_liab2 = (Number) peer.get("bs_tot_liab2");
					Number ebitda = (Number) peer.get("ebitda");
					String group = (String) peer.get("group");
					String sub = (String) peer.get("sub");

					ems.executeUpdate(AccessAction.OTHER, user,
							"{call dbo.put_blmpeers_descr_proc ?, ?, ?, ?, ?, ?, ?, ?}",
							security, cur_mkt_cap, oper_roe, bs_tot_liab2, pe_ration, ebitda, group, sub);
				} catch (Exception e) {
					log.error("put_blmpeers_descr_proc " + security, e);
				}
			}
		}
	}

	public List<SimpleItem> findParams(ArmUserInfo user, String query) {
		String sql = "select code from dbo.bulk_request_params_v";
		String where = "";
		return ems.getComboListName(sql, where, query);
	}

}
