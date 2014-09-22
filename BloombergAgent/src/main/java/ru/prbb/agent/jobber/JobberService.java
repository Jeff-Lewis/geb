package ru.prbb.agent.jobber;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.agent.services.BloombergServices;

abstract class JobberService {

	protected Log log = LogFactory.getLog(getClass());
	
	private SimpleDateFormat bloombergDateFormat = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	protected BloombergServices bs;

	@Autowired
	protected DBManager dbm;

	protected Date yesterday() {
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.DAY_OF_YEAR, -1);
		return c.getTime();
	}

	protected String[] toArray(String... args) {
		return args;
	}

	protected String[] toArray(Collection<String> c) {
		return c.toArray(new String[c.size()]);
	}

	protected String toBloomberg(Date date) {
		return bloombergDateFormat.format(date);
	}

	public abstract void execute();

}
