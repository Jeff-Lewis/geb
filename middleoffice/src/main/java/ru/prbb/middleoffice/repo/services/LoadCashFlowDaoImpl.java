/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.sql.Date;
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
import ru.prbb.middleoffice.domain.SecurityCashFlowItem;

/**
 * Загрузка дат погашений
 * 
 * @author RBr
 */
@Service
public class LoadCashFlowDaoImpl implements LoadCashFlowDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<Map<String, Object>> execute(List<Map<String, Object>> answer) {
		List<SecurityCashFlowData> data = new ArrayList<>(answer.size());
		for (Map<String, Object> item : answer) {
			long id = Long.parseLong(item.get("id_sec").toString());
			String date = (String) item.get("date");
			double value = Double.parseDouble(item.get("value").toString());
			double value2 = Double.parseDouble(item.get("value2").toString());

			data.add(new SecurityCashFlowData(id, Utils.parseDate(date), value, value2));
		}
		putSecurityCashFlow(data);

		return answer;
	}

	private class SecurityCashFlowData {

		public final long security_id;
		public final Date maturity_date;
		public final double coupon_cash_flow;
		public final double principal_cash_flow;

		public SecurityCashFlowData(long security_id, Date maturity_date, double coupon_cash_flow, double principal_cash_flow) {
			this.security_id = security_id;
			this.maturity_date = maturity_date;
			this.coupon_cash_flow = coupon_cash_flow;
			this.principal_cash_flow = principal_cash_flow;
		}

	}

	private void putSecurityCashFlow(final List<SecurityCashFlowData> items) {
		String sql = "{call dbo.put_security_cash_flow_sp ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (SecurityCashFlowData item : items) {
			q.setParameter(1, item.security_id);
			q.setParameter(2, item.maturity_date);
			q.setParameter(3, item.coupon_cash_flow);
			q.setParameter(4, item.principal_cash_flow);
			q.executeUpdate();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityCashFlowItem> findAllSecurities() {
		String sql = "select * from dbo.mo_WebGet_bonds_v";
		Query q = em.createNativeQuery(sql, SecurityCashFlowItem.class);
		return q.getResultList();
	}

}
