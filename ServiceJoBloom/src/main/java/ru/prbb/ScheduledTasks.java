/**
 * 
 */
package ru.prbb;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.service.BloombergServices;

/**
 * Запланированные задачи Jobber
 * 
 * @author RBr
 * 
 */
@Service
public class ScheduledTasks
{
	@Autowired
	private BloombergServices bs;

	/**
	 * Загрузка ATR
	 */
	@Scheduled(cron = "0 10 05 * * ?")
	public void taskAtrLoad() {
		bs.executeAtrLoad(yesterday());
	}

	@Scheduled(cron = "0 00 06 * * ?")
	public void taskBdpOverrideLoad() {
		bs.executeBdpOverrideLoad();
	}

	@Scheduled(cron = "0 00 03 * * ?")
	public void taskBdsLoad() {
		bs.executeBdsLoad();
	}

	@Scheduled(cron = "0 59 11-18 * * ?")
	public void taskBondsLoad() {
		bs.executeBondsLoad();
	}

	@Scheduled(cron = "0 00 07 * * ?")
	public void taskHistDataLoad() {
		bs.executeHistDataLoad(yesterday());
	}

	@Scheduled(cron = "0 00 05 * * ?")
	public void taskQuotesLoad() {
		bs.executeQuotesLoad(yesterday());
	}

	@Scheduled(cron = "0 15 04 * * ?")
	public void taskFuturesLoad() {
		bs.executeFuturesLoad();
	}

	private Date yesterday() {
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DAY_OF_YEAR, -1);
		return c.getTime();
	}
}
