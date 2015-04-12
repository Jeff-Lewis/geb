/**
 * 
 */
package ru.prbb.jobber.repo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.AtrLoadDataItem;
import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.domain.tasks.TaskAtrLoad;
import ru.prbb.jobber.domain.tasks.TaskBdpOverrideLoad;
import ru.prbb.jobber.domain.tasks.TaskBdsRequest;
import ru.prbb.jobber.domain.tasks.TaskHistoricalDataRequest;
import ru.prbb.jobber.domain.tasks.TaskReferenceDataRequest;
import ru.prbb.jobber.services.TasksService;

/**
 * @author RBr
 */
@Service
public class BloombergServicesJTasksImpl implements BloombergServicesJ {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private TasksService tasks;

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
	public Map<String, Map<String, String>> executeReferenceDataRequest(String name,
			String[] securities, String[] fields) {
		try {
			TaskReferenceDataRequest task = new TaskReferenceDataRequest(name);
			task.setSecurities(securities);
			task.setFields(fields);

			tasks.execute(task);

			return task.getResult();
		} catch (Exception e) {
			log.error("ReferenceData", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			TaskHistoricalDataRequest task = new TaskHistoricalDataRequest(name);
			task.setDateStart(sdf.format(startDate));
			task.setDateEnd(sdf.format(endDate));
			task.setSecurities(securities);
			task.setFields(fields);

			tasks.execute(task);

			return task.getResult();
		} catch (Exception e) {
			log.error("HistoricalDataRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields, String[] currencies) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			TaskHistoricalDataRequest task = new TaskHistoricalDataRequest(name);
			task.setDateStart(sdf.format(startDate));
			task.setDateEnd(sdf.format(endDate));
			task.setSecurities(securities);
			task.setFields(fields);
			task.setCurrencies(currencies);

			tasks.execute(task);

			return task.getResult();
		} catch (Exception e) {
			log.error("HistoricalDataRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<AtrLoadDataItem> executeAtrLoad(String name, Date startDate, Date endDate, String[] securities,
			String maType, Integer taPeriod, String period, String calendar) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			TaskAtrLoad task = new TaskAtrLoad(name);
			task.setDateStart(sdf.format(startDate));
			task.setDateEnd(sdf.format(endDate));
			task.setSecurities(securities);
			task.setMaType(maType);
			task.setTaPeriod(taPeriod);
			task.setPeriod( period);
			task.setCalendar(calendar);
			
			tasks.execute(task);
			
			return task.getResult();
		} catch (Exception e) {
			log.error("LoadAtrRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, String>> executeBdpOverrideLoad(String name, List<SecForJobRequest> securities) {
		try {
			Set<String> currencies = new HashSet<>();
			List<String> cursecs = new ArrayList<>(securities.size());
			for (SecForJobRequest security : securities) {
				currencies.add(security.iso);
				String cursec = security.iso + security.code;
				cursecs.add(cursec);
			}
			
			TaskBdpOverrideLoad task = new TaskBdpOverrideLoad(name);
			task.setSecurities(cursecs.toArray(new String[cursecs.size()]));
			task.setCurrencies(currencies.toArray(new String[currencies.size()]));
			
			tasks.execute(task);
			
			return task.getResult();
		} catch (Exception e) {
			log.error("LoadBdpOverrideRequest", e);
			throw new RuntimeException(e);
		}
	}
}
