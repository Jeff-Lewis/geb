/**
 * 
 */
package ru.prbb.analytics.repo.params;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.ViewParamsItem;

/**
 * Справочник параметров
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class ViewParamsDaoImpl implements ViewParamsDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<ViewParamsItem> show() {
		ArrayList<ViewParamsItem> list = new ArrayList<ViewParamsItem>();
		for (long i = 1; i < 11; i++) {
			ViewParamsItem item = new ViewParamsItem();
			item.setParam_id(i);
			item.setBlm_id("blm_id" + i);
			item.setCode("code" + i);
			item.setName("name" + i);
			list.add(item);
		}
		return list;
	}

	@Override
	public ViewParamsItem getById(String blm_id) {
		ViewParamsItem item = new ViewParamsItem();
		item.setParam_id(1L);
		item.setBlm_id("blm_id" + blm_id);
		item.setCode("code" + blm_id);
		item.setName("name" + blm_id);
		return item;
	}

}
