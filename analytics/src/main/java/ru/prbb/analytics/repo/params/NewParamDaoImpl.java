/**
 * 
 */
package ru.prbb.analytics.repo.params;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.NewParamItem;

/**
 * Ввод нового параметра<br>
 * Ввод нового параметра blm_datasource_ovr
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class NewParamDaoImpl implements NewParamDao
{
	@Autowired
	private EntityManager em;

	@Override
	public NewParamItem setup(String code) {
		String sql = "select field_mnemonic as code, field_id as blmId, description as name" +
				" from bloomberg_dl_fields where field_mnemonic=:code";
		try {
			return em.createQuery(sql, NewParamItem.class).setParameter(1, code).getSingleResult();
		} catch (DataAccessException e) {
			// TODO: handle exception
			//answer = bs.executeFieldInfoRequest("Ввод нового параметра", code);
		}
		return null;
	}

	@Override
	public int save(String blm_id, String code, String name) {
		String sql = "{call dbo.put_params_data :blm_id, :code, :name}";
		return em.createQuery(sql).setParameter(1, blm_id).setParameter(2, code).setParameter(3, name).executeUpdate();
	}

	@Override
	public int saveOvr(String code, String broker) {
		String sql = "insert into dbo.blm_datasource_ovr (code, brocker) values (:code, :broker)";
		return em.createQuery(sql).setParameter(1, code).setParameter(2, broker).executeUpdate();
	}

}
