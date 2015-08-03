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
 * Перекидка ЦБ между фондами
 * 
 * @author RBr
 */
@Service
public class DealsTransferDao
{

	@Autowired
	private EntityManagerService ems;

	public int execute(ArmUserInfo user, Long portfolioId, Integer quantity,
			Double price, Long fundId, Integer batch, String comment) {
		String sql = "{call dbo.mo_WebSet_putTransferDeals_sp ?, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql, portfolioId, quantity, price, fundId, batch, comment);
	}

}
