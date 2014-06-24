/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Parameter;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ruslan
 */
public abstract class BaseDaoImpl {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected void storeSql(String sql, Query q) {
		if (q.getParameters().isEmpty()) {
			log.info(sql);
		} else {
			StringBuilder res = new StringBuilder(sql);

			List<Parameter<?>> ps = new ArrayList<>(q.getParameters());
			Collections.sort(ps, new Comparator<Parameter<?>>() {

				@Override
				public int compare(Parameter<?> o1, Parameter<?> o2) {
					return o1.getPosition().compareTo(o2.getPosition());
				}
			});

			res.append('(');
			for (Parameter<?> p : ps) {
				Object pv = q.getParameterValue(p);
				res.append(pv);
				res.append(',');
			}
			res.setCharAt(res.length(), ')');

			log.info(res.toString());
		}
	}
}
