/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.ReferenceItem;

/**
 * Справочник контактов
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class ContactsDaoImpl implements ContactsDao
{
	@Autowired
	private EntityManager em;

	/**
	 * @return
	 */
	public List<ReferenceItem> findAllOrderedByName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param id
	 * @return
	 */
	public ReferenceItem findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, ReferenceItem value) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
