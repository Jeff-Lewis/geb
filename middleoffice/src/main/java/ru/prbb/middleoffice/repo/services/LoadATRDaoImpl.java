/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Загрузка ATR
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class LoadATRDaoImpl implements LoadATRDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<Object> execute(String dateStart, String dateEnd, String[] securities, String typeMA, Integer periodTA,
			String period, String calendar) {
		// TODO Auto-generated method stub
		return new ArrayList<Object>();
	}

	@Override
	public List<SimpleItem> getTypeMA(String query) {
		// "select id, algorithm_name as name from dbo.mo_WebGet_ajaxAlgorithm_v"
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (int i = 0; i < 10; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}

	@Override
	public List<SimpleItem> getPeriod(String query) {
		// "select period_id as id, name from dbo.period_type"
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (int i = 0; i < 10; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}

	@Override
	public List<SimpleItem> getCalendar(String query) {
		// "select calendar_id as id, name from calendar_type"
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (int i = 0; i < 10; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}

}
