package ru.prbb.analytics.repo.users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.DictUserItem;
import ru.prbb.analytics.domain.DictUsersInfoItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * @author BrihlyaevRA
 */
@Service
public class DictUsersDaoImpl extends BaseDaoImpl implements DictUsersDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<DictUserItem> findAll() {
		String sql = "select user_id, user_login, user_name, user_email from users_v";
		Query q = em.createNativeQuery(sql, DictUserItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public DictUserItem findById(Long id) {
		String sql = "select user_id, user_login, user_name, user_email from users_v where user_id=?";
		Query q = em.createNativeQuery(sql, DictUserItem.class)
				.setParameter(1, id);
		return (DictUserItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<DictUsersInfoItem> findInfoById(Long id) {
		String sql = "{call dbo.WebGet_Permission2user_sp null, null, null, null, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		ArrayList<DictUsersInfoItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			DictUsersInfoItem item = new DictUsersInfoItem();
			item.setLogin(Utils.toString(arr[0]));
			item.setName(Utils.toString(arr[1]));
			item.setObject(Utils.toString(arr[2]));
			item.setPermission(Utils.toString(arr[3]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String login, String password, String name, String email) {
		String sql = "{call dbo.WebSet_iudUsers_sp 'i', null, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, login)
				.setParameter(2, password)
				.setParameter(3, name)
				.setParameter(4, email);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, String login, String password, String name, String email) {
		String sql = "{call dbo.WebSet_iudUsers_sp 'u', ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, login)
				.setParameter(3, password)
				.setParameter(4, name)
				.setParameter(5, email);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.WebSet_iudUsers_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return q.executeUpdate();
	}

}
