package com.mfsi.appbuilder.model;

import java.util.List;


public class Model {
	
	private String applicationName;
	private String modelName;
	private String tableName;
	private List<Parameter> parameterList;
	
	
	

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getModelName() {
		return modelName;
	}
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public List<Parameter> getParameterList() {
		return parameterList;
	}
	
	public void setParameterList(List<Parameter> parameterList) {
		this.parameterList = parameterList;
	}
	
	
}
