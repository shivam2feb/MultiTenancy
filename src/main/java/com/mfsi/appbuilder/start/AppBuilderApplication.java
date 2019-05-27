package com.mfsi.appbuilder.start;

import com.mfsi.appbuilder.model.Parameter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@ComponentScan(basePackages="com.mfsi")
@EntityScan(basePackages="com.mfsi")
@EnableMongoRepositories(basePackages="com.mfsi.appbuilder.repository")
@EnableMongoAuditing
public class AppBuilderApplication {

	//private static final Logger logger =LoggerFactory.getLogger(AppBuilderApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AppBuilderApplication.class, args);
		System.out.println("Inside Starter Application class");
		//logger.error("Inside main method of AppBuilderApplication class");
	}

	public static void prepareMap(Map<String,Object> map) {
		List<Parameter> listOfParam=new ArrayList<>();
		Parameter idParam= new Parameter();
		idParam.setIsId(Boolean.TRUE);
		idParam.setName("id");
		idParam.setDataType("Long");
		idParam.setColumnName("ModelID");
		listOfParam.add(idParam);
		for(int i=1;i<13;i++) {
			Parameter param= new Parameter();
			if(i<5)
				param.setDataType("int");
			else
				param.setDataType("String");
			param.setName("param"+i);
			listOfParam.add(param);
			if(Arrays.asList(1,2,4,9).contains(i)) {
				param.setColumnName("Column_Name_"+i);
			}
		}
		map.put("params", listOfParam);
	}
}
