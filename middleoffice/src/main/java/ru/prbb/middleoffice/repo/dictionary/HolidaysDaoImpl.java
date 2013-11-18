/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author RBr
 * 
 */
@Repository
@Transactional
public class HolidaysDaoImpl implements HolidaysDao
{
	@Autowired
	private EntityManager em;

}
