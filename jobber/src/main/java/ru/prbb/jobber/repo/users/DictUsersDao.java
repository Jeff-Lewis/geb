package ru.prbb.jobber.repo.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.DictUserItem;
import ru.prbb.jobber.domain.DictUsersInfoItem;
import ru.prbb.jobber.services.EntityManagerService;

/**
 * @author BrihlyaevRA
 */
@Service
public class DictUsersDao
{

	@Autowired
	private EntityManagerService ems;

	public List<DictUserItem> findAll() {
		String sql = "select user_id, user_login, user_name, user_email from dbo.users_v";
		return ems.getSelectList(DictUserItem.class, sql);
	}

	public DictUserItem findById(Long id) {
		String sql = "select user_id, user_login, user_name, user_email from dbo.users_v where user_id=?";
		return ems.getSelectItem(DictUserItem.class, sql, id);
	}

	public List<DictUsersInfoItem> findInfoById(Long id) {
		String sql = "{call dbo.WebGet_Permission2user_sp null, null, null, null, ?}";
		return ems.getSelectList(DictUsersInfoItem.class, sql, id);
	}

	public int put(String login, String password, String name, String email) {
		String sql = "{call dbo.WebSet_iudUsers_sp 'i', null, ?, ?, ?, ?}";
		return ems.executeUpdate(sql, login, password, name, email);
	}

	public int updateById(Long id, String login, String password, String name, String email) {
		String sql = "{call dbo.WebSet_iudUsers_sp 'u', ?, ?, ?, ?, ?}";
		return ems.executeUpdate(sql, id, login, password, name, email);
	}

	public int deleteById(Long id) {
		String sql = "{call dbo.WebSet_iudUsers_sp 'd', ?}";
		return ems.executeUpdate(sql, id);
	}

}
