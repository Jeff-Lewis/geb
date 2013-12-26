/**
 * 
 */
package ru.prbb.analytics.repo.params;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
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
				" from bloomberg_dl_fields where field_mnemonic=?";
		NewParamItem res = null;
		try {
			Query q = em.createNativeQuery(sql, NewParamItem.class)
					.setParameter(1, code);
			res = (NewParamItem) q.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (null == res) {
			// TODO answer = bs.executeFieldInfoRequest("Ввод нового параметра", code);
			res = new NewParamItem();
			res.setCode(code);
			res.setBlmId("blmId");
			res.setName("name");
		}
		return res;
	}

	@Override
	public int save(String blm_id, String code, String name) {
		String sql = "{call dbo.put_params_data ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, blm_id)
				.setParameter(2, code)
				.setParameter(3, name);
		return q.executeUpdate();
	}

	@Override
	public int saveOvr(String code, String broker) {
		String sql = "insert into dbo.blm_datasource_ovr (code, brocker) values (?, ?)";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, code)
				.setParameter(2, broker);
		return q.executeUpdate();
	}

}
