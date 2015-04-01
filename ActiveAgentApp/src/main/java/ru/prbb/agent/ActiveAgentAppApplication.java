package ru.prbb.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableScheduling
public class ActiveAgentAppApplication {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Bean
	public TaskScheduler getTaskScheduler() {
		ThreadPoolTaskScheduler bean = new ThreadPoolTaskScheduler();
		bean.setPoolSize(15);
		bean.setWaitForTasksToCompleteOnShutdown(true);
		log.debug("Create " + bean.getClass().getSimpleName());
		return bean;
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper bean = new ObjectMapper();
		//bean.setDateFormat(new SimpleDateFormat("yyyyMMdd"));
		log.debug("Create " + bean.getClass().getSimpleName());
		return bean;
	}

	public static void main(String[] args) {
		SpringApplication.run(ActiveAgentAppApplication.class, args);
	}
}
