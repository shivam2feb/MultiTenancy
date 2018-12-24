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

import org.springframework.stereotype.Service;

import com.mfsi.appbuilder.entity.Model;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class DemoServiceImpl implements DemoService{

	public boolean copyFolder(String srcPath, String dscPath) {
		File srcFolder=new File(srcPath);
		File dscFolder=new File(dscPath);
		try {
			copyFolder(srcFolder,dscFolder);
			System.out.println("Folder is copied");
			return true;
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
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
	
	public void generateFileFromTemplate(Map<String,Object> map,String templateName,String fileName) {
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
