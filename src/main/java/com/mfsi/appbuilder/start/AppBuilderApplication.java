package com.mfsi.appbuilder.start;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import com.mfsi.appbuilder.entity.Parameter;

@SpringBootApplication
@ComponentScan(basePackages="com.demo")
@EntityScan(basePackages="com.demo")
public class AppBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppBuilderApplication.class, args);
		/*Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("EntityName", "Model");
		paramMap.put("idType", "Long");
		DemoController demo=new DemoController();*/
		/*demo.generatePojo(paramMap, "ControllerTemplate.ftl","ModelController");
		demo.generatePojo(paramMap, "RepositoryTemplate.ftl","ModelRepository");
		demo.generatePojo(paramMap, "ServiceImplTemplate.ftl","ModelServiceImpl");
		demo.generatePojo(paramMap, "ServiceTemplate.ftl","ModelService");*/
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
