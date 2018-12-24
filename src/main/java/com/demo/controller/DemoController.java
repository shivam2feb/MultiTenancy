package com.demo.controller;

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

import com.demo.entity.Model;
import com.demo.entity.Parameter;
import com.demo.service.DemoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;

@RestController
public class DemoController {

	@Autowired
	DemoService demoService;
	
	@Value("${templateSource}")
	private String src;

	@Value("${destinationSource}")
	private String dest;

	@RequestMapping(value="/model",method=RequestMethod.POST)
	public void saveEntity(@RequestBody Model model) {
		System.out.println("value of src is fd "+src+" value of dest is "+dest);
		if(demoService.copyFolder(src, dest+"\\"+model.getApplicationName())) {
			System.out.println("Inside controller. Folder is copied to destination.");
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

	public void generatePojo(Map<String,Object> map,String templateName,String fileName) {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		try{
			cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")+"\\src\\main\\resources\\templates"));
			cfg.setDefaultEncoding("UTF-8");
			Template template=cfg.getTemplate(templateName);
			Writer writer=new FileWriter(new File("C:\\Users\\Shivam.Sharma\\Desktop\\FTL\\GeneratedFiles\\"+fileName+"-"+
					String.valueOf(new Date().getTime()).substring(9)+".java"));
			template.process(map, writer);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
