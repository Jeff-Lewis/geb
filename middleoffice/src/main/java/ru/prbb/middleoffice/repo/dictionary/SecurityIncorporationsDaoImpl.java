/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.SecIncItem;

/**
 * Регистрация инструментов
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class SecurityIncorporationsDaoImpl implements SecurityIncorporationsDao
{
	@Autowired
	private EntityManager em;

	/**
	 * @return
	 */
	public List<SecIncItem> findAll() {
		// {call dbo.mo_WebGet_SecurityIncorporations_sp}
		//return em.createQuery("{call dbo.anca_WebGet_SelectBrokers_sp}", ReferenceItem.class).getResultList();
		final List<SecIncItem> list = new ArrayList<SecIncItem>();
		for (int i = 0; i < 10; i++) {
			final SecIncItem item = new SecIncItem();
			list.add(item);
		}
		return list;
	}

	/**
	 * @param id
	 * @return
	 */
	public SecIncItem findById(Long id) {
		// select * from mo_WebGet_SecurityIncorporations_v where id = ?
		final SecIncItem item = new SecIncItem();
		return item;
	}

	/**
	 * 
	 */
	@Override
	public Long put(SecIncItem value) {
		// "{call dbo.mo_WebSet_putSecurityIncorporations_sp ?, ?, ?}"
		return value.getSip();
	}

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, SecIncItem value) {
		// "{call dbo.mo_WebSet_udSecurityIncorporations_sp 'u', ?, ?, ?}"
		return id;
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		// "{call dbo.mo_WebSet_udSecurityIncorporations_sp 'd', ?}"
	}

}
