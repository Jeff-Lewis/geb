/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RBr
 *
 */
@Service
@Transactional
public class DealsOneNewDaoImpl implements DealsOneNewDao
{
	@Autowired
	private EntityManager em;

}
