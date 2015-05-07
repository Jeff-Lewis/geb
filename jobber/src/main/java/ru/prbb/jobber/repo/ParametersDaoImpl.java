/**
 * 
 */
package ru.prbb.jobber.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RBr
 */
@Repository
public class ParametersDaoImpl extends BaseDaoImpl implements ParametersDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public String getValue(String name) {
		try {
			String sql = "select value from dbo.parameters where name = ?";
			Query q = em.createNativeQuery(sql)
					.setParameter(1, name);
			Object result = getSingleResult(q, sql);
			return result.toString();
		} catch (PersistenceException ignore) {
		}
		return "";
	}
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int setValue(String name, String value) {
		Number count = (Number)
				em.createNativeQuery("select count(*) from dbo.parameters where name = ?")
						.setParameter(1, name)
						.getSingleResult();

		String sql = (count.intValue() == 0) ?
				"insert into dbo.parameters (value, name) values (?, ?)" :
				"update dbo.parameters set value = ? where name = ?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, value)
				.setParameter(2, name);
		return executeUpdate(q, sql);
	}


	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delValue(String name) {
		String sql = "delete dbo.parameters where name = ?";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name);
		return executeUpdate(q, sql);
	}

}
