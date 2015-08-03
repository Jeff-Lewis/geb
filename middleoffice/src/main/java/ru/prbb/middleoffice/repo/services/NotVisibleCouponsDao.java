/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ru.prbb.ArmUserInfo;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.NotEnoughCouponsItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Нет настроек для купонов
 * 
 * @author RBr
 */
@Service
public class NotVisibleCouponsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<NotEnoughCouponsItem> show(ArmUserInfo user) {
		String sql = "select * from dbo.mo_WebGet_CouponsNotVisible_v";
		return ems.getSelectList(user, NotEnoughCouponsItem.class, sql);
	}

}
