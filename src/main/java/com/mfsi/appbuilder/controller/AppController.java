package com.mfsi.appbuilder.controller;

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfsi.appbuilder.document.API;
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

	private String  dest = new String();

	private String projectName = new String();

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

	@GetMapping(value="/downloadProject/{id}")
	public void downloadProject(@PathVariable String id,HttpServletResponse response) {

		ZipOutputStream zos;
		// fetch from db 
		List<API> apis = persistenceService.getAPI(id);
		// loop on all apiDto
		for (API api : apis) {
			String dest=destination+"\\"+api.getProjectName();
			this.projectName = api.getProjectName();
			this.dest = dest;
			if(demoService.copyFolder(src, dest)) {
				Map<String, List<ApiJsonTemplate>> listOfMap=demoService.prepareMapForTemplateV2(api);
				demoService.generateFilesFromTemplateV2(listOfMap,src,dest+"\\",api);
			}
		}


		// Use the following paths for windows
		//String folderToZip = "c:\\demo\\test";
		//String zipName = "c:\\demo\\test.zip";

		// Linux/mac paths
		String folderToZip = this.dest;
		String zipName = this.dest + ".zip";
		File file= new File(this.dest + ".zip");

		this.zipFolder(Paths.get(folderToZip), Paths.get(zipName));
		try(InputStream is=new FileInputStream(file);
				OutputStream out=response.getOutputStream();) {


			//zos.


			byte[] buffer=new byte[1024];
			int bytesRead=-1;
			response.setContentType("application/zip");
			response.addHeader("Content-Disposition", "attachment; filename="+this.projectName+".zip");
			while((bytesRead=is.read(buffer))!=-1) {
				out.write(buffer, 0, bytesRead);
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		String folder = this.dest;
		//delete folder recursively
		this.recursiveDelete(new File(folder));
		
		this.recursiveDelete(new File(this.dest + ".zip"));


	}

	@PostMapping(value="/createPojo")
	public void generatePojo(@RequestBody String jsonObj) {
		Map<String,Object> templateMap=new HashMap<>();
		//System.out.println("Json String is "+jsonObj);
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
		//System.out.println(jsonObject.get("proojectName"));
		//persistenceService.saveProject("MyProject");
		return true;
	}

	private void zipFolder(Path sourceFolderPath, Path zipPath){
		try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));){
			Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
					Files.copy(file, zos);
					zos.closeEntry();
					return FileVisitResult.CONTINUE;
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void recursiveDelete(File file) {
		//to end the recursive loop
		if (!file.exists())
			return;

		//if directory, go inside and call recursively
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				//call recursively
				recursiveDelete(f);
			}
		}
		//call delete to delete files and empty directory
		//System.out.println("Deleted file/folder: "+file.getAbsolutePath());

		file.delete();
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
