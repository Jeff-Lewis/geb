/**
 * 
 */
package ru.prbb.jobber.services;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ru.prbb.jobber.repo.BloombergServices;

/**
 * Запланированные задачи Jobber
 * 
 * @author RBr
 */
@Configuration
@EnableScheduling
public class ScheduledTasks {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServices bs;

	@Scheduled(cron = "0 00 3 * * ?")
	public void taskBdsLoad() {
		log.info("task BdsLoad");
		bs.executeBdsLoad();
	}

	@Scheduled(cron = "0 15 4 * * ?")
	public void taskFuturesLoad() {
		log.info("task FuturesLoad");
		bs.executeFuturesLoad();
	}

	@Scheduled(cron = "0 00 5 * * ?")
	public void taskQuotesLoad() {
		log.info("task QuotesLoad");
		bs.executeQuotesLoad(yesterday());
	}

	@Scheduled(cron = "0 10 5 * * ?")
	public void taskAtrLoad() {
		log.info("task AtrLoad");
		bs.executeAtrLoad(yesterday());
	}

	@Scheduled(cron = "0 00 6 * * ?")
	public void taskBdpOverrideLoad() {
		log.info("task BdpOverrideLoad");
		bs.executeBdpOverrideLoad();
	}

	@Scheduled(cron = "0 00 7 * * ?")
	public void taskHistDataLoad() {
		log.info("task HistDataLoad");
		bs.executeHistDataLoad(yesterday());
	}

	@Scheduled(cron = "0 00 8 * * ?")
	public void taskCurrenciesDataLoad() {
		log.info("task CurrenciesDataLoad");
		bs.executeCurrenciesDataLoad(yesterday());
	}

	@Scheduled(cron = "0 59 11-18 * * ?")
	public void taskBondsLoad() {
		log.info("task BondsLoad");
		bs.executeBondsLoad();
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
