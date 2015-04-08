/**
 * 
 */
package ru.prbb.analytics.repo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.analytics.domain.tasks.TaskBdhEpsRequest;
import ru.prbb.analytics.domain.tasks.TaskBdhRequest;
import ru.prbb.analytics.domain.tasks.TaskBdpRequest;
import ru.prbb.analytics.domain.tasks.TaskBdpRequestOverride;
import ru.prbb.analytics.domain.tasks.TaskBdsRequest;
import ru.prbb.analytics.domain.tasks.TaskFieldInfoRequest;
import ru.prbb.analytics.domain.tasks.TaskReferenceDataRequest;
import ru.prbb.analytics.domain.tasks.TaskRequestOverrideQuarter;
import ru.prbb.analytics.services.TasksService;

/**
 * @author RBr
 */
@Service
public final class BloombergServicesATasksImpl implements BloombergServicesA {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private TasksService tasks;

	
	
	
	@Override
	public Map<String, Map<String, String>> executeReferenceDataRequest(String name,
			String[] securities, String[] fields) {
		try {
			TaskReferenceDataRequest task = new TaskReferenceDataRequest(name);
			task.setSecurities(securities);
			task.setFields(fields);

			tasks.execute(task);

			return task.getResult();
		} catch (Exception e) {
			log.error("ReferenceDataRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, String>> executeBdpRequest(String name,
			String[] securities, String[] fields) {
		try {
			TaskBdpRequest task = new TaskBdpRequest(name);
			task.setSecurities(securities);
			task.setFields(fields);

			tasks.execute(task);

			return task.getResult();
		} catch (Exception e) {
			log.error("BdpRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeBdpRequestOverride(String name,
			String[] securities, String[] fields, String period, String over) {
		try {
			TaskBdpRequestOverride task = new TaskBdpRequestOverride(name);
			task.setPeriod(period);
			task.setOver(over);
			task.setSecurities(securities);
			task.setFields(fields);

			tasks.execute(task);

			return task.getResult();
		} catch (Exception e) {
			log.error("BdpRequestOverride", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeBdpRequestOverrideQuarter(String name,
			String[] securities, String[] fields, String[] currencies, String over) {
		try {
			TaskRequestOverrideQuarter task = new TaskRequestOverrideQuarter(name);
			task.setOver(over);
			task.setSecurities(securities);
			task.setFields(fields);
			task.setCurrencies(currencies);
			
			tasks.execute(task);
			
			return task.getResult();
		} catch (Exception e) {
			log.error("BdpRequestOverrideQuarter", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> executeBdsRequest(String name, String[] securities, String[] fields) {
		try {
			TaskBdsRequest task = new TaskBdsRequest(name);
			task.setSecurities(securities);
			task.setFields(fields);
			
			tasks.execute(task);
			
			return task.getResult();
		} catch (Exception e) {
			log.error("BdsRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeBdhEpsRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields) {
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			TaskBdhEpsRequest task = new TaskBdhEpsRequest(name);
			//task.setDateStart(sdf.format(startDate));
			//task.setDateEnd(sdf.format(endDate));
			task.setDateStart(dateStart);
			task.setDateEnd(dateEnd);
			task.setPeriod(period);
			task.setCalendar(calendar);
			task.setCurrencies(currencies);
			task.setSecurities(securities);
			task.setFields(fields);
			
			tasks.execute(task);
			
			return task.getResult();
		} catch (Exception e) {
			log.error("BdhEpsRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeBdhRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields) {
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			TaskBdhRequest task = new TaskBdhRequest(name);
			//task.setDateStart(sdf.format(startDate));
			//task.setDateEnd(sdf.format(endDate));
			task.setDateStart(dateStart);
			task.setDateEnd(dateEnd);
			task.setPeriod(period);
			task.setCalendar(calendar);
			task.setCurrencies(currencies);
			task.setSecurities(securities);
			task.setFields(fields);
			
			tasks.execute(task);
			
			return task.getResult();
		} catch (Exception e) {
			log.error("BdhRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> executeFieldInfoRequest(String name, String code) {
		try {
			TaskFieldInfoRequest task = new TaskFieldInfoRequest(name);
			task.setCode(code);
			
			tasks.execute(task);
			
			return task.getResult();
		} catch (Exception e) {
			log.error("FieldInfoRequest", e);
			throw new RuntimeException(e);
		}
	}
}
