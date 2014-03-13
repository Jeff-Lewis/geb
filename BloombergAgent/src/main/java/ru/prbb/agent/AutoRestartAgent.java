package ru.prbb.agent;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 
 * @author RBr
 *
 */
@Service
@EnableScheduling
public class AutoRestartAgent {

	private final Log log = LogFactory.getLog(getClass());

	private final File file = new File("agent.jar.new");

	/**
	 * Ежеменутная проверка обновления
	 */
	@Scheduled(fixedDelay = 1000 * 20)
	public void execute() {
		log.trace("test restart");

		if (file.exists()) {
			log.info("reboot application");
			System.exit(0);
		}
	}

}
