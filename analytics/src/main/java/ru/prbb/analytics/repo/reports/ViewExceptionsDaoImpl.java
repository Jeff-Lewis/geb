/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.ViewExceptionsItem;

/**
 * Отчёт по исключениям
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class ViewExceptionsDaoImpl implements ViewExceptionsDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<ViewExceptionsItem> execute() {
		String sql = "{call dbo.output_equities_exceptions}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<ViewExceptionsItem> res = new ArrayList<>();
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			ViewExceptionsItem item = new ViewExceptionsItem();
			item.setSec_code(Utils.toString(arr[0]));
			item.setRs_code(Utils.toString(arr[1]));
			item.setExc(Utils.toString(arr[2]));
			item.setR_par(Utils.toString(arr[3]));
			res.add(item);
		}
		return res;
	}

}
