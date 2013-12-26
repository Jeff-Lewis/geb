/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.CurrencyRateItem;

/**
 * @author RBr
 *
 */
@Repository
@Transactional
public class CurrencyRateDaoImpl implements CurrencyRateDao
{
	@Autowired
	private EntityManager em;

	@Transactional(readOnly = true)
	@Override
	public List<CurrencyRateItem> findAll(Date dated, String iso) {
		String sql = "{call dbo.mo_WebGet_CurrencyRate_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, dated)
				.setParameter(2, iso);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<CurrencyRateItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			CurrencyRateItem item = new CurrencyRateItem();
			item.setDated(Utils.toTimestamp(arr[0]));
			item.setCode(Utils.toString(arr[1]));
			item.setScale(Utils.toInteger(arr[2]));
			item.setIso(Utils.toString(arr[3]));
			item.setName(Utils.toString(arr[4]));
			item.setRate(Utils.toDouble(arr[5]));
			res.add(item);
		}
		return res;
	}

}
