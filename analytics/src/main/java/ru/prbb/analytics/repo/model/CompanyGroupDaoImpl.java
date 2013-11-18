/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.CompanyStaffItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * Компании и группы
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class CompanyGroupDaoImpl implements CompanyGroupDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<SimpleItem> findAll() {
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (long i = 1; i < 11; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i);
			item.setName("NAME_" + i);
			list.add(item);
		}
		return list;
	}

	@Override
	public SimpleItem findById(Long id) {
		SimpleItem item = new SimpleItem();
		item.setId(id);
		item.setName("NAME_" + id);
		return item;
	}

	@Override
	public void put(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renameById(Long id, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CompanyStaffItem> findStaff(Long id) {
		final List<CompanyStaffItem> list = new ArrayList<CompanyStaffItem>();
		for (long i = 1; i < 11; i++) {
			final CompanyStaffItem item = new CompanyStaffItem();
			item.setId_sec(i);
			item.setShort_name("NAME_" + i);
			list.add(item);
		}
		return list;
	}

	@Override
	public List<CompanyStaffItem> findStaffGroup(Long id) {
		final List<CompanyStaffItem> list = new ArrayList<CompanyStaffItem>();
		for (long i = 1; i < 11; i++) {
			final CompanyStaffItem item = new CompanyStaffItem();
			item.setId_sec(i);
			item.setShort_name("NAME_" + i);
			list.add(item);
		}
		return list;
	}

	@Override
	public void putStaff(Long id, Long[] ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteStaff(Long id, Long[] ids) {
		// TODO Auto-generated method stub

	}

}
