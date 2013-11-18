/**
 * 
 */
package ru.prbb.analytics.repo.params;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public Map<String, String> setup(String code) {
		Map<String, String> item = new HashMap<String, String>();
		item.put("NAME", "name");
		item.put("BLM_ID", "blm_id");
		return item;
	}

	@Override
	public void save(String blm_id, String code, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveOvr(String code, String broker) {
		// TODO Auto-generated method stub

	}

}
