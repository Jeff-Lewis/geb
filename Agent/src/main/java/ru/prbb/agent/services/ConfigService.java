package ru.prbb.agent.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author ruslan
 */
@Configuration
public class ConfigService {

	@Bean(name = "serverJobber")
	public String getServerJobber() {
		return "http://192.168.100.101:8080/Jobber/Agents";
	}

	@Bean(name = "networkInterface")
	public String getNetworkInterface() {
		return "eth0";
	}
}
