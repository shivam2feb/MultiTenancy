package com.mfsi.appbuilder.service;

import java.util.List;
import java.util.Map;

import com.mfsi.appbuilder.document.API;
import com.mfsi.appbuilder.model.ApiJsonTemplate;
import com.mfsi.appbuilder.model.Model;

public interface AppService {

	
	public boolean copyFolder(String srcPath, String dscPath);
	public Map<String,Map<String, Object>> prepareMapForTemplate(Model model);
	public void generateFilesFromTemplate(Map<String,Map<String,Object>> map,Model model,String src,String dest);
	public void generateFilesFromTemplateV2(Map<String, List<ApiJsonTemplate>> map,String src,String dest,API apiDto,String getApiMethodName);
	public void compileProject(String fileLocation);
	public Map<String, List<ApiJsonTemplate>> prepareEntitiesMap(List<ApiJsonTemplate> jsonString);
	public String createMethodName(List<ApiJsonTemplate> getParams);
}
