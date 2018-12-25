package com.mfsi.appbuilder.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfsi.appbuilder.entity.Model;
import com.mfsi.appbuilder.entity.Parameter;
import com.mfsi.appbuilder.service.AppService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@RestController
public class AppController {

	@Autowired
	AppService demoService;
	
	@Value("${templateSource}")
	private String src;

	@Value("${destinationSource}")
	private String dest;

	@RequestMapping(value="/model",method=RequestMethod.POST)
	public void saveEntity(@RequestBody Model model) {
		if(demoService.copyFolder(src, dest+"\\"+model.getApplicationName())) {
			System.out.println("Inside controller. Folder is copied to destination.");
			Map<String,Map<String,Object>> listOfMap=demoService.prepareMapForTemplate(model);
			demoService.generateFileFromTemplate(listOfMap.get("entityMap"), "EntityTemplate.ftl", model.getModelName());
			demoService.generateFileFromTemplate(listOfMap.get("genericMap"), "ControllerTemplate.ftl", model.getModelName());
			demoService.generateFileFromTemplate(listOfMap.get("genericMap"), "RepositoryTemplate.ftl", model.getModelName());
			demoService.generateFileFromTemplate(listOfMap.get("genericMap"), "ServiceImplTemplate.ftl", model.getModelName());
			demoService.generateFileFromTemplate(listOfMap.get("genericMap"), "ServiceTemplate.ftl", model.getModelName());
		}
	}

	@RequestMapping(value="/createPojo", method=RequestMethod.POST)
	public void generatePojo(@RequestBody String jsonObj) {
		Map<String,Object> templateMap=new HashMap<String,Object>();
		System.out.println("Json String is "+jsonObj);
		ObjectMapper mapper=new ObjectMapper(); 
		Map<String,Object> map=new HashMap<>();
		try {
			map=mapper.readValue(jsonObj, new TypeReference<Map<String,Object>>(){});
		}catch(Exception e) {
			e.printStackTrace();
		}
		List<Parameter> listOfParam=new ArrayList<>();
		listOfParam.add(new Parameter("Long","id","Unique_Id",true));
		templateMap.put("params", listOfParam);
		templateMap.put("EntityName", "Model");

		for(String str:map.keySet()) {
			Parameter param= new Parameter();
			//param.setColumnName("Col_"+str);
			param.setDataType(map.get(str).getClass().getSimpleName());
			param.setName(str);
			System.out.println("Value of "+str+" is "+map.get(str)+" -"+map.get(str).getClass().getSimpleName());
			listOfParam.add(param);
		}
		//generatePojo(templateMap,"");
	}

	
}
