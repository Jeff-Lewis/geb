/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.bloomberg.BloombergServices;

/**
 * BDP запрос
 * 
 * @author RBr
 */
@Service
public class RequestBDPDaoImpl implements RequestBDPDao
{

	@Autowired
	private EntityManager em;
	@Autowired
	private BloombergServices bs;

	@Override
	public void execute(String[] security, String[] params) {
		if (null == params) {
			params = new String[] {
					"ANNOUNCEMENT_DT", "EQY_DVD_YLD_IND", "EQY_WEIGHTED_AVG_PX",
					"HIGH_52WEEK", "LOW_52WEEK", "PX_LAST", "PX_VOLUME", "EQY_RAW_BETA",
					"OPER_ROE", "BS_TOT_LIAB2", "PE_RATIO", "EBITDA", "BEST_EPS_GAAP",
					"BEST_EPS_GAAP_1WK_CHG", "BEST_EPS_GAAP_3MO_CHG", "BEST_EPS_GAAP_4WK_CHG",
					"BOOK_VAL_PER_SH"
			};
		}
		bs.executeBdpRequest("BDP запрос", security, params);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findParams() {
		String sql = "select code from dbo.cur_request_params_v";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}

}
