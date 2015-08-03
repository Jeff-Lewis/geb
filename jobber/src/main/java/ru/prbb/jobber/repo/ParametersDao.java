/**
 * 
 */
package ru.prbb.jobber.repo;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class ParametersDao
{

	@Autowired
	private EntityManagerService ems;

	public String getValue(String name) {
		try {
			String sql = "select value from dbo.parameters where name = ?";
			Object result = ems.getSelectItem(Object.class, sql, name);
			return result.toString();
		} catch (PersistenceException ignore) {
		}
		return "";
	}

	public int setValue(String name, String value) {
		String sqlCount = "select count(*) from dbo.parameters where name = ?";
		Number count = ems.getSelectItem(Number.class, sqlCount, name);

		String sql = (count.intValue() == 0) ?
				"insert into dbo.parameters (value, name) values (?, ?)" :
				"update dbo.parameters set value = ? where name = ?";
		return ems.executeUpdate(sql, value, name);
	}

	public int delValue(String name) {
		String sql = "delete dbo.parameters where name = ?";
		return ems.executeUpdate(sql, name);
	}

}
