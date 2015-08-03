package ru.prbb.jobber.repo.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.DictGroupItem;
import ru.prbb.jobber.domain.DictGroupsObjectItem;
import ru.prbb.jobber.domain.DictGroupsPermisionItem;
import ru.prbb.jobber.domain.DictGroupsUserItem;
import ru.prbb.jobber.services.EntityManagerService;

/**
 * @author BrihlyaevRA
 */
@Service
public class DictGroupsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<DictGroupItem> findAll() {
		String sql = "select group_id, group_name, group_comment from dbo.groups_v";
		return ems.getSelectList(DictGroupItem.class, sql);
	}

	public DictGroupItem findById(Long id) {
		String sql = "select group_id, group_name, group_comment from dbo.groups_v where group_id=?";
		return ems.getSelectItem(DictGroupItem.class, sql, id);
	}

	public int put(String name, String comment) {
		String sql = "{call dbo.WebSet_iudGroups_sp  'i', null, ?, ?}";
		return ems.executeUpdate(sql, name, comment);
	}

	public int updateById(Long id, String name, String comment) {
		String sql = "{call dbo.WebSet_iudGroups_sp  'u', ?, ?, ?}";
		return ems.executeUpdate(sql, id, name, comment);
	}

	public int deleteById(Long id) {
		String sql = "{call dbo.WebSet_iudGroups_sp  'd', ?}";
		return ems.executeUpdate(sql, id);
	}

	public List<DictGroupsUserItem> findAllUsers(Long id) {
		String sql = "{call dbo.WebGet_noGroupUsers_sp ?}";
		return ems.getSelectList(DictGroupsUserItem.class, sql, id);
	}

	public List<DictGroupsUserItem> findAllStaff(Long id) {
		String sql = "{call dbo.WebGet_SelectGroupUsers_sp ?}";
		return ems.getSelectList(DictGroupsUserItem.class, sql, id);
	}

	public void addStuff(Long idGroup, Long[] ids) {
		String sql = "{call dbo.WebSet_mapUser2Group_sp ?, ?}";
		for (Long id : ids) {
			ems.executeUpdate(sql, idGroup, id);
		}
	}

	public void delStuff(Long idGroup, Long[] ids) {
		String sql = "{call dbo.WebSet_dUserFromGroup_sp ?, ?}";
		for (Long id : ids) {
			ems.executeUpdate(sql, idGroup, id);
		}
	}

	public List<DictGroupsObjectItem> findAllObjects(Long id) {
		String sql = "{call dbo.WebGet_noPermission2Group_sp ?}";
		return ems.getSelectList(DictGroupsObjectItem.class, sql, id);
	}

	public List<DictGroupsPermisionItem> findAllPermission(Long id) {
		String sql = "{call dbo.WebGet_SelectPermission2Group_sp ?}";
		return ems.getSelectList(DictGroupsPermisionItem.class, sql, id);
	}

	public void addPermission(Long idGroup, Long[] objects, Long[] ids) {
		String sql = "{call dbo.WebSet_iudPermission2group_sp 'i', null, ?, ?, ?}";
		for (Long idObject : objects) {
			for (Long idPermission : ids) {
				ems.executeUpdate(sql, idObject, idPermission, idGroup);
			}
		}
	}

	public void delPermission(Long idGroup, Long[] ids) {
		String sql = "{call dbo.WebSet_iudPermission2group_sp 'd', ?}";
		for (Long id : ids) {
			ems.executeUpdate(sql, id);
		}
	}
}
