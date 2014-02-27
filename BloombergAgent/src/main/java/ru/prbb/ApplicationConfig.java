/**
 * 
 */
package ru.prbb;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author RBr
 */
@Configuration
//@EnableTransactionManagement
@ComponentScan
@PropertySource("classpath:jdbc.properties")
class ApplicationConfig {

	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {
//		BasicDataSource ds = new BasicDataSource();
//		ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//		ds.setUrl(env.getProperty("jdbc.url"));
//		ds.setUsername(env.getProperty("jdbc.username"));
//		ds.setPassword(env.getProperty("jdbc.password"));
//		return ds;
		return null;
	}
/*
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource());
		return txManager;
	}
*/
}