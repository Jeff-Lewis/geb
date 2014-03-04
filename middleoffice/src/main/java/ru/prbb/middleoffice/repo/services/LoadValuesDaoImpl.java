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
import ru.prbb.middleoffice.domain.SecurityValuesItem;

/**
 * Загрузка номинала
 * 
 * @author RBr
 */
@Service
public class LoadValuesDaoImpl implements LoadValuesDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List<Map<String, Object>> execute(List<Map<String, Object>> answer) {
		List<SecDateValueData> data = new ArrayList<>(answer.size());
		for (Map<String, Object> item : answer) {
			long id = Long.parseLong(item.get("id_sec").toString());
			String date = (String) item.get("date");
			double value = Double.parseDouble(item.get("value").toString());

			data.add(new SecDateValueData(id, Utils.parseDate(date), value));
		}
		putFaceAmount(data);

		return answer;
	}

	private class SecDateValueData {

		public final long security_id;
		public final Date date_time;
		public final double value;

		public SecDateValueData(long security_id, Date date_time, double value) {
			this.security_id = security_id;
			this.date_time = date_time;
			this.value = value;
		}
	}

	private void putFaceAmount(final List<SecDateValueData> items) {
		String sql = "{call dbo.put_face_amount_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (SecDateValueData item : items) {
			q.setParameter(1, item.security_id);
			q.setParameter(2, item.date_time);
			q.setParameter(3, item.value);
			q.executeUpdate();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityValuesItem> findAllSecurities() {
		String sql = "select * from dbo.mo_WebGet_bonds_sinkable_v";
		Query q = em.createNativeQuery(sql, SecurityValuesItem.class);
		return q.getResultList();
	}

}
