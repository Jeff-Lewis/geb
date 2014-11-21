/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.BuildModelItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * Расчёт модели по компании
 * 
 * @author RBr
 * 
 */
@Service
public class BuildModelDaoImpl extends BaseDaoImpl implements BuildModelDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<BuildModelItem> calculateModel(Long... ids) {
		final List<BuildModelItem> list = new ArrayList<>();
		String sql = "{call dbo.build_model_proc_p ?}";
		Query q = em.createNativeQuery(sql);
		for (Long id : ids) {
			BuildModelItem item = new BuildModelItem();
			try {
				q.setParameter(1, id);
				Object[] arr = (Object[]) getSingleResult(q, sql);
				item.setSecurity_code(Utils.toString(arr[0]));
				item.setStatus(Utils.toString(arr[1]));
			} catch (Exception e) {
				item.setSecurity_code(id.toString());
				item.setStatus(e.getMessage());
			}
			list.add(item);
		}
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<BuildModelItem> calculateSvod() {
		String sql = "{call dbo.build_model_proc}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<BuildModelItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			BuildModelItem item = new BuildModelItem();
			item.setSecurity_code(Utils.toString(arr[1]));
			item.setStatus(Utils.toString(arr[2]));
			res.add(item);
		}
		return res;
	}

}
