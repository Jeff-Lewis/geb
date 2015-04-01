package ru.prbb.agent.services;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author RBr
 */
@Service
public class AutoRestartService {

	private final Log log = LogFactory.getLog(getClass());

	private final File file = new File("active_agent.jar.new");

	@Autowired
	private ApplicationContext context;

	/**
	 * Проверка обновления
	 */
	@Scheduled(fixedDelay = 1000 * 20)
	public void execute() {
		log.debug("Check new jar");

		if (file.exists()) {
			log.info("Exit application");
			SpringApplication.exit(context);
		}
	}

}
