package ru.prbb.jobber.repo.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.DictObjectItem;
import ru.prbb.jobber.services.EntityManagerService;

/**
 * 
 * @author BrihlyaevRA
 *
 */
@Service
public class DictObjectsDao
{
	@Autowired
	private EntityManagerService ems;

	public List<DictObjectItem> findAll() {
		String sql = "{call dbo.WebGet_SelectObjects_sp}";
		return ems.getSelectList(DictObjectItem.class, sql);
	}

	public DictObjectItem findById(Long id) {
		String sql = "{call dbo.WebGet_SelectObjects_sp ?}";
		return ems.getSelectItem(DictObjectItem.class, sql, id);
	}

	public int put(String name, String comment) {
		String sql = "{call dbo.WebSet_iudObjects_sp  'i', null, ?, ?}";
		return ems.executeUpdate(sql, name, comment);
	}

	public int updateById(Long id, String name, String comment) {
		String sql = "{call dbo.WebSet_iudObjects_sp  'u', ?, ?, ?}";
		return ems.executeUpdate(sql, id, name, comment);
	}

	public int deleteById(Long id) {
		String sql = "{call dbo.WebSet_iudObjects_sp  'd', ?}";
		return ems.executeUpdate(sql, id);
	}
}
