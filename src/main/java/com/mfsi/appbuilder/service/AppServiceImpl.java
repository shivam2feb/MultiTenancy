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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mfsi.appbuilder.model.Model;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class AppServiceImpl implements AppService{

	@Value("${templateSource}")
	private String src;

	@Value("${destinationSource}")
	private String dest;

	static final String GENERIC_MAP="genericMap";
	static final String BASE_JAVA_FOLDER="src\\main\\java\\";
	static final String BASE_PACKAGE="com\\app\\";

	public boolean copyFolder(String srcPath, String dscPath) {
		File srcFolder=new File(srcPath);
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
			if(parameter.getIsId()) {
				genericMap.put("idType",parameter.getDataType());
			}
		});
		mapsOfTemplate.put("genericMap", genericMap);
		return mapsOfTemplate;
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
	
	public void compileProject(String fileLocation) {
		
	}
	
	public static void runProcess(String command) throws IOException{
		Process process=Runtime.getRuntime().exec(command);
	}
}
