/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.BrokersCoverageItem;

/**
 * Покрытие брокеров
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class BrokersCoverageDaoImpl implements BrokersCoverageDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<BrokersCoverageItem> execute() {
		final ArrayList<BrokersCoverageItem> list = new ArrayList<BrokersCoverageItem>();
		for (long i = 1; i < 11; i++) {
			final BrokersCoverageItem item = new BrokersCoverageItem();
			item.setId_sec(i);
			item.setSecurity_code("SECURITY_CODE_" + i);
			item.setShort_name("SHORT_NAME_" + i);
			item.setPivot_group("PIVOT_GROUP_" + (i % 3));
			list.add(item);
		}
		return list;
	}

	@Override
	public void change(Long id, String broker, String value) {
		// TODO Auto-generated method stub

	}
}
