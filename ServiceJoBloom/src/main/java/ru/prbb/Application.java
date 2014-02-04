/**
 * 
 */
package ru.prbb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.service.BloombergServices;

/**
 * @author RBr
 * 
 */
@Controller
@Configuration
@ComponentScan
@EnableAutoConfiguration
//@EnableScheduling
public class Application {
	@Autowired
	private BloombergServices bs;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Service Jobber and Bloomberg.";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
