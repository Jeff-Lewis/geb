package ru.prbb.analytics.repo.users;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.DictGroupItem;
import ru.prbb.analytics.domain.DictGroupsObjectItem;
import ru.prbb.analytics.domain.DictGroupsPermisionItem;
import ru.prbb.analytics.domain.DictGroupsUserItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * 
 * @author BrihlyaevRA
 *
 */
@Service
public class DictGroupsDaoImpl extends BaseDaoImpl implements DictGroupsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<DictGroupItem> findAll() {
		String sql = "select group_id, group_name, group_comment from dbo.groups_v";
		Query q = em.createNativeQuery(sql, DictGroupItem.class);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public DictGroupItem findById(Long id) {
		String sql = "select group_id, group_name, group_comment from dbo.groups_v where group_id=?";
		Query q = em.createNativeQuery(sql, DictGroupItem.class)
				.setParameter(1, id);
		return (DictGroupItem) getSingleResult(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String name, String comment) {
		String sql = "{call dbo.WebSet_iudGroups_sp  'i', null, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name)
				.setParameter(2, comment);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, String name, String comment) {
		String sql = "{call dbo.WebSet_iudGroups_sp  'u', ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name)
				.setParameter(3, comment);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.WebSet_iudGroups_sp  'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<DictGroupsUserItem> findAllUsers(Long id) {
		String sql = "{call dbo.WebGet_noGroupUsers_sp ?}";
		Query q = em.createNativeQuery(sql, DictGroupsUserItem.class)
				.setParameter(1, id);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<DictGroupsUserItem> findAllStaff(Long id) {
		String sql = "{call dbo.WebGet_SelectGroupUsers_sp ?}";
		Query q = em.createNativeQuery(sql, DictGroupsUserItem.class)
				.setParameter(1, id);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void addStuff(Long idGroup, Long[] ids) {
		String sql = "{call dbo.WebSet_mapUser2Group_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, idGroup);
		for (Long id : ids) {
			q.setParameter(2, id);
			storeSql(sql, q);
			executeUpdate(q, sql);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void delStuff(Long idGroup, Long[] ids) {
		String sql = "{call dbo.WebSet_dUserFromGroup_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, idGroup);
		for (Long id : ids) {
			q.setParameter(2, id);
			storeSql(sql, q);
			executeUpdate(q, sql);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<DictGroupsObjectItem> findAllObjects(Long id) {
		String sql = "{call dbo.WebGet_noPermission2Group_sp ?}";
		Query q = em.createNativeQuery(sql, DictGroupsObjectItem.class)
				.setParameter(1, id);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<DictGroupsPermisionItem> findAllPermission(Long id) {
		String sql = "{call dbo.WebGet_SelectPermission2Group_sp ?}";
		Query q = em.createNativeQuery(sql, DictGroupsPermisionItem.class)
				.setParameter(1, id);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void addPermission(Long idGroup, Long[] objects, Long[] ids) {
		String sql = "{call dbo.WebSet_iudPermission2group_sp 'i', null, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(3, idGroup);
		for (Long idObject : objects) {
			q.setParameter(1, idObject);
			for (Long idPermission : ids) {
				q.setParameter(2, idPermission);
				storeSql(sql, q);
				executeUpdate(q, sql);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void delPermission(Long idGroup, Long[] ids) {
		String sql = "{call dbo.WebSet_iudPermission2group_sp 'd', ?}";
		Query q = em.createNativeQuery(sql);
		for (Long id : ids) {
			q.setParameter(1, id);
			storeSql(sql, q);
			executeUpdate(q, sql);
		}
	}
}
