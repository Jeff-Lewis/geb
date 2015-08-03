/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ru.prbb.ArmUserInfo;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.NotEnoughDividendsItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Нет настроек для дивидендов
 * 
 * @author RBr
 */
@Service
public class NotVisibleDividendsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<NotEnoughDividendsItem> show(ArmUserInfo user) {
		String sql = "select * from dbo.mo_WebGet_DividendsNotVisible_v";
		return ems.getSelectList(user, NotEnoughDividendsItem.class, sql);
	}

}
