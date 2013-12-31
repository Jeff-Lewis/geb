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
 * Загрузка ставки по купонам
 * 
 * @author RBr
 * 
 */
@Service
public class LoadRateCouponDaoImpl implements LoadRateCouponDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<Map<String, Object>> execute(String[] securities) {
		// TODO LoadRateCouponDaoImpl.execute
		return new ArrayList<Map<String, Object>>();
	}

}
