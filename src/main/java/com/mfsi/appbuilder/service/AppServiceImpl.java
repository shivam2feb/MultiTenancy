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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.master.document.API;
import com.mfsi.appbuilder.master.document.Project;
import com.mfsi.appbuilder.model.ApiJsonTemplate;
import com.mfsi.appbuilder.model.Model;
import com.mfsi.appbuilder.tenant.service.TenantAPIService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class AppServiceImpl implements AppService {
	
	@Autowired
	private PersistenceService persistenceService;

	@Autowired
	private TenantAPIService tenantAPIService;

	@Value("classpath:templateProject")
	private String src;

	@Value("${destinationSource}")
	private String dest;

	public static final String GENERIC_MAP = "genericMap";
	public static final String BASE_FOLDER = "src" + File.separator + "main" + File.separator;
	public static final String BASE_JAVA_FOLDER = BASE_FOLDER + "java" + File.separator;
	public static final String BASE_RESOURCES_FOLDER = BASE_FOLDER + "resources" + File.separator;
	public static final String BASE_PACKAGE = "com" + File.separator + "app" + File.separator;

	/**
	 * used to make method name dynamically to be used in repository call for
	 * fetching data.
	 * 
	 * @author rohan
	 * @param getParams - list of params.
	 * @return string of method name to be used in repository call.
	 */
	public String createMethodName(List<ApiJsonTemplate> getParams) {
		StringBuilder methodName = new StringBuilder("");
		int curr = 0;
		for (ApiJsonTemplate param : getParams) {

			if (param.getRelationship().equalsIgnoreCase("default"))
				methodName.append(param.getColumnName());
			else
				methodName.append(param.getEntityName()).append(param.getColumnName());

			if (curr != getParams.size() - 1)
				methodName.append("And");

			curr++;
		}

		return methodName.toString();
	}

	public boolean copyFolder(String srcPath, String dscPath,Boolean wantedSecurity) {
		File srcFolder = null;
		try {
			System.out.println(wantedSecurity);
			if(wantedSecurity)
				srcFolder = new ClassPathResource("templateProject"+ File.separator + "withSecurity").getFile();
			else
				srcFolder = new ClassPathResource("templateProject"+ File.separator + "withoutSecurity").getFile();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		File dscFolder = new File(dscPath);
		try {
			copyFolder(srcFolder, dscFolder);
			// System.out.println("Folder is copied");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdirs();
				// System.out.println("Directory is created having path
				// "+dest.getAbsolutePath());
			}
			String[] files = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile, destFile);
			}
		} else {
			InputStream is = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			is.close();
			out.close();

			System.out.println("File copied from " + src + " to " + dest);
		}
	}

	public Map<String, Map<String, Object>> prepareMapForTemplate(Model model) {
		Map<String, Map<String, Object>> mapsOfTemplate = new HashMap<>();
		Map<String, Object> entityMap = new HashMap<>();
		entityMap.put("params", model.getParameterList());
		entityMap.put("tableName", model.getTableName());
		entityMap.put("EntityName", model.getModelName());
		mapsOfTemplate.put("entityMap", entityMap);
		Map<String, Object> genericMap = new HashMap<>();
		genericMap.put("EntityName", model.getModelName());
		model.getParameterList().forEach(parameter -> {
			if (parameter.getIsPrimaryKey()) {
				genericMap.put("idType", parameter.getDataType());
			}
		});
		mapsOfTemplate.put("genericMap", genericMap);
		return mapsOfTemplate;
	}

	/**
	 * Loop over freetext json to fetch out entities to be created dynamically.
	 * 
	 * @author rohan
	 * @param List of Api json dto's
	 * @return entities map
	 */
	public Map<String, List<ApiJsonTemplate>> prepareEntitiesMap(List<ApiJsonTemplate> jsonString) {
		Map<String, List<ApiJsonTemplate>> entitiesMap = new HashMap<>();

		for (Iterator<ApiJsonTemplate> iterator = jsonString.iterator(); iterator.hasNext();) {
			ApiJsonTemplate apiJsonTemplate = iterator.next();
			prepareApiJsonTemplateObj(entitiesMap, apiJsonTemplate);
		}

		return entitiesMap;
	}

	/**
	 * used to parse json and push it into entities map for making entities
	 * dynamically.
	 * 
	 * @author rohan
	 * @param entitiesMap     to be finally made.
	 * @param apiJsonTemplate to be parsed.
	 * @return final entitiesMap.
	 */
	public Map<String, List<ApiJsonTemplate>> prepareApiJsonTemplateObj(Map<String, List<ApiJsonTemplate>> entitiesMap,
			ApiJsonTemplate apiJsonTemplate) {

		// check whether the object is a simple attribute or a Has A relationship with
		// diff entity.
		if (apiJsonTemplate.getDataType().getClass().getSimpleName().equals("String")) {
			String entityName = apiJsonTemplate.getEntityName();
			if (entitiesMap.get(entityName) == null || entitiesMap.get(entityName).size() == 0) {
				// fetch the preList and then insert into in the the map
				List<ApiJsonTemplate> newList = new ArrayList<>();
				newList.add(apiJsonTemplate);
				entitiesMap.put(entityName, newList);
			} else {
				List<ApiJsonTemplate> preList = entitiesMap.get(entityName);
				preList.add(apiJsonTemplate);
				entitiesMap.put(entityName, preList);
			}
		} else {
			// add it to list and send it to recursion.
			ObjectMapper mapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			List<ApiJsonTemplate> nestedList = (List<ApiJsonTemplate>) apiJsonTemplate.getDataType();

			String entityName = apiJsonTemplate.getEntityName();
			if (entitiesMap.get(entityName) == null || entitiesMap.get(entityName).size() == 0) {
				// fetch the preList and then insert into in the the map
				List<ApiJsonTemplate> newList = new ArrayList<>();
				apiJsonTemplate.setDataType(apiJsonTemplate.getColumnName().substring(0, 1).toUpperCase()
						+ apiJsonTemplate.getColumnName().substring(1));
				newList.add(apiJsonTemplate);
				entitiesMap.put(entityName, newList);
			} else {
				List<ApiJsonTemplate> preList = entitiesMap.get(entityName);
				apiJsonTemplate.setDataType(apiJsonTemplate.getColumnName().substring(0, 1).toUpperCase()
						+ apiJsonTemplate.getColumnName().substring(1));
				preList.add(apiJsonTemplate);
				entitiesMap.put(entityName, preList);
			}

			for (Iterator<ApiJsonTemplate> iterator = nestedList.iterator(); iterator.hasNext();) {
				ApiJsonTemplate nestedApiJsonTemplate = mapper.convertValue(iterator.next(), ApiJsonTemplate.class);
				prepareApiJsonTemplateObj(entitiesMap, nestedApiJsonTemplate);
			}
		}
		return entitiesMap;
	}

	public void generateFileFromTemplate(Map<String, Object> map, String templateName, String location,
			String fileName) {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		try {
			cfg.setDirectoryForTemplateLoading(
					new File(System.getProperty("user.dir") + "\\src\\main\\resources\\templates"));
			cfg.setDefaultEncoding("UTF-8");
			Template template = cfg.getTemplate(templateName);
			File file = new File(location + fileName + ".java");
			file.createNewFile();
			Writer writer = new FileWriter(file);
			template.process(map, writer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateFileFromTemplateV2(Map<String, Object> map, String templateType, String templateName,
			String location, String fileName,String fileExt) {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		try {
			if(fileExt.equals(".java"))
				location = location + File.separator + templateType + File.separator;
			else
				location = location + File.separator;
			
			cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")+File.separator+"src"+
							File.separator+"main"+File.separator+"resources"+File.separator+"templates"+File.separator+templateType));
			cfg.setDefaultEncoding("UTF-8");
			Template template = cfg.getTemplate(templateName);
			File file = new File(location);
			file.mkdirs();
			file = new File(location + fileName + fileExt);
			file.createNewFile();
			Writer writer = new FileWriter(file);
			template.process(map, writer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateFilesFromTemplate(Map<String, Map<String, Object>> listOfMap, Model model, String src,
			String dest) {
		final String modelName = model.getModelName();
		generateFileFromTemplate(listOfMap.get("entityMap"), "EntityTemplate.ftl",
				dest + BASE_JAVA_FOLDER + BASE_PACKAGE + "entity\\", modelName);
		generateFileFromTemplate(listOfMap.get(GENERIC_MAP), "ControllerTemplate.ftl",
				dest + BASE_JAVA_FOLDER + BASE_PACKAGE + "controller\\", modelName + "Controller");
		generateFileFromTemplate(listOfMap.get(GENERIC_MAP), "RepositoryTemplate.ftl",
				dest + BASE_JAVA_FOLDER + BASE_PACKAGE + "repository\\", modelName + "Repository");
		generateFileFromTemplate(listOfMap.get(GENERIC_MAP), "ServiceTemplate.ftl",
				dest + BASE_JAVA_FOLDER + BASE_PACKAGE + "service\\", modelName + "Service");
		generateFileFromTemplate(listOfMap.get(GENERIC_MAP), "ServiceImplTemplate.ftl",
				dest + BASE_JAVA_FOLDER + BASE_PACKAGE + "service\\", modelName + "ServiceImpl");
	}

	/**
	 * used to create java files for project like entities, controller, service,
	 * repositories etc from templates and template values.
	 * 
	 * @author shivam, rohan
	 * @param entitiesMap      to be finally made.
	 * @param src              for source files.
	 * @param dest             for destination file location.
	 * @param apiDto           for current api to be made.
	 * @param getApiMethodName if it is a get call then it is provided.
	 */
	public void generateFilesFromTemplateV2(Map<String, List<ApiJsonTemplate>> entitiesMap, String src, String dest,
			API apiDto, String getApiMethodName) {

		Set<String> entities = entitiesMap.keySet();

		// iterating over json string for fetching all entities.
		for (String entityName : entities) {
			Map<String, Object> entityMap = new HashMap<>();
			entityMap.put("params", entitiesMap.get(entityName));
			entityMap.put("tableName", entityName);
			entityMap.put("EntityName", entityName);
			entityMap.put("ApiName", apiDto.getApiName());

			String filename = entityName.substring(0,1).toUpperCase()+entityName.substring(1);
			generateFileFromTemplateV2(entityMap, "entity", "EntityTemplate.ftl",
					dest + BASE_JAVA_FOLDER + BASE_PACKAGE + apiDto.getApiName(), filename,".java");
		}
		Map<String, Object> entityMap = new HashMap<>();
		entityMap.put("EntityName", apiDto.getMainEntityName());
		entityMap.put("ApiName", apiDto.getApiName());
		entityMap.put("ApiUrl", apiDto.getApiUrl());
		entityMap.put("idType", apiDto.getMainEntityIdType());
		entityMap.put("params", apiDto.getGetParams());
		entityMap.put("MethodName", getApiMethodName);

		String destinationPath = dest + BASE_JAVA_FOLDER + BASE_PACKAGE + apiDto.getApiName();

		String apiName = apiDto.getApiName().substring(0,1).toUpperCase()+apiDto.getApiName().substring(1);
		// creating accordingly for each api type.
		if (apiDto.getApiType().equalsIgnoreCase("post")) {

			generateFileFromTemplateV2(entityMap, "controller", "PostControllerTemplate.ftl", destinationPath,
					apiName + "Controller",".java");

			generateFileFromTemplateV2(entityMap, "service", "PostServiceTemplate.ftl", destinationPath,
					apiName + "Service",".java");
			generateFileFromTemplateV2(entityMap, "service", "PostServiceImplTemplate.ftl", destinationPath,
					apiName + "ServiceImpl",".java");

			generateFileFromTemplateV2(entityMap, "repository", "PostRepositoryTemplate.ftl", destinationPath,
					apiName + "Repository",".java");
		} else if (apiDto.getApiType().equalsIgnoreCase("put")) {
			generateFileFromTemplateV2(entityMap, "controller", "PutControllerTemplate.ftl", destinationPath,
					apiName + "Controller",".java");

			generateFileFromTemplateV2(entityMap, "service", "PutServiceTemplate.ftl", destinationPath,
					apiName + "Service",".java");
			generateFileFromTemplateV2(entityMap, "service", "PutServiceImplTemplate.ftl", destinationPath,
					apiName + "ServiceImpl",".java");

			generateFileFromTemplateV2(entityMap, "repository", "PutRepositoryTemplate.ftl", destinationPath,
					apiName + "Repository",".java");

		} else if (apiDto.getApiType().equalsIgnoreCase("get")) {
			generateFileFromTemplateV2(entityMap, "controller", "GetControllerTemplate.ftl", destinationPath,
					apiName + "Controller",".java");

			generateFileFromTemplateV2(entityMap, "service", "GetServiceTemplate.ftl", destinationPath,
					apiName + "Service",".java");
			generateFileFromTemplateV2(entityMap, "service", "GetServiceImplTemplate.ftl", destinationPath,
					apiName + "ServiceImpl",".java");

			generateFileFromTemplateV2(entityMap, "repository", "GetRepositoryTemplate.ftl", destinationPath,
					apiName + "Repository",".java");

		} else if (apiDto.getApiType().equalsIgnoreCase("delete")) {
			// TODO for delete type api

		}

	}

	@Override
	public void compileProject(String fileLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createGenerateTokenAPI(Project project) {
		ApiDto api= new ApiDto();
		api.setApiName("generate-token");
		api.setApiType("POST");
		api.setProjectName(project.getProjectName());
		api.setProjectId(project.get_id());
		api.setApiUrl("token/generate-token");
		api.setSecured(false);
	
		ApiJsonTemplate jsonTemplate1 = new ApiJsonTemplate();
		jsonTemplate1.getAdditionalProperties().put("keyName", "username");
		
		ApiJsonTemplate jsonTemplate2 = new ApiJsonTemplate();
		jsonTemplate2.getAdditionalProperties().put("keyName", "password");
		
		api.getJsonString().add(jsonTemplate1);
		api.getJsonString().add(jsonTemplate2);
		
		api.setReJson("{\"username\": \"appBuilder\",\"password\" :\"appBuilder\"} ");
		persistenceService.createAPI(api);
		tenantAPIService.saveAPI(api);
	}
	
	public void createUserEntityForSecurity(API api,String dest) {
		Map<String,Object> map = new HashMap<>();
		map.put("tableName", api.getMainEntityName());
		for(ApiJsonTemplate apiJson:api.getJsonString()) {
			String key = apiJson.getAdditionalProperties().values().iterator().next().toString();
			if(apiJson.getIsPrimaryKey())
				key="id";
			switch(key) {
			case "id": 
				map.put("idColumn", apiJson.getColumnName());
				break;
			case "username":
				map.put("userNameColumn", apiJson.getColumnName());
				break;
			case "password":
				map.put("passwordColumn", apiJson.getColumnName());
				break;
			}
		}
		String basePackage= BASE_PACKAGE.substring(0, BASE_PACKAGE.lastIndexOf(File.separator));
		generateFileFromTemplateV2(map, "security", "User.ftl", dest+File.separator+(BASE_JAVA_FOLDER + basePackage), "entity"+File.separator+"User", ".java");
	}

}
