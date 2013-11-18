/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.SecurityItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * @author RBr
 *
 */
@Repository
@Transactional
public class SecuritiesDaoImpl implements SecuritiesDao {

	@Override
	public List<SecurityItem> findAll(String filter, Long security) {
		// "{call dbo.mo_WebGet_FilterSecurities_sp ?, ?}"
		final List<SecurityItem> list = new ArrayList<SecurityItem>();
		for (long i = 1; i < 11; ++i) {
			final SecurityItem item = new SecurityItem();
			item.setId_sec(i);
			item.setSecurity_code("security_code" + i);
			item.setShort_name("short_name" + i);
			item.setType_id(null);
			list.add(item);
		}
		return list;
	}

	@Override
	public List<SimpleItem> findCombo(String query) {
		// select id, name from dbo.mo_WebGet_ajaxSecurities_v
		// " where lower(name) like ?"
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (int i = 0; i < 10; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}

	@Override
	public List<SimpleItem> findComboFilter(String query) {
		// "select * from dbo.mo_WebGet_ajaxFilterRequest_v"
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (int i = 0; i < 10; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}

}
