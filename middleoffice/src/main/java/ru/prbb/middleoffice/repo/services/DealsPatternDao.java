/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.DealsPatternItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Сохраненные шаблоны
 * 
 * @author RBr
 */
@Service
public class DealsPatternDao
{

	@Autowired
	private EntityManagerService ems;

	public List<DealsPatternItem> show(ArmUserInfo user) {
		String sql = "select id, file_name, file_type, date_insert from dbo.DealsTemplateStorage";
		return ems.getSelectList(user, DealsPatternItem.class, sql);
	}

	public DealsPatternItem getById(ArmUserInfo user, Long id) {
		String sql = "select id, file_name, file_type, date_insert from dbo.DealsTemplateStorage where id=?";
		return ems.getSelectItem(user, DealsPatternItem.class, sql, id);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "delete from dbo.DealsTemplateStorage where id=?";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public byte[] getFileById(ArmUserInfo user, Long id) {
		String sql = "select file from dbo.DealsTemplateStorage where id=?";
		return ems.getSelectItem(user, byte[].class, sql, id);
	}

	public int add(ArmUserInfo user, String name, String type, byte[] bytes) {
		String sql = "insert into dbo.DealsTemplateStorage" +
				" (file, file_name, file_type) values(?, ?, ?)";
		return ems.executeUpdate(AccessAction.NONE, user, sql, bytes, name, type);
	}
}
