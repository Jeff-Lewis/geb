package ru.prbb.agent;

import java.awt.SystemTray;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActiveAgentAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActiveAgentAppApplication.class, args);
    }

    @PostConstruct
    public void init() {
    	if (SystemTray.isSupported())
    		System.out.println("SystemTray is supported");
    	Logger log = LoggerFactory.getLogger(ActiveAgentAppApplication.class);
    	log.info("LoggerFactory out");
	}
}
