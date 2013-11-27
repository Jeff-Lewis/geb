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
		for (Long id : ids) {
			BuildModelItem item;
			try {
				item = buildModelCompany(id);
			} catch (DataAccessException e) {
				item = new BuildModelItem();
				item.setSecurity_code(id);
				item.setStatus(e.toString());
			}
			list.add(item);
		}
		return list;
	}

	private BuildModelItem buildModelCompany(Long id) {
		String sql = "{call dbo.build_model_proc_p :id}";
		return em.createQuery(sql, BuildModelItem.class).setParameter(1, id).getSingleResult();
	}

	@Override
	public List<BuildModelItem> calculateSvod() {
		String sql = "{call dbo.build_model_proc}";
		return em.createQuery(sql, BuildModelItem.class).getResultList();
	}

}
