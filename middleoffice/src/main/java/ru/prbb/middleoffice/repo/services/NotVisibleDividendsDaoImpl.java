/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.NotEnoughDividendsItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Нет настроек для дивидендов
 * 
 * @author RBr
 */
@Repository
public class NotVisibleDividendsDaoImpl extends BaseDaoImpl implements NotVisibleDividendsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<NotEnoughDividendsItem> show() {
		String sql = "select * from dbo.mo_WebGet_DividendsNotVisible_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = getResultList(q, sql);
		List<NotEnoughDividendsItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			NotEnoughDividendsItem item = new NotEnoughDividendsItem();
			item.setSecurity_code(Utils.toString(arr[0]));
			item.setShort_name(Utils.toString(arr[1]));
			item.setClient(Utils.toString(arr[2]));
			item.setFund(Utils.toString(arr[3]));
			item.setBroker(Utils.toString(arr[4]));
			item.setAccount(Utils.toString(arr[5]));
			item.setCurrency(Utils.toString(arr[6]));
			item.setRecord_date(Utils.toDate(arr[7]));
			item.setQuantity(Utils.toInteger(arr[8]));
			item.setDividend_per_share(Utils.toDouble(arr[9]));
			item.setReceive_date(Utils.toDate(arr[10]));
			res.add(item);
		}
		return res;
	}

}
