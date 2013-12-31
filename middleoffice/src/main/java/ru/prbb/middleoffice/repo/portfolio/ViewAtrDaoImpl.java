/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.ViewAtrItem;

/**
 * @author RBr
 * 
 */
@Repository
public class ViewAtrDaoImpl implements ViewAtrDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<ViewAtrItem> execute(Date begin, Date end, Long[] securities) {
		List<ViewAtrItem> resAll = new ArrayList<>();
		String sql = "{call dbo.mo_WebGet_ATR_sp ?, ?, ?}";
		for (Long security : securities) {
			Query q = em.createNativeQuery(sql)
					.setParameter(1, security)
					.setParameter(2, begin)
					.setParameter(3, end);
			@SuppressWarnings("rawtypes")
			List list = q.getResultList();
			List<ViewAtrItem> res = new ArrayList<>(list.size());
			for (Object object : list) {
				Object[] arr = (Object[]) object;
				ViewAtrItem i = new ViewAtrItem();
				i.setId_sec(Utils.toLong(arr[0]));
				i.setSecurity_code(Utils.toString(arr[1]));
				i.setDate_time(Utils.toString(arr[2]));
				i.setATR(Utils.toDouble(arr[3]));
				i.setAtr_period(Utils.toString(arr[4]));
				i.setAlgorithm(Utils.toString(arr[5]));
				i.setDs_high_code(Utils.toString(arr[6]));
				i.setDs_low_code(Utils.toString(arr[7]));
				i.setDs_close_code(Utils.toString(arr[8]));
				i.setPeriod_type(Utils.toString(arr[9]));
				i.setCalendar(Utils.toString(arr[10]));
				i.setDate_insert(Utils.toDate(arr[11]));
				res.add(i);
			}
			resAll.addAll(res);
		}
		return resAll;
	}
}
