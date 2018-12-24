package com.mfsi.appbuilder.service;

import java.util.Map;

import com.mfsi.appbuilder.entity.Model;

public interface DemoService {

	
	public boolean copyFolder(String srcPath, String dscPath);

	public Map<String, Object> prepareMapForTemplate(Model model);
}
