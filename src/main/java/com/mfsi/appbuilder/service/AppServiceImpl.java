package com.mfsi.appbuilder.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.model.ApiJsonTemplate;
import com.mfsi.appbuilder.model.Model;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class AppServiceImpl implements AppService{

	@Value("classpath:templateProject")
	private String src;

	@Value("${destinationSource}")
	private String dest;

	static final String GENERIC_MAP="genericMap";
	static final String BASE_JAVA_FOLDER="src\\main\\java\\";
	static final String BASE_PACKAGE="com\\app\\";
	
	

	
	public String createMethodName(List<ApiJsonTemplate> getParams) {
		StringBuilder methodName = new StringBuilder("");
		int curr = 0;
		for (ApiJsonTemplate param : getParams) {
			
			if(param.getRelationship().equalsIgnoreCase("default"))	
				methodName.append(param.getColumnName());
			else
				methodName.append(param.getEntityName()).append(param.getColumnName());
			
			if(curr != getParams.size()-1) methodName.append("And");
			
			curr++;
		}
		
		return methodName.toString();
	}
	
	public boolean copyFolder(String srcPath, String dscPath) {
		File srcFolder = null;
		try {
			srcFolder = new ClassPathResource("templateProject").getFile();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File dscFolder=new File(dscPath);
		try {
			copyFolder(srcFolder,dscFolder);
			System.out.println("Folder is copied");
			return true;
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void copyFolder(File src,File dest) throws IOException {

		if(src.isDirectory()) {
			if(!dest.exists()) {
				dest.mkdirs();
				System.out.println("Directory is created having path "+dest.getAbsolutePath());
			}
			String[] files=src.list();
			for(String file:files) {
				File srcFile=new File(src,file);
				File destFile=new File(dest,file);
				copyFolder(srcFile,destFile);
			}
		}else {
			InputStream is=new FileInputStream(src);
			OutputStream out=new FileOutputStream(dest);
			byte[] buffer=new byte[1024];
			int length;
			while((length=is.read(buffer))>0) {
				out.write(buffer, 0, length);
			}
			is.close();
			out.close();
			
			System.out.println("File copied from "+src+ " to "+dest);
		}
	}

	public Map<String,Map<String, Object>> prepareMapForTemplate(Model model){
		Map<String,Map<String,Object>> mapsOfTemplate=new HashMap<>();
		Map<String,Object> entityMap=new HashMap<>();
		entityMap.put("params", model.getParameterList());
		entityMap.put("tableName", model.getTableName());
		entityMap.put("EntityName", model.getModelName());
		mapsOfTemplate.put("entityMap", entityMap);
		Map<String,Object> genericMap=new HashMap<>();
		genericMap.put("EntityName", model.getModelName());
		model.getParameterList().forEach(parameter->{
			if(parameter.getIsPrimaryKey()) {
				genericMap.put("idType",parameter.getDataType());
			}
		});
		mapsOfTemplate.put("genericMap", genericMap);
		return mapsOfTemplate;
	}

	
	public Map<String,List<ApiJsonTemplate>> prepareEntitiesMap(List<ApiJsonTemplate> jsonString){
		Map<String,List<ApiJsonTemplate>> entitiesMap = new HashMap<>();
		
		for (Iterator iterator = jsonString.iterator(); iterator.hasNext();) {
			ApiJsonTemplate apiJsonTemplate = (ApiJsonTemplate) iterator.next();
			prepareApiJsonTemplateObj(entitiesMap, apiJsonTemplate);	
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(entitiesMap));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entitiesMap;
	}
	
	public Map<String,List<ApiJsonTemplate>> prepareApiJsonTemplateObj(Map<String,List<ApiJsonTemplate>> map,ApiJsonTemplate apiJsonTemplate) {
		
		//check whether the object is a simple attribute or a Has A relationship with diff entity.
		if(apiJsonTemplate.getDataType().getClass().getSimpleName().equals("String")) {
			String entityName = apiJsonTemplate.getEntityName();
			if(map.get(entityName) == null || map.get(entityName).size() == 0) {
				// fetch the preList and then insert into in the the map
				List<ApiJsonTemplate> newList = new ArrayList<>();
				newList.add(apiJsonTemplate);
				map.put(entityName, newList);
			}else {
				List<ApiJsonTemplate> preList = map.get(entityName);
				preList.add(apiJsonTemplate);
				map.put(entityName, preList);
			}
		}else {
			//add it to list and send it to recursion.
			ObjectMapper mapper = new ObjectMapper();
			List<ApiJsonTemplate> nestedList = (List<ApiJsonTemplate>)apiJsonTemplate.getDataType();
			
			String entityName = apiJsonTemplate.getEntityName();
			if(map.get(entityName) == null || map.get(entityName).size() == 0) {
				// fetch the preList and then insert into in the the map
				List<ApiJsonTemplate> newList = new ArrayList<>();
				apiJsonTemplate.setDataType(apiJsonTemplate.getColumnName().substring(0,1).toUpperCase()+apiJsonTemplate.getColumnName().substring(1));
				newList.add(apiJsonTemplate);
				map.put(entityName, newList);
			}else {
				List<ApiJsonTemplate> preList = map.get(entityName);
				apiJsonTemplate.setDataType(apiJsonTemplate.getColumnName().substring(0,1).toUpperCase()+apiJsonTemplate.getColumnName().substring(1));
				preList.add(apiJsonTemplate);
				map.put(entityName, preList);
			}

			for (Iterator iterator = nestedList.iterator(); iterator.hasNext();) {
				ApiJsonTemplate nestedApiJsonTemplate = mapper.convertValue(iterator.next(), ApiJsonTemplate.class);
				prepareApiJsonTemplateObj(map, nestedApiJsonTemplate);
			}	
		}
		return map;
	}
	
	
	public void generateFileFromTemplate(Map<String,Object> map,String templateName,String location,String fileName) {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		try{
			cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")+"\\src\\main\\resources\\templates"));
			cfg.setDefaultEncoding("UTF-8");
			Template template=cfg.getTemplate(templateName);
			File file=new File(location+fileName+".java");
			file.createNewFile();
			Writer writer=new FileWriter(file);
			template.process(map, writer);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generateFileFromTemplateV2(Map<String,Object> map,String templateName,String location,String fileName) {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		try{
			cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")+"\\src\\main\\resources\\templates"));
			cfg.setDefaultEncoding("UTF-8");
			Template template=cfg.getTemplate(templateName);
			File file=new File(location);
			file.mkdirs();
			file=new File(location+fileName+".java");
			file.createNewFile();
			Writer writer=new FileWriter(file);
			template.process(map, writer);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void generateFilesFromTemplate(Map<String,Map<String,Object>> listOfMap,Model model,String src,String dest) {
		final String modelName=model.getModelName();
		generateFileFromTemplate(listOfMap.get("entityMap"), "EntityTemplate.ftl",dest+BASE_JAVA_FOLDER+
				BASE_PACKAGE+"entity\\",modelName);
		generateFileFromTemplate(listOfMap.get(GENERIC_MAP), "ControllerTemplate.ftl",dest+BASE_JAVA_FOLDER+
				BASE_PACKAGE+"controller\\", modelName+"Controller");
		generateFileFromTemplate(listOfMap.get(GENERIC_MAP), "RepositoryTemplate.ftl",dest+BASE_JAVA_FOLDER+
				BASE_PACKAGE+"repository\\", modelName+"Repository");
		generateFileFromTemplate(listOfMap.get(GENERIC_MAP), "ServiceTemplate.ftl",dest+BASE_JAVA_FOLDER+
				BASE_PACKAGE+"service\\", modelName+"Service");
		generateFileFromTemplate(listOfMap.get(GENERIC_MAP), "ServiceImplTemplate.ftl",dest+BASE_JAVA_FOLDER+
				BASE_PACKAGE+"service\\", modelName+"ServiceImpl");
	}
	
	public void generateFilesFromTemplateV2(Map<String, List<ApiJsonTemplate>> listOfMap,String src,String dest,API apiDto,String getApiMethodName) {
		
		Set<String> entities = listOfMap.keySet();
		
		// iterating over json string for fetching all entities.
		for (String entityName : entities) {
			Map<String,Object> entityMap=new HashMap<>();
			entityMap.put("params", listOfMap.get(entityName));
			entityMap.put("tableName", entityName);
			entityMap.put("EntityName", entityName);
			entityMap.put("ApiName", apiDto.getApiName());
			
			generateFileFromTemplateV2(entityMap, "EntityTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"entity\\",entityName);
		}
		Map<String,Object> entityMap=new HashMap<>();
		entityMap.put("EntityName", apiDto.getMainEntityName());
		entityMap.put("ApiName", apiDto.getApiName());
		entityMap.put("ApiUrl", apiDto.getApiUrl());
		entityMap.put("idType", apiDto.getMainEntityIdType());
		entityMap.put("params", apiDto.getGetParams());
		entityMap.put("MethodName", getApiMethodName);
		
		System.out.println(entityMap);
		
		if(apiDto.getApiType().equalsIgnoreCase("post")) {
		
			generateFileFromTemplateV2(entityMap, "PostControllerTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"controller\\", apiDto.getApiName()+"Controller");
			
			
			generateFileFromTemplateV2(entityMap, "PostServiceTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"service\\", apiDto.getApiName()+"Service");
			generateFileFromTemplateV2(entityMap, "PostServiceImplTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"service\\", apiDto.getApiName()+"ServiceImpl");
			
			generateFileFromTemplateV2(entityMap, "PostRepositoryTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"repository\\", apiDto.getApiName()+"Repository");
		}else if(apiDto.getApiType().equalsIgnoreCase("put")) {
			generateFileFromTemplateV2(entityMap, "PutControllerTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"controller\\", apiDto.getApiName()+"Controller");
			
			
			generateFileFromTemplateV2(entityMap, "PutServiceTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"service\\", apiDto.getApiName()+"Service");
			generateFileFromTemplateV2(entityMap, "PutServiceImplTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"service\\", apiDto.getApiName()+"ServiceImpl");
			
			generateFileFromTemplateV2(entityMap, "PutRepositoryTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"repository\\", apiDto.getApiName()+"Repository");
			
		}else if(apiDto.getApiType().equalsIgnoreCase("get")) {
			generateFileFromTemplateV2(entityMap, "GetControllerTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"controller\\", apiDto.getApiName()+"Controller");
			
			
			generateFileFromTemplateV2(entityMap, "GetServiceTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"service\\", apiDto.getApiName()+"Service");
			generateFileFromTemplateV2(entityMap, "GetServiceImplTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"service\\", apiDto.getApiName()+"ServiceImpl");
			
			generateFileFromTemplateV2(entityMap, "GetRepositoryTemplate.ftl",dest+BASE_JAVA_FOLDER+
					BASE_PACKAGE+apiDto.getApiName()+"\\"+"repository\\", apiDto.getApiName()+"Repository");
			
		}else if(apiDto.getApiType().equalsIgnoreCase("delete")) {
			//TODO
			
		}
		
		/*
		 * generateFileFromTemplate(listOfMap.get("entityMap"),
		 * "EntityTemplate.ftl",dest+BASE_JAVA_FOLDER+
		 * BASE_PACKAGE+"entity\\",modelName);
		 * generateFileFromTemplate(listOfMap.get(GENERIC_MAP),
		 * "ControllerTemplate.ftl",dest+BASE_JAVA_FOLDER+
		 * BASE_PACKAGE+"controller\\", modelName+"Controller");
		 * generateFileFromTemplate(listOfMap.get(GENERIC_MAP),
		 * "RepositoryTemplate.ftl",dest+BASE_JAVA_FOLDER+
		 * BASE_PACKAGE+"repository\\", modelName+"Repository");
		 * generateFileFromTemplate(listOfMap.get(GENERIC_MAP),
		 * "ServiceTemplate.ftl",dest+BASE_JAVA_FOLDER+
		 * BASE_PACKAGE+"service\\", modelName+"Service");
		 * generateFileFromTemplate(listOfMap.get(GENERIC_MAP),
		 * "ServiceImplTemplate.ftl",dest+BASE_JAVA_FOLDER+
		 * BASE_PACKAGE+"service\\", modelName+"ServiceImpl");
		 */
	}
	
	public void compileProject(String fileLocation) {
		
	}
	
	public static void runProcess(String command) throws IOException{
		Process process=Runtime.getRuntime().exec(command);
	}
}
