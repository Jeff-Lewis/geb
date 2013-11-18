/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.DealsPatternItem;

/**
 * Сохраненные шаблоны
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class DealsPatternDaoImpl implements DealsPatternDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<DealsPatternItem> show() {
		// "select id, file_name, file_type, date_insert from dbo.DealsTemplateStorage"
		final List<DealsPatternItem> list = new ArrayList<DealsPatternItem>();
		for (long i = 1; i < 3; ++i) {
			final DealsPatternItem map = new DealsPatternItem();
			map.setId(i);
			map.setFile_name("file_name" + i);
			map.setFile_type("file_type" + i);
			map.setDate_insert(null);
			list.add(map);
		}
		return list;
	}

	@Override
	public void deleteById(Long id) {
		// "delete from dbo.DealsTemplateStorage where id = ?"

	}

}
