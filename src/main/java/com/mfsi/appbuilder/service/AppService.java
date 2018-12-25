package com.mfsi.appbuilder.service;

import java.util.Map;

import com.mfsi.appbuilder.entity.Model;

public interface AppService {

	
	public boolean copyFolder(String srcPath, String dscPath);
	public Map<String,Map<String, Object>> prepareMapForTemplate(Model model);
	public void generateFileFromTemplate(Map<String,Object> map,String templateName,String fileName);
}
