package com.mfsi.appbuilder.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mfsi.appbuilder.entity.Model;

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
	
	public Map<String,Object> prepareMapForTemplate(Model model){
		Map<String,Object> templateMap=new HashMap<String,Object>();
		templateMap.put("params", model.getParameterList());
		templateMap.put("tableName", model.getTableName());
		templateMap.put("EntityName", model.getModelName());
		return new HashMap<String,Object>();
	}
}
