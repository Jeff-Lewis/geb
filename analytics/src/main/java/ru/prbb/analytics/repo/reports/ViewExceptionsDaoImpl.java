/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.ViewExceptionsItem;

/**
 * Отчёт по исключениям
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class ViewExceptionsDaoImpl implements ViewExceptionsDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<ViewExceptionsItem> execute() {
		final List<ViewExceptionsItem> list = new ArrayList<ViewExceptionsItem>();
		for (int i = 1; i < 11; i++) {
			final ViewExceptionsItem item = new ViewExceptionsItem();
			item.setSec_code("SEC_CODE_" + i);
			item.setRs_code("RS_CODE_" + i);
			item.setExc("EXC_" + i);
			item.setR_par("R_PAR_" + i);
			list.add(item);
		}
		return list;
	}

}
