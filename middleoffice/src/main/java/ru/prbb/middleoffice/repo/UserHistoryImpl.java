package ru.prbb.middleoffice.repo;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author RBr
 */
@Repository
public class UserHistoryImpl implements UserHistory {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManager em;

	@Override
	public int putHist(String user_login, String user_ip, String command) {
		String sql = "{call dbo.WebSet_putHist_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, user_login)
				.setParameter(2, user_ip)
				.setParameter(3, command);
		return q.executeUpdate();
	}

	@Override
	public void checkAccess(String user_login, String sqlAccess, AccessAction accessAction) {
		if (sqlAccess.startsWith("{call dbo.")) {
			String object_name = sqlAccess.substring(10);
			int idx = object_name.indexOf(' ');
			if (idx > 0) {
				object_name = object_name.substring(0, idx - 1);
			}

			String sql = "{call dbo.WebGet_Permission2user_sp ?, null, ?, ?, null}";
			Query q = em.createNativeQuery(sql)
					.setParameter(1, user_login)
					.setParameter(2, object_name)
					.setParameter(3, accessAction.getPermissionAction());
			// TODO WebGet_Permission2user_sp q.executeUpdate();
		} else {
			log.warn("CheckAccess for " + sqlAccess);
		}
	}
}
