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
 * Редактирование фьючерсов
 * 
 * @author RBr
 */
@Service
public class ViewFuturesDao
{

	@Autowired
	private EntityManagerService ems;

	public int put(ArmUserInfo user, Long id_sec, String deal, Long futures) {
		String sql = "{call dbo.blm_cmdt_mapping ?, ?, ?, 2}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, id_sec, deal, futures);
	}

	public int del(ArmUserInfo user, Long id_sec, String deal) {
		String sql = "{call dbo.blm_cmdt_delete ?, ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id_sec, deal);
	}

}
