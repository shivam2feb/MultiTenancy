package com.mfsi.appbuilder.service;

import java.util.Map;

import com.mfsi.appbuilder.model.Model;

public interface AppService {

	
	public boolean copyFolder(String srcPath, String dscPath);
	public Map<String,Map<String, Object>> prepareMapForTemplate(Model model);
	public void generateFilesFromTemplate(Map<String,Map<String,Object>> map,Model model,String src,String dest);
	public void compileProject(String fileLocation);
	
}
