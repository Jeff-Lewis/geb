/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.tasks.TaskAtrLoad;
import ru.prbb.middleoffice.domain.tasks.TaskCashFlowLoad;
import ru.prbb.middleoffice.domain.tasks.TaskCashFlowLoadNew;
import ru.prbb.middleoffice.domain.tasks.TaskHistoricalDataRequest;
import ru.prbb.middleoffice.domain.tasks.TaskRateCouponLoad;
import ru.prbb.middleoffice.domain.tasks.TaskReferenceDataRequest;
import ru.prbb.middleoffice.domain.tasks.TaskValuesLoad;
import ru.prbb.middleoffice.services.TasksService;

/**
 * @author RBr
 */
@Service
public class BloombergServicesMTasksImpl implements BloombergServicesM {

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
	public List<Map<String, Object>> executeCashFlowLoad(Map<String, Long> _ids, Map<String, String> _dates) {
		List<String> ids = new ArrayList<>(_ids.size());
		for (Entry<String, Long> entry : _ids.entrySet()) {
			Long id = entry.getValue();
			String security = entry.getKey();
			ids.add(id + ";" + security);
		}

		List<String> dates = new ArrayList<>(_dates.size());
		for (Entry<String, String> entry : _dates.entrySet()) {
			String date = entry.getValue();
			String security = entry.getKey();
			dates.add(date + ";" + security);
		}

		try {
			TaskCashFlowLoad task = new TaskCashFlowLoad("Загрузка дат погашений");
			task.setIds(ids.toArray(new String[ids.size()]));
			task.setDates(dates.toArray(new String[dates.size()]));
			
			tasks.execute(task);
			
			return task.getResult();
		} catch (Exception e) {
			log.error("CashFlowLoad", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> executeCashFlowLoadNew(Map<String, Long> _ids, Map<String, String> _dates) {
		List<String> ids = new ArrayList<>(_ids.size());
		for (Entry<String, Long> entry : _ids.entrySet()) {
			Long id = entry.getValue();
			String security = entry.getKey();
			ids.add(id + ";" + security);
		}

		List<String> dates = new ArrayList<>(_dates.size());
		for (Entry<String, String> entry : _dates.entrySet()) {
			String date = entry.getValue();
			String security = entry.getKey();
			dates.add(date + ";" + security);
		}

		try {
			TaskCashFlowLoadNew task = new TaskCashFlowLoadNew("Загрузка дат погашений(New)");
			task.setIds(ids.toArray(new String[ids.size()]));
			task.setDates(dates.toArray(new String[dates.size()]));
			
			tasks.execute(task);
			
			return task.getResult();
		} catch (Exception e) {
			log.error("CashFlowLoadNew", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> executeValuesLoad(Map<String, Long> _ids) {
		List<String> ids = new ArrayList<>(_ids.size());
		for (Entry<String, Long> entry : _ids.entrySet()) {
			Long id = entry.getValue();
			String security = entry.getKey();
			ids.add(id + ";" + security);
		}

		try {
			TaskValuesLoad task = new TaskValuesLoad("Загрузка номинала");
			task.setIds(ids.toArray(new String[ids.size()]));
			
			tasks.execute(task);
			
			return task.getResult();
		} catch (Exception e) {
			log.error("ValuesLoad", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> executeRateCouponLoad(Map<String, Long> _ids) {
		List<String> ids = new ArrayList<>(_ids.size());
		for (Entry<String, Long> entry : _ids.entrySet()) {
			Long id = entry.getValue();
			String security = entry.getKey();
			ids.add(id + ";" + security);
		}

		try {
			TaskRateCouponLoad task = new TaskRateCouponLoad("Загрузка ставки по купонам");
			task.setIds(ids.toArray(new String[ids.size()]));
			
			tasks.execute(task);
			
			return task.getResult();
		} catch (Exception e) {
			log.error("RateCouponLoad", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> executeAtrLoad(Date startDate, Date endDate, String[] securities,
			String maType, Integer taPeriod, String period, String calendar) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			TaskAtrLoad task = new TaskAtrLoad("Загрузка ATR");
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
			log.error("AtrLoad", e);
			throw new RuntimeException(e);
		}
	}
}
