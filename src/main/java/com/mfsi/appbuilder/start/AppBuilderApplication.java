package com.mfsi.appbuilder.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages="com.mfsi.appbuilder")
@EnableMongoAuditing
public class AppBuilderApplication {

	//private static final Logger logger =LoggerFactory.getLogger(AppBuilderApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AppBuilderApplication.class, args);
		System.out.println("Inside Starter Application class");
		//logger.error("Inside main method of AppBuilderApplication class");
	}

}
