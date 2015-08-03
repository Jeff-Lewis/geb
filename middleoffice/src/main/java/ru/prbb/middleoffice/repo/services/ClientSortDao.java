package ru.prbb.middleoffice.repo.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.ClientSortItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Сортировка клиентов
 * 
 * @author RBr
 */
@Service
public class ClientSortDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ClientSortItem> showSelected(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_SelectClientSort_sp 1}";
		return ems.getSelectList(user, ClientSortItem.class, sql);
	}

	public List<ClientSortItem> showUnselected(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_SelectClientSort_sp 0}";
		return ems.getSelectList(user, ClientSortItem.class, sql);
	}

	public int action(ArmUserInfo user, Long id, Integer flag) {
		String sql = "{call dbo.mo_WebSet_ClientSort_sp ?, ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql, id, flag);
	}

	public int setDate(ArmUserInfo user, Long id, Date date_b) {
		String sql = "{call dbo.mo_WebSet_uDateClientSort_sp ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, date_b);
	}
}
