/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Задать параметры риска
 * 
 * @author RBr
 */
@Service
public class SetSecurityRiscsDao
{

	@Autowired
	private EntityManagerService ems;

	public int execute(ArmUserInfo user, Long id, Double riskATH, Double riskAVG, Double stopLoss, String comment) {
		String sql = "{call dbo.mo_WebSet_putSecurityRiscs_sp ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql, id, riskATH, riskAVG, stopLoss, comment);
	}

}
