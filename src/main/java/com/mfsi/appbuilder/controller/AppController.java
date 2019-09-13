package com.mfsi.appbuilder.controller;

/**
 * 
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfsi.appbuilder.dto.MetaDataDTO;
import com.mfsi.appbuilder.dto.ProjectDTO;
import com.mfsi.appbuilder.master.document.API;
import com.mfsi.appbuilder.master.document.Project;
import com.mfsi.appbuilder.model.ApiJsonTemplate;
import com.mfsi.appbuilder.model.Parameter;
import com.mfsi.appbuilder.service.AppService;
import com.mfsi.appbuilder.service.AppServiceImpl;
import com.mfsi.appbuilder.service.PersistenceService;
import com.mfsi.appbuilder.tenant.service.TenantAPIService;
import com.mfsi.appbuilder.util.MySQLDatabaseGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AppController {

	@Autowired
	AppService appService;

	@Autowired
	PersistenceService persistenceService;

	@Value("classpath:templateProject")
	private String src;

	@Value("${destinationSource}")
	private String destination;
	
	@Autowired
	private TenantAPIService tenantApiService;

	/**
	 * saving destination and project name for storing zip 
	 */
	private String dest = new String();

	private String projectName = new String();



	/**
	 * Used to download the projects to client machine.
	 * 
	 * @author rohan
	 * @param projectId - takes project id to download all apis related to it.
	 */
	@GetMapping(value = "/downloadProject/{projectId}")
	public void downloadProject(@PathVariable String projectId, HttpServletResponse response) {
		String getApiMethodName = "";

		// fetch from db
		List<API> apis = persistenceService.getAPI(projectId);
		Project projectDetails = persistenceService.getProjectDetails(projectId);
		Boolean wantedSecurity = projectDetails.getWantSecurity();
		
		String dest = null;
		
		// loop on all apis
		for (API api : apis) {

			// for creating the repository method name like findBy"something"
			if (api.getApiType().equalsIgnoreCase("get"))
				getApiMethodName = appService.createMethodName(api.getGetParams());

			dest = destination + File.separator + api.getProjectName();

			this.projectName = api.getProjectName();
			this.dest = dest;
			
			if (appService.copyFolder(src, dest, wantedSecurity)) {

				Map<String, List<ApiJsonTemplate>> entitiesMap = appService.prepareEntitiesMap(api.getJsonString());
				appService.generateFilesFromTemplateV2(entitiesMap, src, dest + File.separator, api, getApiMethodName);
			}
		}
		Map<String, Object> appPropsMap = new HashMap<String, Object>();


		appPropsMap.put("db_schemaname", projectDetails.getDbDetailsDTO().getSchema());
		appPropsMap.put("db_username", projectDetails.getDbDetailsDTO().getDbUsername());
		appPropsMap.put("db_password", projectDetails.getDbDetailsDTO().getDbPassword());

		appService.generateFileFromTemplateV2(appPropsMap, "property", "application.properties.ftl",
				dest + File.separator + AppServiceImpl.BASE_RESOURCES_FOLDER, "application", ".properties");


		// Use the following paths for windows
		// String folderToZip = "c:\\demo\\test";
		// String zipName = "c:\\demo\\test.zip";
		// Linux/mac paths
		/**
		 * Code for converting project folder to zip and make zip downloadable
		 * author: shubham
		 */
		String folderToZip = this.dest;
		System.out.println(folderToZip);
		String zipName = this.dest + ".zip";
		File file = new File(this.dest + ".zip");

		this.zipFolder(Paths.get(folderToZip), Paths.get(zipName));
		try (InputStream is = new FileInputStream(file); OutputStream out = response.getOutputStream();) {

			byte[] buffer = new byte[1024];
			int bytesRead = -1;
			response.setContentType("application/zip");
			response.addHeader("Content-Disposition", "attachment; filename=" + this.projectName + ".zip");
			while ((bytesRead = is.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String folder = this.dest;

		/**
		 * method call for deleting the project folder after zip has been created
		 */
		this.recursiveDelete(new File(folder));
		
		/**
		 * method call for deleting the zip after download
		 */
		this.recursiveDelete(new File(this.dest + ".zip"));
	}

	@PostMapping(value = "/createPojo")
	public void generatePojo(@RequestBody String jsonObj) {
		Map<String, Object> templateMap = new HashMap<>();

		Map<String, Object> map = new HashMap<>();

		List<Parameter> listOfParam = new ArrayList<>();

		Parameter param1 = new Parameter();
		param1.setDataType("Long");
		param1.setColumnName("id");
		param1.setIsPrimaryKey(true);
		listOfParam.add(param1);
		templateMap.put("params", listOfParam);
		templateMap.put("EntityName", "Model");

		for (Entry<String, Object> entrySet : map.entrySet()) {
			Parameter param = new Parameter();
			param.setDataType(entrySet.getValue().getClass().getSimpleName());
			param.setColumnName(entrySet.getKey());
			listOfParam.add(param);
		}
	}

	@PostMapping("/createProject1")
	public boolean createProject1(@RequestBody String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
			});
		} catch (Exception e) {
			// logger.error("Error while parsing the the JSON String {}",e);
			e.printStackTrace();
		}
		// System.out.println(jsonObject.get("proojectName"));
		// persistenceService.saveProject("MyProject");
		return true;
	}

	/**
	 * method for converting a file to zip file.
	 * author - shubham
	 * @param sourceFolderPath -  path of file need to be converted to zip
	 * @param zipPath - path where zip is going to be created
	 */
	private void zipFolder(Path sourceFolderPath, Path zipPath) {
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));) {
			Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
					Files.copy(file, zos);
					zos.closeEntry();
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * method for deleting a folder structure recursively
	 * author- shubham
	 * @param file the file which is going delete 
	 */
	private void recursiveDelete(File file) {
		// to end the recursive loop
		if (!file.exists())
			return;

		// if directory, go inside and call recursively
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				// call recursively
				recursiveDelete(f);
			}
		}
		// call delete to delete files and empty directory
		// System.out.println("Deleted file/folder: "+file.getAbsolutePath());

		file.delete();
	}

	@PostMapping(value = "/getDBInfo")
	public Map<String, List<MetaDataDTO>> getDBInfo(@RequestBody ProjectDTO projectDTO) {
		return persistenceService.getDBInfo(projectDTO);
	}

}
