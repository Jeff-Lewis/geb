/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;
import java.util.Set;

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
 * BDP с override
 * 
 * @author RBr
 * 
 */
@Service
public class RequestBDPovrDaoImpl implements RequestBDPovrDao
{
	@Autowired
	private EntityManager em;
	@Autowired
	private BloombergServices bs;

	@Override
	public void execute(String[] security, String[] params, String over, String period) {
		bs.executeBdpRequestOverride("BDP с override", security, params, period, over);
	}

	@Override
	public void execute(String[] security, String[] params, String over, Set<String> _currency) {
		bs.executeBdpRequestOverrideQuarter("BDP с override-quarter", security, params,
				_currency, over);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findParams() {
		String sql = "select code from dbo.fundamentals_params_v";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> comboFilterOverride(String query) {
		String sql = "select code from dbo.blm_datasource_ovr";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}
}
