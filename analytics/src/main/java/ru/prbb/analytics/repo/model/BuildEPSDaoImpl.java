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

import ru.prbb.analytics.domain.BuildEPSItem;

/**
 * Расчёт EPS по компании
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class BuildEPSDaoImpl implements BuildEPSDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<BuildEPSItem> calculate(Long[] ids) {
		final List<BuildEPSItem> list = new ArrayList<BuildEPSItem>();
		for (int i = 0; i < ids.length; i++) {
			final BuildEPSItem item = new BuildEPSItem();
			item.setSecurity_code("SEC_CODE_" + ids[i]);
			list.add(item);
		}
		return list;
	}

	@Override
	public List<BuildEPSItem> calculate() {
		final List<BuildEPSItem> list = new ArrayList<BuildEPSItem>();
		for (int i = 0; i < 11; i++) {
			final BuildEPSItem item = new BuildEPSItem();
			item.setSecurity_code("SEC_CODE_" + i);
			list.add(item);
		}
		return list;
	}

}
