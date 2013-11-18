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

import ru.prbb.analytics.domain.BuildModelItem;

/**
 * Расчёт модели по компании
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class BuildModelDaoImpl implements BuildModelDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<BuildModelItem> calculateModel(Long[] ids) {
		final List<BuildModelItem> list = new ArrayList<BuildModelItem>();
		for (int i = 0; i < ids.length; i++) {
			final BuildModelItem item = new BuildModelItem();
			item.setSecurity_code("SEC_CODE_" + ids[i]);
			item.setStatus("OK");
			list.add(item);
		}
		return list;
	}

	@Override
	public List<BuildModelItem> calculateSvod() {
		final List<BuildModelItem> list = new ArrayList<BuildModelItem>();
		for (int i = 0; i < 10; i++) {
			final BuildModelItem item = new BuildModelItem();
			item.setSecurity_code("SEC_CODE_" + i);
			item.setStatus("OK");
			list.add(item);
		}
		return list;
	}

}
