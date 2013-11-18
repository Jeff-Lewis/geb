/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.ReferenceItem;

/**
 * @author RBr
 *
 */
@Repository
@Transactional
public class AccountDaoImpl implements AccountDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<ReferenceItem> findAllOrderedByName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReferenceItem findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long updateById(Long id, ReferenceItem value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
