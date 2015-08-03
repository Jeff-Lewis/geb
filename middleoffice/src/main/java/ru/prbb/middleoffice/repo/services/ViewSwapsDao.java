/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Редактирование свопов
 * 
 * @author RBr
 */
@Service
public class ViewSwapsDao
{

	@Autowired
	private EntityManagerService ems;

	public int put(ArmUserInfo user, Long id_sec, String deal) {
		String sql = "{call dbo.mo_WebSet_putDealsSecuritiesMapping_sp ?, ?, null, 4}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, id_sec, deal);
	}

	public int del(ArmUserInfo user, Long id_sec, String deal) {
		String sql = "{call dbo.mo_WebSet_dDealsSecuritiesMapping_sp ?, ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id_sec, deal);
	}

}
