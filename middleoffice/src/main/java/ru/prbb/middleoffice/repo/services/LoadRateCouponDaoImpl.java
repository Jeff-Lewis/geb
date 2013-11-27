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
import org.springframework.transaction.annotation.Transactional;

/**
 * Загрузка ставки по купонам
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class LoadRateCouponDaoImpl implements LoadRateCouponDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<Map<String, Object>> execute(String[] securities) {
		// TODO Auto-generated method stub
		return new ArrayList<Map<String, Object>>();
	}

}