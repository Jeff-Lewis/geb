/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
		for (Long id : ids) {
			BuildEPSItem item;
			try {
				item = calculateEps(id);
			} catch (DataAccessException e) {
				item = new BuildEPSItem();
				item.setSecurity_code(id.toString());
			}
			list.add(item);
		}
		return list;
	}

	public BuildEPSItem calculateEps(Long id) {
		String sql = "{call main_create_eps_proc :id}";
		return em.createQuery(sql, BuildEPSItem.class).setParameter(1, id).getSingleResult();
	}

	@Override
	public List<BuildEPSItem> calculate() {
		String sql = "{call main_create_eps_proc}";
		return em.createQuery(sql, BuildEPSItem.class).getResultList();
	}

}
