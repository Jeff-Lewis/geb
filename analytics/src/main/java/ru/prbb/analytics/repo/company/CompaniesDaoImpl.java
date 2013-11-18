/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.CompaniesItem;

/**
 * Список компаний
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class CompaniesDaoImpl implements CompaniesDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<CompaniesItem> show() {
		final List<CompaniesItem> list = new ArrayList<CompaniesItem>();
		for (long i = 1; i < 11; i++) {
			final CompaniesItem item = new CompaniesItem();
			item.setId_sec(i);
			item.setId_isin("id_isin" + i);
			item.setSecurity_code("SECURITY_CODE_" + i);
			item.setSecurity_name("SECURITY_NAME_" + i);
			item.setCurrency("currency" + i);
			item.setIndstry_grp("indstry_grp" + (i % 3));
			list.add(item);
		}
		return list;
	}

	@Override
	public CompaniesItem findById(Long id) {
		final CompaniesItem item = new CompaniesItem();
		item.setId_sec(id);
		item.setId_isin("id_isin" + id);
		item.setSecurity_code("SECURITY_CODE_" + id);
		item.setSecurity_name("SECURITY_NAME_" + id);
		item.setCurrency("currency" + id);
		item.setIndstry_grp("indstry_grp" + (id % 3));
		return item;
	}

}
