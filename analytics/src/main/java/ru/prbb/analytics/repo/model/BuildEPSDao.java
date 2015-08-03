/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.BuildEPSItem;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Расчёт EPS по компании
 * 
 * @author RBr
 * 
 */
@Service
public class BuildEPSDao
{
	@Autowired
	private EntityManagerService ems;

	public BuildEPSItem calculate(ArmUserInfo user, Long id) {
		String sql = "{call dbo.main_create_eps_proc ?}";
		return ems.getResultItem(user, BuildEPSItem.class, sql, id);
	}

	public List<BuildEPSItem> calculate(ArmUserInfo user) {
		String sql = "{call dbo.main_create_eps_proc}";
		return ems.getResultList(user, BuildEPSItem.class, sql);
	}

}
