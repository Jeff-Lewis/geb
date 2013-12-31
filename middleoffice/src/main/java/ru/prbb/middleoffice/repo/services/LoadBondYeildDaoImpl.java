/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Загрузка доходности облигаций
 * 
 * @author RBr
 * 
 */
@Service
public class LoadBondYeildDaoImpl implements LoadBondYeildDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<Map<String, Object>> execute(String dateStart, String dateEnd, String[] securities) {
		// TODO LoadBondYeildDaoImpl.execute
		return new ArrayList<Map<String, Object>>();
	}

}
