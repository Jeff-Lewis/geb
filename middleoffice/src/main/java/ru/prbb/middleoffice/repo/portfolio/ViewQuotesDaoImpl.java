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
import ru.prbb.middleoffice.domain.ViewQuotesItem;

/**
 * Котировки
 * 
 * @author RBr
 * 
 */
@Repository
public class ViewQuotesDaoImpl implements ViewQuotesDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<ViewQuotesItem> execute(Date begin, Date end, Long[] securities) {
		List<ViewQuotesItem> resAll = new ArrayList<>();
		String sql = "{call dbo.mo_WebGet_SelectQuotes_sp ?, ?, ?}";
		for (Long security : securities) {
			Query q = em.createNativeQuery(sql)
					.setParameter(1, security)
					.setParameter(2, begin)
					.setParameter(3, end);
			@SuppressWarnings("rawtypes")
			List list = q.getResultList();
			List<ViewQuotesItem> res = new ArrayList<>(list.size());
			for (Object object : list) {
				Object[] arr = (Object[]) object;
				ViewQuotesItem item = new ViewQuotesItem();
				item.setId_sec(Utils.toLong(arr[0]));
				item.setSecurityCode(Utils.toString(arr[1]));
				item.setShortName(Utils.toString(arr[2]));
				item.setSecurityType(Utils.toString(arr[3]));
				item.setQuoteDate(Utils.toDate(arr[4]));
				item.setPrice(Utils.toDouble(arr[5]));
				item.setCloseprice(Utils.toDouble(arr[6]));
				res.add(item);
			}
			resAll.addAll(res);
		}
		return resAll;
	}

}
