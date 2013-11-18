/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.ViewModelItem;

/**
 * Просмотр текущей модели
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class ViewModelDaoImpl implements ViewModelDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<ViewModelItem> current() {
		final List<ViewModelItem> list = new ArrayList<ViewModelItem>();
		for (long i = 1; i < 11; i++) {
			final ViewModelItem item = new ViewModelItem();
			item.setId_sec(i);
			item.setCompany_short_name("SEC_CODE_" + i);
			list.add(item);
		}
		return list;
	}

	@Override
	public ViewModelItem getById(Long id_sec) {
		final ViewModelItem item = new ViewModelItem();
		item.setId_sec(id_sec);
		item.setCompany_short_name("SEC_CODE_" + id_sec);
		return item;
	}
}
