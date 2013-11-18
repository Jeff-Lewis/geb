/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * @author RBr
 *
 */
@Repository
@Transactional
public class ContractDaoImpl implements ContractDao
{
	@Autowired
	private EntityManager em;

	/**
	 * @return
	 */
	public List<SimpleItem> findAllOrderedByName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param id
	 * @return
	 */
	public SimpleItem findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, SimpleItem value) {
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

	/* (non-Javadoc)
	 * @see ru.prbb.middleoffice.repo.ContractDao#findAll()
	 */
	@Override
	public List<SimpleItem> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
