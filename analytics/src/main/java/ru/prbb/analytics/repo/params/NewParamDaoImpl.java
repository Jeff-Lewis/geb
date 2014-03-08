/**
 * 
 */
package ru.prbb.analytics.repo.params;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.NewParamItem;
import ru.prbb.analytics.repo.BloombergServicesA;

/**
 * Ввод нового параметра<br>
 * Ввод нового параметра blm_datasource_ovr
 * 
 * @author RBr
 */
@Service
public class NewParamDaoImpl implements NewParamDao
{

	@Autowired
	private EntityManager em;

	@Autowired
	private BloombergServicesA bs;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public NewParamItem setup(String code) {
		NewParamItem res = null;
		try {
			String sql = "select field_mnemonic as code, field_id as blmId, description as name" +
					" from bloomberg_dl_fields where field_mnemonic=?";
			Query q = em.createNativeQuery(sql, NewParamItem.class)
					.setParameter(1, code);
			res = (NewParamItem) q.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (null == res) {
			Map<String, Object> answer = bs.executeFieldInfoRequest("Ввод нового параметра", code);
			res = new NewParamItem();
			res.setCode(answer.get("CODE").toString());
			res.setBlmId(answer.get("BLM_ID").toString());
			res.setName(answer.get("NAME").toString());
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int save(String blm_id, String code, String name) {
		String sql = "{call dbo.put_params_data ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, blm_id)
				.setParameter(2, code)
				.setParameter(3, name);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int saveOvr(String code, String broker) {
		String sql = "insert into dbo.blm_datasource_ovr (code, brocker) values (?, ?)";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, code)
				.setParameter(2, broker);
		return q.executeUpdate();
	}

}
