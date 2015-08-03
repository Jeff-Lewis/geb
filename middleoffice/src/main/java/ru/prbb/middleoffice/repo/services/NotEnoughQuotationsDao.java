/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ru.prbb.ArmUserInfo;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.NotEnoughQuotationsItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Не хватает котировок
 * 
 * @author RBr
 */
@Service
public class NotEnoughQuotationsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<NotEnoughQuotationsItem> show(ArmUserInfo user) {
		String sql = "select * from dbo.mo_WebGet_QuotesNotExist_v";
		return ems.getSelectList(user, NotEnoughQuotationsItem.class, sql);
	}

}
