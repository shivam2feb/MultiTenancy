package com.mfsi.appbuilder.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.model.ApiJsonTemplate;
import com.mfsi.appbuilder.model.Model;
import com.mfsi.appbuilder.model.Parameter;
import com.mfsi.appbuilder.service.AppService;
import com.mfsi.appbuilder.service.PersistenceService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AppController {

	@Autowired
	AppService demoService;
	
	@Autowired
	PersistenceService persistenceService;
	
	@Value("${templateSource}")
	private String src;

	@Value("${destinationSource}")
	private String destination;
	
	//private static final Logger logger =LoggerFactory.getLogger(AppController.class);
	

	@PostMapping(value="/model")
	public void createProject(@RequestBody Model model) {
		String dest=destination+"\\"+model.getApplicationName();
		if(demoService.copyFolder(src, dest)) {
			//logger.info("Inside controller. Folder is copied to destination.");
			String entityName=model.getModelName();
			entityName=entityName.substring(0,1).toUpperCase()+entityName.substring(1);
			model.setModelName(entityName);
			Map<String,Map<String,Object>> listOfMap=demoService.prepareMapForTemplate(model);
			demoService.generateFilesFromTemplate(listOfMap,model,src,dest+"\\");
		}
	}
	
	@PostMapping(value="/createNewApi")
	public void createNewApi(@RequestBody ApiDto apiDto) {
		String dest=destination+"\\"+apiDto.getProjectName();
		
		if(demoService.copyFolder(src, dest)) {
		
			Map<String, List<ApiJsonTemplate>> listOfMap=demoService.prepareMapForTemplateV2(apiDto);
			demoService.generateFilesFromTemplateV2(listOfMap,src,dest+"\\",apiDto);
			
		}
	
		
		
		
		
		
	}

	@PostMapping(value="/createPojo")
	public void generatePojo(@RequestBody String jsonObj) {
		Map<String,Object> templateMap=new HashMap<>();
		System.out.println("Json String is "+jsonObj);
		ObjectMapper mapper=new ObjectMapper(); 
		Map<String,Object> map=new HashMap<>();
		try {
			map=mapper.readValue(jsonObj, new TypeReference<Map<String,Object>>(){});
		}catch(Exception e) {
			e.printStackTrace();
		}
		List<Parameter> listOfParam=new ArrayList<>();
		
		Parameter param1 = new Parameter();
		param1.setDataType("Long");
		param1.setColumnName("id");
		param1.setIsPrimaryKey(true);
		listOfParam.add(param1);
		templateMap.put("params", listOfParam);
		templateMap.put("EntityName", "Model");

		for(Entry<String, Object> entrySet:map.entrySet()) {
			Parameter param= new Parameter();
			param.setDataType(entrySet.getValue().getClass().getSimpleName());
			param.setColumnName(entrySet.getKey());
			listOfParam.add(param);
		}
		//generatePojo(templateMap,"");
	}
	
	@PostMapping("/createProject1")
	public boolean createProject1(@RequestBody String jsonString) {
		ObjectMapper mapper=new ObjectMapper();
		Map<String,Object> jsonObject=new HashMap<>();
		try {
			mapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
		}catch(Exception e) {
			//logger.error("Error while parsing the the JSON String {}",e);
			e.printStackTrace();
		}
		System.out.println(jsonObject.get("proojectName"));
		//persistenceService.saveProject("MyProject");
		return true;
	}
	
	
	/*
	 * @GetMapping("/createProject1") public boolean createProject1(@RequestBody
	 * String jsonString) { ObjectMapper mapper=new ObjectMapper();
	 * Map<String,Object> jsonObject=new HashMap<>(); try {
	 * mapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
	 * }catch(Exception e) {
	 * //logger.error("Error while parsing the the JSON String {}",e);
	 * e.printStackTrace(); } System.out.println(jsonObject.get("proojectName"));
	 * //persistenceService.saveProject("MyProject"); return true; }
	 */
	
	/*
	 * @GetMapping("/getProject/{projectId}") public Project
	 * getProject(@PathVariable(name="projectId") int id) { return
	 * persistenceService.getProject(id); }
	 */
}
